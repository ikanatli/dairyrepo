package com.idk.diary.api.controller;

import com.idk.diary.api.DiaryApi;
import com.idk.diary.api.dto.CreateDiaryDto;
import com.idk.diary.api.dto.PatchDiaryDto;
import com.idk.diary.domain.model.Diary;
import com.idk.diary.domain.model.DiaryId;
import com.idk.diary.domain.services.impl.CreateDiaryCommand;
import com.idk.diary.domain.services.impl.DeleteDiaryCommand;
import com.idk.diary.domain.services.impl.RetrieveDiaryQuery;
import com.idk.diary.domain.services.impl.UpdateDiaryCommand;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Log4j2 // Version2 enables async logging.
@RestController
public class DiaryController implements DiaryApi {

    private final CreateDiaryCommand createDiaryCommand;
    private final DeleteDiaryCommand deleteDiaryCommand;
    private final RetrieveDiaryQuery retrieveDiaryQuery;
    private final UpdateDiaryCommand updateDiaryCommand;

    @Autowired
    public DiaryController(CreateDiaryCommand createDiaryCommand,
                           DeleteDiaryCommand deleteDiaryCommand,
                           RetrieveDiaryQuery retrieveDiaryQuery,
                           UpdateDiaryCommand updateDiaryCommand) {
        this.createDiaryCommand = createDiaryCommand;
        this.deleteDiaryCommand = deleteDiaryCommand;
        this.retrieveDiaryQuery = retrieveDiaryQuery;
        this.updateDiaryCommand = updateDiaryCommand;
    }

    @Override
    public ResponseEntity<?> create(CreateDiaryDto createDiaryDto) {
        log.info("DiaryController -> Create diary started for {}", createDiaryDto.name());

        // TODO: create DTO converter
        Diary diary = new Diary();
        diary.setName(createDiaryDto.name());
        diary.setCreatedAt(Instant.now());
        diary.setUpdatedAt(Instant.now());
        diary.setLocation(createDiaryDto.location());
        diary.setText(createDiaryDto.text());
        diary.setVersion(1);
        diary.setId(DiaryId.randomUUID());

        diary = createDiaryCommand.execute(diary);

        return new ResponseEntity<>(diary, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> delete(UUID id) {
        log.info("DiaryController -> Delete diary started for {}", id);

        // TODO: create DTO converter
        Diary diary = new Diary();
        diary.setId(DiaryId.from(id));

        deleteDiaryCommand.execute(diary);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Diary> retrieve(UUID id) {
        log.info("DiaryController -> Get diary for {}", id);
        // TODO: create DTO converter
        Diary retrievedDiary = retrieveDiaryQuery.execute(DiaryId.from(id));
        return new ResponseEntity<>(retrievedDiary, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Diary>> retrieve() {
        log.info("DiaryController -> Get diary list");
        // TODO: apply pagination and new type oof DiaryQuery.
        // TODO: create DTO converter
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Diary> patch(UUID id, PatchDiaryDto patchDiaryDto) {
        log.info("DiaryController -> Patch diary started for name {} and id {}", patchDiaryDto.name(), id);

        // TODO: create DTO converter
        Diary diary = new Diary();
        diary.setName(patchDiaryDto.name());
        diary.setUpdatedAt(Instant.now());
        diary.setLocation(patchDiaryDto.location());
        diary.setText(patchDiaryDto.text());
        // diary.setVersion(patchDiaryDto.); TODO add e-tag, optimistic lock mechanism, version control
        diary.setVersion(1);
        diary.setId(DiaryId.from(id));

        Diary updatedDiary = updateDiaryCommand.execute(diary);
        return new ResponseEntity<>(updatedDiary, HttpStatus.OK);
    }
}
