package com.idk.diary.domain.dto;


import com.idk.diary.api.dto.PatchDiaryDto;
import lombok.Builder;


public class PatchDiaryDtoTestDataBuilder {

    private PatchDiaryDtoTestDataBuilder() {
    }

    @Builder(buildMethodName = "buildEmpty", builderMethodName = "anEmptyPatchDiaryDto", setterPrefix = "with")
    private static PatchDiaryDto buildEmptyPatchDiaryDto(String name, String text, String location) {
        return new PatchDiaryDto(name, text, location);
    }


    @Builder(buildMethodName = "buildDefault",builderMethodName = "aDefaultPatchDiaryDto", setterPrefix = "with")
    private static PatchDiaryDto buildDefaultPatchDiaryDto(String name, String text, String location) {
        return new PatchDiaryDto(
                name == null ? "John" : name,
                text == null ? "John is busy now!" : text,
                location == null ? "USA" : location
        );
    }

}
