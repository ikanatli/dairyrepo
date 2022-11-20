package com.idk.diary.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        description = "Diary represents a single diary to be recorded. Can be a daily story, an idea, thoughts, my child said... kind of memories etc."
)
public record DeleteDiaryDto(
) {

}
