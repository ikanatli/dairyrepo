package com.idk.diary.domain.exception;

import com.idk.diary.domain.model.Diary;
import com.idk.diary.domain.services.impl.UpdateDiaryCommand;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

public class DiaryVersionNotMatchesToExistingRecord extends ObjectOptimisticLockingFailureException {

    public static final String MESSAGE = "Version mismatch causes Optimistic Lock failure for diary id ";

    public DiaryVersionNotMatchesToExistingRecord(UpdateDiaryCommand.DiaryUpdate diaryUpdate) {
        super(MESSAGE + diaryUpdate.getDiaryId() + " where version is " + diaryUpdate.getVersion(), ObjectOptimisticLockingFailureException.class);
    }

}
