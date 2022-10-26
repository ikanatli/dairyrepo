package com.idk.diary.domain.exception;

import com.idk.diary.domain.model.DiaryId;

public class DiaryNotFoundException extends RuntimeException {

    public static final String MESSAGE = "Could not find diary where diary id is ";

    public DiaryNotFoundException(DiaryId diaryId) {
        super(MESSAGE + diaryId.getIdValue());
    }
}
