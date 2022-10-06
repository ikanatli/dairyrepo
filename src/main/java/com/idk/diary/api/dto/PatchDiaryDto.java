package com.idk.diary.api.dto;

import java.util.UUID;

public record PatchDiaryDto(
        UUID id,
        String name,
        String text,
        String location
) {
}
