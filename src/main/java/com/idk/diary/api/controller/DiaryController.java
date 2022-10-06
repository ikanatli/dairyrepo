package com.idk.diary.api.controller;

import com.idk.diary.api.DiaryApi;
import com.idk.diary.api.dto.CreateDiaryDto;
import com.idk.diary.api.dto.PatchDiaryDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class DiaryController implements DiaryApi {


    @Override
    public ResponseEntity<?> create(CreateDiaryDto createDiaryDto) {
        return new ResponseEntity<>(createDiaryDto.name(), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> delete(UUID id) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<?> getById(UUID id) {
        return new ResponseEntity<>(String.valueOf(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> patch(UUID id, PatchDiaryDto patchDiaryDto) {
        return new ResponseEntity<>(patchDiaryDto.name() + " " + patchDiaryDto.id(), HttpStatus.OK);
    }
}
