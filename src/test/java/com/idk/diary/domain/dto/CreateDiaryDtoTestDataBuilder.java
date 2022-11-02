package com.idk.diary.domain.dto;


import com.idk.diary.api.dto.CreateDiaryDto;
import lombok.Builder;


public class CreateDiaryDtoTestDataBuilder {

    private CreateDiaryDtoTestDataBuilder() {
    }

    @Builder
    private static CreateDiaryDto build(
            String name,
            String text,
            String location
    ) {
        return new CreateDiaryDto(
                name.isEmpty() ? "John" : name,
                text.isEmpty() ? "John is busy now!" : text,
                location.isEmpty() ? "USA" : location
        );
    }

}
