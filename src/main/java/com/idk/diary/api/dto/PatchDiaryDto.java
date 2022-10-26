package com.idk.diary.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

@ApiModel(
        description = "Diary represents a single diary to be recorded. Can be a daily story, an idea, thoughts, my child said... kind of memories etc.",
        value = "Patch_Diary"
)
public record PatchDiaryDto(

        @JsonProperty("name")
        String name,

        @JsonProperty("text")
        String text,

        @JsonProperty("location")
        String location
) {
}
