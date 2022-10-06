package com.idk.diary.api.dto;

import java.util.UUID;

public record GetDiaryDto(
        UUID id,
        String name,
        String location
) {


}
