package com.idk.diary.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(
        description = "Diary represents a single diary to be recorded. Can be a daily story, an idea, thoughts, my child said... kind of memories etc."
)
public record GetDiaryDto(

        @Schema(defaultValue = "Unique identifier of the diary")
        @JsonProperty("id")
        UUID id,

        @Schema(defaultValue = "Explanatory name of the diary")
        @JsonProperty("name")
        String name,

        @Schema(defaultValue = "Place where you want to tag.")
        @JsonProperty("location")
        String location,

        @Schema(defaultValue = "Text/Thoughts of the diary.")
        @JsonProperty("text")
        String text,

        @JsonIgnore
        @JsonProperty("version")
        Integer version

) {


}
