package com.idk.diary.domain.dto;


import com.idk.diary.api.dto.CreateDiaryDto;
import lombok.Builder;


public class CreateDiaryDtoTestDataBuilder {

    private CreateDiaryDtoTestDataBuilder() {
    }

   @Builder(buildMethodName = "buildEmpty", builderMethodName = "anEmptyCreateDiaryDto", setterPrefix = "with")
   private static CreateDiaryDto buildEmptyCreateDiaryDto(String name, String text, String location) {
               return new CreateDiaryDto(name, text, location);
   }


    @Builder(buildMethodName = "buildDefault",builderMethodName = "aDefaultCreateDiaryDto", setterPrefix = "with")
    private static CreateDiaryDto buildDefaultCreateDiaryDto(String name, String text, String location) {
        return new CreateDiaryDto(
                name.isEmpty() ? "John" : name,
                text.isEmpty() ? "John is busy now!" : text,
                location.isEmpty() ? "USA" : location
        );
    }

}
