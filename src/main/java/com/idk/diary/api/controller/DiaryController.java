package com.idk.diary.api.controller;

import com.idk.diary.api.DiaryApi;
import com.idk.diary.api.dto.CreateDiaryDto;
import com.idk.diary.api.dto.PatchDiaryDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<?> getById(UUID id) {
        log.info("DiaryController -> Get diary started for {}", id);
        return new ResponseEntity<>(String.valueOf(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> patch(UUID id, PatchDiaryDto patchDiaryDto) {
        log.info("DiaryController -> Patch diary started for name {} and id {}",
                patchDiaryDto.name(),
                patchDiaryDto.id());

        return new ResponseEntity<>(patchDiaryDto.name() + " " + patchDiaryDto.id(), HttpStatus.OK);
    }
}
