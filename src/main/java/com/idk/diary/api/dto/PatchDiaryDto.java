package com.idk.diary.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@ApiModel(
        description = "Diary represents a single diary to be recorded. Can be a daily story, an idea, thoughts, my child said... kind of memories etc.",
        value = "Patch_Diary"
)
public record PatchDiaryDto(

        @ApiModelProperty(required = true, value = "Explanatory name of the diary")
        @JsonProperty("name")
        @NotBlank(message = "Name must not be empty.")
        @Length(min = 4, max = 40, message = "Name length must not be at least 4 and at most 40 characters.")
        String name,

        @JsonProperty("text")
        String text,

        @JsonProperty("location")
        String location
) {
}
