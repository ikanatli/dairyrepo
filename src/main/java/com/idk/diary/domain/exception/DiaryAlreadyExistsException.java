package com.idk.diary.domain.exception;

import com.idk.diary.domain.model.DiaryId;

public class DiaryAlreadyExistsException extends RuntimeException {
    public DiaryAlreadyExistsException(DiaryId diaryId) {
        super("Diary already exists where diary id is " + diaryId.getIdValue());
    }
}
