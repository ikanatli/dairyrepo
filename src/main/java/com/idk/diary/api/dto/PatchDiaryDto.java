package com.idk.diary.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Schema(
        description = "Diary represents a single diary to be recorded. Can be a daily story, an idea, thoughts, my child said... kind of memories etc."
)
public record PatchDiaryDto(

        @Schema(defaultValue = "Explanatory name of the diary", required = true)
        @JsonProperty("name")
        @NotBlank(message = "Name must not be empty.")
        @Length(min = 4, max = 40, message = "Name length must not be at least 4 and at most 40 characters.")
        String name,

        @Schema(defaultValue = "Text/Thoughts of the diary.", required = false)
        @JsonProperty("text")
        String text,

        @Schema(defaultValue = "Place where you want to tag.", required = false)
        @JsonProperty("location")
        String location
) {
}
