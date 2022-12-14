package com.idk.diary.api;


import com.idk.diary.api.dto.CreateDiaryDto;
import com.idk.diary.api.dto.PatchDiaryDto;
import com.idk.diary.domain.dto.CreateDiaryDtoTestDataBuilder;
import com.idk.diary.domain.dto.PatchDiaryDtoTestDataBuilder;
import com.idk.diary.domain.exception.PreConditionFailedForPatchDiary;
import com.idk.diary.domain.exception.PreConditionHeaderRequired;
import com.idk.diary.domain.model.Diary;
import com.idk.diary.domain.model.DiaryId;
import com.idk.diary.domain.model.DiaryTestDataBuilder;
import com.idk.diary.domain.persistence.DiaryRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class DiaryApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DiaryRepository diaryRepository;

    private void diaryWasModifiedInTheMeanTime(Diary diary) {
        diary.setText("Faster user updated the diary.");
        diaryRepository.save(diary);
    }

    @Test
    void givenExistingDiaryIdWithCorrectPayload_whenRequestedWithPatch_thenUpdateDiarySuccessfully() throws Exception {
        // given
        DiaryId availableDiaryId = addNewDiaryToTheSystem();
        Diary availableDiary = diaryRepository.findById(availableDiaryId).get();
        // and
        PatchDiaryDto patchDiaryDtoOnLoadedDiary = PatchDiaryDtoTestDataBuilder.aDefaultPatchDiaryDto()
                .buildDefault();

        // when
        ResultActions resultActions = sendPatchDiaryCommandFor(availableDiaryId, patchDiaryDtoOnLoadedDiary, availableDiary.getVersion());

        // then
        resultActions.andExpectAll(
                status().isOk(),
                jsonPath("$.name", is(patchDiaryDtoOnLoadedDiary.name())),
                jsonPath("$.text", is(patchDiaryDtoOnLoadedDiary.text())),
                jsonPath("$.location", is(patchDiaryDtoOnLoadedDiary.location())),
                jsonPath("$.version", not(availableDiary.getVersion()))
        );

    }

    @Test
    void givenExistingDiaryIdWithNullName_whenRequestedWithPatch_thenThrowException() throws Exception {
        // given
        DiaryId availableDiaryId = addNewDiaryToTheSystem();
        Diary availableDiary = diaryRepository.findById(availableDiaryId).get();
        // and
        PatchDiaryDto patchDiaryDtoOnLoadedDiary = PatchDiaryDtoTestDataBuilder.anEmptyPatchDiaryDto()
                .withText("Update my loaded diary where version is null.")
                .withLocation(availableDiary.getLocation())
                .buildEmpty();

        // when
        ResultActions resultActions = sendPatchDiaryCommandFor(availableDiaryId, patchDiaryDtoOnLoadedDiary, availableDiary.getVersion());

        // then
        resultActions.andExpectAll(
                status().isBadRequest(),
                jsonPath("$.message", containsString("Name must not be empty."))
        );

    }

    @Test
    void givenExistingDiaryIdWitEmptyName_whenRequestedWithPatch_thenThrowException() throws Exception {
        // given
        DiaryId availableDiaryId = addNewDiaryToTheSystem();
        Diary availableDiary = diaryRepository.findById(availableDiaryId).get();
        // and
        PatchDiaryDto patchDiaryDtoOnLoadedDiary = PatchDiaryDtoTestDataBuilder.anEmptyPatchDiaryDto()
                .withName("")
                .withText("Update my loaded diary where version is null.")
                .withLocation(availableDiary.getLocation())
                .buildEmpty();

        // when
        ResultActions resultActions = sendPatchDiaryCommandFor(availableDiaryId, patchDiaryDtoOnLoadedDiary, availableDiary.getVersion());

        // then
        resultActions.andExpectAll(
                status().isBadRequest(),
                jsonPath("$.message", containsString("Name must not be empty."))
        );

    }

    @Test
    void givenExistingDiaryIdWithoutIfMatchHeader_whenRequestedWithPatch_thenThrowPreConditionRequiredException() throws Exception {
        // given
        DiaryId availableDiaryId = addNewDiaryToTheSystem();
        Diary availableDiary = diaryRepository.findById(availableDiaryId).get();
        // and
        PatchDiaryDto patchDiaryDtoOnLoadedDiary = PatchDiaryDtoTestDataBuilder.aDefaultPatchDiaryDto()
                .withText("Update my loaded diary where version is null.")
                .withName(availableDiary.getName())
                .withLocation(availableDiary.getLocation())
                .buildEmpty();

        // when
        ResultActions resultActions = sendPatchDiaryCommandFor(availableDiaryId, patchDiaryDtoOnLoadedDiary, null);

        // then
        resultActions.andExpectAll(
                status().isPreconditionRequired(),
                jsonPath("$.message", containsString(String.format(PreConditionHeaderRequired.MESSAGE, HttpHeaders.IF_MATCH)))
        );

    }



    @Test
    void givenExistingDiaryIdButOutdatedVersion_whenRequestedWithPatch_thenThrowPreConditionFailedException() throws Exception {
        // given
        DiaryId availableDiaryId = addNewDiaryToTheSystem();
        Diary availableDiary = diaryRepository.findById(availableDiaryId).get();

        // and
        ResultActions dairyGetResponse = sendGetDiaryCommandFor(availableDiaryId);
        String eTag = dairyGetResponse.andReturn().getResponse().getHeader(HttpHeaders.ETAG);

        // and
        diaryWasModifiedInTheMeanTime(availableDiary);

        // and
        PatchDiaryDto patchDiaryDtoOnLoadedDiary = PatchDiaryDtoTestDataBuilder.aDefaultPatchDiaryDto()
                .withText("Update my loaded diary where version is " + eTag)
                .withName(availableDiary.getName())
                .withLocation(availableDiary.getLocation())
                .buildEmpty();

        // when
        ResultActions resultActions = sendPatchDiaryCommandFor(availableDiaryId, patchDiaryDtoOnLoadedDiary, availableDiary.getVersion());

        // then
        resultActions.andExpectAll(
                status().isPreconditionFailed(),
                jsonPath("$.message", containsString(PreConditionFailedForPatchDiary.MESSAGE))
        );

    }

    @Test
    void givenExistingDiaryId_whenRequestedWithGet_thenReturnEtagBasedOnVersionDiary() throws Exception {
        // given
        DiaryId availableDiaryId = addNewDiaryToTheSystem();
        Diary availableDiary = diaryRepository.findById(availableDiaryId).get();

        // when
        ResultActions resultActions = sendGetDiaryCommandFor(availableDiaryId);

        // then
        resultActions.andExpectAll(
                status().isOk(),
                jsonPath("$.id", is(availableDiaryId.getIdValue().toString())),
                jsonPath("$.version").doesNotExist(),
                header().string(HttpHeaders.ETAG, notNullValue()),
                header().string(HttpHeaders.ETAG, String.format("\"%d\"",availableDiary.getVersion()))
        );

    }

    @Test
    public void givenCreateDiaryDtoWithNullNamePayload_whenRequestedWithPost_thenThrowException() throws Exception {
        //given
        CreateDiaryDto createDiaryDto = CreateDiaryDtoTestDataBuilder.anEmptyCreateDiaryDto()
                .withText("ilker")
                .withLocation("istanbul")
                .buildEmpty();

        //when
        ResultActions resultActions = sendPostDiaryCommandFor(createDiaryDto);

        //then
        resultActions.andExpectAll(
                status().isBadRequest(),
                jsonPath("$.message", containsString("Name must not be empty."))
        );
    }

    @Test
    public void givenDefaultCreateDiaryDtoPayload_whenRequestedWithPost_thenCreateNewDiarySuccessfully() throws Exception {
        //given
        CreateDiaryDto createDiaryDto = CreateDiaryDtoTestDataBuilder.aDefaultCreateDiaryDto()
                .withName("Jhony Cash")
                .withText("I wrote a song today")
                .withLocation("Kentucky")
                .buildDefault();

        //when
        ResultActions resultActions = sendPostDiaryCommandFor(createDiaryDto);

        //then
        resultActions.andExpectAll(
                status().isCreated(),
                jsonPath("$", notNullValue()),
                jsonPath("$.name", is(createDiaryDto.name())),
                jsonPath("$.text", is(createDiaryDto.text())),
                jsonPath("$.location", is(createDiaryDto.location()))
        );
    }


    @Test
    public void givenNotExistingDiaryId_whenRequestedWithGet_thenReturnConflict() throws Exception {
        //given
        DiaryId notExistingDiaryId = DiaryId.randomUUID();

        //when
        ResultActions resultActions = sendGetDiaryCommandFor(notExistingDiaryId);

        //then
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    public void givenExistingDiaryId_whenRequestedWithGet_thenRetrieveTheDiarySuccessfully() throws Exception {
        //given
        DiaryId existingDiaryId = addNewDiaryToTheSystem();

        //when
        ResultActions resultActions = sendGetDiaryCommandFor(existingDiaryId);

        //then
        resultActions.andExpect(status().isOk());
    }

    private ResultActions sendGetDiaryCommandFor(DiaryId diaryId) throws Exception {
        return mockMvc
                .perform(get("/diary/{id}", diaryId.getIdValue()));
    }

    private ResultActions sendPostDiaryCommandFor(CreateDiaryDto createDiaryDto) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String payload = objectMapper.writeValueAsString(createDiaryDto);
        return mockMvc
                .perform(
                        post("/diary")
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                                .content(payload)
                );
    }

    private ResultActions sendPatchDiaryCommandFor(DiaryId id, PatchDiaryDto payloadDto, Integer resourceVersion) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String payload = objectMapper.writeValueAsString(payloadDto);
        return mockMvc
                .perform(
                        patch("/diary/{id}/", id.getIdValue())
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                                .header((resourceVersion != null ? HttpHeaders.IF_MATCH : "dummy"), (resourceVersion != null ? resourceVersion : "dummy"))
                                .content(payload)
                );
    }


    private DiaryId addNewDiaryToTheSystem(){
        Diary diary = new DiaryTestDataBuilder()
                .name("ilker Kanatl??")
                .id(DiaryId.randomUUID())
                .location("??stanbul")
                .text("I am tired. Sleepy now!")
                .build();
        return diaryRepository.save(diary).getId();
    }


}