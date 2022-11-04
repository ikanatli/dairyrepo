package com.idk.diary.domain.exception;

import com.idk.diary.domain.model.Diary;

public class PreConditionFailedForPatchDiary extends RuntimeException{

    public static final String MESSAGE = "Pre-conditional check for if-Match header conflicts withh current resource version where diary id is ";

    public PreConditionFailedForPatchDiary(Diary diary, Integer condition) {
        super(MESSAGE + diary.getId() + " where if-match is " + condition);
    }

}
