package com.idk.diary.api;

import com.idk.diary.api.dto.CreateDiaryDto;
import com.idk.diary.model.Error;
import com.idk.diary.api.dto.PatchDiaryDto;
import com.idk.diary.model.Diary;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Api(tags = "diary")
public interface DiaryApi {

    @ApiOperation(
            value = "Creates a Diary",
            nickname = "createDiary",
            notes = "This operation creates a Diary",
            response = Diary.class,
            tags = {"diary"}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(code = 201, message = "Created", response = Diary.class),
                    @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
                    @ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
                    @ApiResponse(code = 403, message = "Forbidden", response = Error.class),
                    @ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
                    @ApiResponse(code = 409, message = "Conflict", response = Error.class),
                    @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class)
            }
    )
    @PostMapping(path = "/diary",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<?> create(
            @ApiParam(value = "The Diary to be created", required = true)
            @RequestBody
                    CreateDiaryDto createDiaryDto
    );




    @ApiOperation(
            value = "Deletes a Diary",
            nickname = "deleteDiary",
            notes = "This operation deletes a Diary",
            tags = {"diary"}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
                    @ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
                    @ApiResponse(code = 403, message = "Forbidden", response = Error.class),
                    @ApiResponse(code = 404, message = "Not Found", response = Error.class),
                    @ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
                    @ApiResponse(code = 409, message = "Conflict", response = Error.class),
                    @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class)
            }
    )
    @DeleteMapping(path = "/diary/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<Void> delete(
            @ApiParam(value = "Identifier of the Diary", required = true)
            @PathVariable("id")
                    UUID id
    );



    @ApiOperation(
            value = "Retrieve the List of Diaries",
            nickname = "listDiary",
            notes = "This operation returns List of Diaries",
            response = Diary.class,
            responseContainer = "List",
            tags = {"diary"}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Success", response = Diary.class),
                    @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
                    @ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
                    @ApiResponse(code = 403, message = "Forbidden", response = Error.class),
                    @ApiResponse(code = 404, message = "Not Found", response = Error.class),
                    @ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
                    @ApiResponse(code = 409, message = "Conflict", response = Error.class),
                    @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class)
            }
    )
    @GetMapping(path = "/diary",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<List<Diary>> retrieve();





    @ApiOperation(
            value = "Retrieve the Diary",
            nickname = "retrieveDiary",
            notes = "This operation returns a Diary for an id",
            response = Diary.class,
            tags = {"diary"}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Success", response = Diary.class),
                    @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
                    @ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
                    @ApiResponse(code = 403, message = "Forbidden", response = Error.class),
                    @ApiResponse(code = 404, message = "Not Found", response = Error.class),
                    @ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
                    @ApiResponse(code = 409, message = "Conflict", response = Error.class),
                    @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class)
            }
    )
    @GetMapping(path = "/diary/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<Diary> retrieve(
            @ApiParam(value = "Identifier of the Diary", required = true)
            @PathVariable("id")
                    UUID id
    );






    @ApiOperation(
            value = "Partially Update the Diary",
            nickname = "patchDiary",
            notes = "This operation updates a diary partially",
            response = Diary.class,
            tags = {"diary"}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Success", response = Diary.class),
                    @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
                    @ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
                    @ApiResponse(code = 403, message = "Forbidden", response = Error.class),
                    @ApiResponse(code = 404, message = "Not Found", response = Error.class),
                    @ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
                    @ApiResponse(code = 409, message = "Conflict", response = Error.class),
                    @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class)
            }
    )
    @PatchMapping(path = "/diary/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<Diary> patch(
            @ApiParam(value = "Identifier of the Diary", required = true)
            @PathVariable("id")
                    UUID id,
            @ApiParam(value = "The Diary with to be updated fields", required = true)
            @RequestBody
                    PatchDiaryDto patchDiaryDto
    );

}
