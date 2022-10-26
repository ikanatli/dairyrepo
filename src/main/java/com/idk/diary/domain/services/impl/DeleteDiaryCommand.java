package com.idk.diary.domain.services.impl;

import com.idk.diary.domain.exception.DiaryNotFoundException;
import com.idk.diary.domain.model.Diary;
import com.idk.diary.domain.persistence.DiaryRepository;
import com.idk.diary.domain.services.DiaryCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Component
@RequiredArgsConstructor
public class DeleteDiaryCommand implements DiaryCommand<Diary, Boolean> {

    private final DiaryRepository diaryRepository;

    @Transactional
    @Override
    public Boolean execute(Diary diary) {
        Diary existingDiary = diaryRepository.findById(diary.getId()).orElse(null);
        if (existingDiary == null) {
            log.info("DeleteDiaryCommand -> Could not find diary with id {}", diary.getName());
            throw new DiaryNotFoundException(diary.getId());
        }
        return delete(diary);
    }

    private boolean delete(Diary diary) {
        log.info("DeleteDiaryCommand -> Delete diary started for {}", diary.getName());
        long deletedCount = diaryRepository.deleteById_idValue(diary.getId().getIdValue());
        log.info("DeleteDiaryCommand -> {} of diaries has been deleted.", deletedCount);
        return deletedCount > 0;
    }
}
