package com.idk.diary.domain.services.impl;

import com.idk.diary.domain.exception.DiaryNotFoundException;
import com.idk.diary.domain.exception.DiaryVersionNotMatchesToExistingRecord;
import com.idk.diary.domain.model.Diary;
import com.idk.diary.domain.model.DiaryId;
import com.idk.diary.domain.persistence.DiaryRepository;
import com.idk.diary.domain.services.DiaryCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Component
@RequiredArgsConstructor
public class UpdateDiaryCommand implements DiaryCommand<Diary, Diary> {

    private final DiaryRepository diaryRepository;

    @Transactional
    @Override
    public Diary execute(Diary diary) {
        Diary existingDiary = diaryRepository.findById(diary.getId()).orElse(null);
        if (existingDiary == null) {
            log.info("UpdateDiaryCommand -> Could not find diary with id {}", diary.getName());
            throw new DiaryNotFoundException(diary.getId());
        }
        return update(diary);
    }

    private Diary update(Diary diary) {
        try {
            return diaryRepository.save(diary);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new DiaryVersionNotMatchesToExistingRecord(diary);
        }

    }

    public interface DiaryUpdate{
        String getName();
        String getLocation();
        String getText();
    }

}
