package com.idk.diary.api.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;


public record DeleteDiaryDto(
        UUID id
) {

}
