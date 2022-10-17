package com.idk.diary.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;


@ApiModel(
        description = "Used when an API throws an Error, typically with a HTTP error response-code (3xx, 4xx, 5xx)"
)
@Data
@JsonInclude(Include.NON_NULL)
@Builder
public class ErrorDto {
    @JsonProperty("code")
    private int code;

    @JsonProperty("title")
    private String title;

    @JsonProperty("reason")
    private String reason;

    @JsonProperty("message")
    private String message;

    @JsonProperty("status")
    private String status;

    @JsonProperty("referenceError")
    private String referenceError;


}

