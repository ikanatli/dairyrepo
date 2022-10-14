package com.idk.diary.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;


@ApiModel(
        description = "Used when an API throws an Error, typically with a HTTP error response-code (3xx, 4xx, 5xx)"
)
@Data
@JsonInclude(Include.NON_NULL)
public class Error {
    @JsonProperty("code")
    private String code = null;

    @JsonProperty("reason")
    private String reason = null;

    @JsonProperty("message")
    private String message = null;

    @JsonProperty("status")
    private String status = null;

    @JsonProperty("referenceError")
    private String referenceError = null;


}

