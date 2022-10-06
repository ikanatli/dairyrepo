package com.idk.diary.api.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

public record CreateDiaryDto(
        UUID id,
        String name,
        String text,
        String location
) {
}
