package com.idk.diary.api.dto;

import io.swagger.annotations.ApiModel;

@ApiModel(
        description = "Diary represents a single diary to be recorded. Can be a daily story, an idea, thoughts, my child said... kind of memories etc.",
        value = "Delete_Diary"
)
public record DeleteDiaryDto(
) {

}
