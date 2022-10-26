package com.idk.diary.domain.exception;

import com.idk.diary.domain.model.Diary;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

public class DiaryVersionNotMatchesToExistingRecord extends ObjectOptimisticLockingFailureException {

    public static final String MESSAGE = "Version mismatch causes Optimistic Lock failure for diary id ";

    public DiaryVersionNotMatchesToExistingRecord(Diary diary) {
        super(MESSAGE + diary.getId().getIdValue() + " where version is " + diary.getVersion(), ObjectOptimisticLockingFailureException.class);
    }

}
