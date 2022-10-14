package com.idk.diary.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.UUID;

@ApiModel(
        description = "Diary represents a single diary to be recorded. Can be a daily story, an idea, thoughts, my child said... kind of memories etc.",
        value = "Create_Diary"
)
public record CreateDiaryDto(

        @ApiModelProperty(value = "Unique identifier of the diary")
        @JsonProperty("id")
        UUID id,

        @ApiModelProperty(required = true, value = "Explanatory name of the diary")
        @JsonProperty("name")
        String name,

        @JsonProperty("text")
        String text,

        @JsonProperty("location")
        String location
) {
}
