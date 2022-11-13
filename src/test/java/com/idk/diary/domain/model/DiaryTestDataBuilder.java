package com.idk.diary.domain.model;

import lombok.RequiredArgsConstructor;

import java.time.Instant;

@RequiredArgsConstructor
public class DiaryTestDataBuilder extends Diary.DiaryBuilder {

    public Diary.DiaryBuilder withTestDefaults() {
        return Diary.builder()
                .id(DiaryId.randomUUID())
                .name(String.format("Spock.%s", Instant.now().toString()))
                .location("Vulcan")
                .text("Where no man has gone before")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .version(1);
    }
}