package com.idk.diary.api;


import com.idk.diary.api.dto.CreateDiaryDto;
import com.idk.diary.api.dto.PatchDiaryDto;
import com.idk.diary.domain.dto.CreateDiaryDtoTestDataBuilder;
import com.idk.diary.domain.dto.PatchDiaryDtoTestDataBuilder;
import com.idk.diary.domain.exception.DiaryVersionNotMatchesToExistingRecord;
import com.idk.diary.domain.model.Diary;
import com.idk.diary.domain.model.DiaryId;
import com.idk.diary.domain.model.DiaryTestBuilder;
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
    void givenExistingDiaryIdButOutdatedVersion_whenRequestedWithPatch_thenThrowException() throws Exception {
        // given
        DiaryId availableDiaryId = addNewDiaryToTheSystem();
        Diary availableDiary = diaryRepository.findById(availableDiaryId).get();

        // and
        Diary loadedDiary = diaryRepository.findById(availableDiaryId).get();

        // and
        diaryWasModifiedInTheMeanTime(availableDiary);

        // and
        PatchDiaryDto patchDiaryDtoOnLoadedDiary = PatchDiaryDtoTestDataBuilder.builder()
                .text("Update my loaded diary where version is 0.")
                .name(availableDiary.getName())
                .location(availableDiary.getLocation())
                .build();

        // when
        ResultActions resultActions = sendPatchDiaryCommandFor(loadedDiary.getId(), patchDiaryDtoOnLoadedDiary, loadedDiary.getVersion());

        // then
        resultActions.andExpectAll(
                status().isConflict(),
                jsonPath("$.message", containsString(DiaryVersionNotMatchesToExistingRecord.MESSAGE))
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
    public void givenDefaultCreateDiaryDtoPayload_whenRequestedWithPost_thenCreateNewDiarySuccessfully() throws Exception {
        //given
        CreateDiaryDto createDiaryDto = CreateDiaryDtoTestDataBuilder.builder()
                .name("Jhony Cash")
                .text("I wrote a song today")
                .location("Kentucky")
                .build();

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
                                .header(HttpHeaders.IF_MATCH, resourceVersion)
                                .content(payload)
                );
    }


    private DiaryId addNewDiaryToTheSystem(){
        Diary diary = new DiaryTestBuilder()
                .name("ilker Kanatlı")
                .id(DiaryId.randomUUID())
                .location("İstanbul")
                .text("I am tired. Sleepy now!")
                .build();
        return diaryRepository.save(diary).getId();
    }


}