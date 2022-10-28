package com.idk.diary.api.controller;

import com.idk.diary.api.DiaryApi;
import com.idk.diary.api.dto.CreateDiaryDto;
import com.idk.diary.api.dto.GetDiaryDto;
import com.idk.diary.api.dto.PatchDiaryDto;
import com.idk.diary.api.dto.converter.request.PatchDiaryDtoToDiaryConverter;
import com.idk.diary.domain.model.Diary;
import com.idk.diary.domain.model.DiaryId;
import com.idk.diary.domain.services.impl.CreateDiaryCommand;
import com.idk.diary.domain.services.impl.DeleteDiaryCommand;
import com.idk.diary.domain.services.impl.RetrieveDiaryQuery;
import com.idk.diary.domain.services.impl.UpdateDiaryCommand;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Log4j2 // Version2 enables async logging.
@RestController
public class DiaryController implements DiaryApi {

    private final CreateDiaryCommand createDiaryCommand;
    private final DeleteDiaryCommand deleteDiaryCommand;
    private final RetrieveDiaryQuery retrieveDiaryQuery;
    private final UpdateDiaryCommand updateDiaryCommand;

    private final ConversionService conversionService;
    private final PatchDiaryDtoToDiaryConverter patchDiaryDtoToDiaryConverter;

    @Autowired
    public DiaryController(CreateDiaryCommand createDiaryCommand,
                           DeleteDiaryCommand deleteDiaryCommand,
                           RetrieveDiaryQuery retrieveDiaryQuery,
                           UpdateDiaryCommand updateDiaryCommand,
                           ConversionService conversionService,
                           PatchDiaryDtoToDiaryConverter patchDiaryDtoToDiaryConverter) {
        this.createDiaryCommand = createDiaryCommand;
        this.deleteDiaryCommand = deleteDiaryCommand;
        this.retrieveDiaryQuery = retrieveDiaryQuery;
        this.updateDiaryCommand = updateDiaryCommand;
        this.conversionService = conversionService;
        this.patchDiaryDtoToDiaryConverter = patchDiaryDtoToDiaryConverter;
    }

    // TODO: add uriComponentsBuilder
    @Override
    public ResponseEntity<?> create(CreateDiaryDto createDiaryDto) {
        log.info("DiaryController -> Create diary started for {}", createDiaryDto.name());

        Diary diary = createDiaryCommand.execute(
                Objects.requireNonNull(conversionService.convert(createDiaryDto, Diary.class))
        );

        return new ResponseEntity<>(diary, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> delete(UUID id) {
        log.info("DiaryController -> Delete diary started for {}", id);
        deleteDiaryCommand.execute(DiaryId.from(id));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<GetDiaryDto> retrieve(UUID id) {
        log.info("DiaryController -> Get diary for {}", id);
        Diary retrievedDiary = retrieveDiaryQuery.execute(DiaryId.from(id));
        GetDiaryDto getDiaryDto = conversionService.convert(retrievedDiary, GetDiaryDto.class);
        return new ResponseEntity<>(getDiaryDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Diary>> retrieve() {
        log.info("DiaryController -> Get diary list");
        // TODO: apply pagination and new type oof DiaryQuery.
        // TODO: create DTO converter
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    // TODO: add eTag header and confirm accordingly
    @Override
    public ResponseEntity<Diary> patch(UUID id, PatchDiaryDto patchDiaryDto) {
        log.info("DiaryController -> Patch diary started for name {} and id {}", patchDiaryDto.name(), id);

        // diary.setVersion(patchDiaryDto.); TODO add e-tag, optimistic lock mechanism, version control
        UpdateDiaryCommand.DiaryUpdate diaryUpdate = patchDiaryDtoToDiaryConverter.convert(id, 0, patchDiaryDto);

        Diary updatedDiary = updateDiaryCommand.execute(diaryUpdate);
        return new ResponseEntity<>(updatedDiary, HttpStatus.OK);
    }
}
