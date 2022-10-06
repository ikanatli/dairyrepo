package com.idk.diary.api;

import com.idk.diary.api.dto.CreateDiaryDto;
import com.idk.diary.api.dto.PatchDiaryDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

public interface DiaryApi {

    @PostMapping(path = "/diary",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<?> create(@RequestBody CreateDiaryDto createDiaryDto);

    @DeleteMapping(path = "/diary/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<Void> delete(@PathVariable("id") UUID id);

    @GetMapping(path = "/diary/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<?> getById(@PathVariable("id") UUID id);

    @PatchMapping(path = "/diary/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<?> patch(@PathVariable("id") UUID id,
                            @RequestBody PatchDiaryDto patchDiaryDto);

}
