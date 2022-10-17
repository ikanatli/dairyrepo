package com.idk.diary.api.controller;

import com.idk.diary.api.DiaryApi;
import com.idk.diary.api.dto.CreateDiaryDto;
import com.idk.diary.api.dto.PatchDiaryDto;
import com.idk.diary.model.Diary;
import com.idk.diary.model.DiaryId;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Log4j2 // Version2 enables async logging.
@RestController
public class DiaryController implements DiaryApi {


    @Override
    public ResponseEntity<?> create(CreateDiaryDto createDiaryDto) {
        log.info("DiaryController -> Create diary started for {}", createDiaryDto.name());
        return new ResponseEntity<>(createDiaryDto.name(), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> delete(UUID id) {
        log.info("DiaryController -> Delete diary started for {}", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Diary> retrieve(UUID id) {
        log.info("DiaryController -> Get diary for {}", id);

        // TODO: remove dummy things...
        Diary dummyDiary = new Diary();
        dummyDiary.setId(new DiaryId(new UUID(234L, 567L)));

        return new ResponseEntity<>(dummyDiary, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Diary>> retrieve() {
        log.info("DiaryController -> Get diary list");

        // TODO: remove dummy things...
        Diary dummyDiary = new Diary();
        dummyDiary.setName("Dummy Day günlüğü.");
        dummyDiary.setId(new DiaryId(new UUID(123L, 456L)));
        List<Diary> dummyResult = List.of(dummyDiary);

        return new ResponseEntity<>(dummyResult, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Diary> patch(UUID id, PatchDiaryDto patchDiaryDto) {
        log.info("DiaryController -> Patch diary started for name {} and id {}", patchDiaryDto.name(), id);

        // TODO: remove dummy things...
        Diary dummyDiary = new Diary();
        dummyDiary.setId(new DiaryId(id));
        List<Diary> dummyResult = List.of(dummyDiary);

        return new ResponseEntity<>(dummyDiary, HttpStatus.OK);
    }
}
