package com.idk.diary.domain.services;

public interface DiaryQuery<INPUT, RESULT> {
    RESULT execute(INPUT input);
}
