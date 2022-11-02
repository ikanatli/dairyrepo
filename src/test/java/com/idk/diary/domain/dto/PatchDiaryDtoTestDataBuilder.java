package com.idk.diary.domain.dto;


import com.idk.diary.api.dto.PatchDiaryDto;
import lombok.Builder;


public class PatchDiaryDtoTestDataBuilder {

    private PatchDiaryDtoTestDataBuilder() {
    }

    @Builder
    private static PatchDiaryDto build(
            String name,
            String text,
            String location
    ) {
        return new PatchDiaryDto(
                name.isEmpty() ? "John" : name,
                text.isEmpty() ? "John is busy now!" : text,
                location.isEmpty() ? "USA" : location
        );
    }

}
