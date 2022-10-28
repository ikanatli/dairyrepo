package com.idk.diary.domain.services.impl;

import com.idk.diary.domain.exception.DiaryNotFoundException;
import com.idk.diary.domain.model.Diary;
import com.idk.diary.domain.model.DiaryId;
import com.idk.diary.domain.persistence.DiaryRepository;
import com.idk.diary.domain.services.DiaryCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Component
@RequiredArgsConstructor
public class DeleteDiaryCommand implements DiaryCommand<DiaryId, Boolean> {

    private final DiaryRepository diaryRepository;

    @Transactional
    public Boolean execute(DiaryId diaryId) {
        Diary existingDiary = diaryRepository.findById(diaryId).orElse(null);
        if (existingDiary == null) {
            log.info("DeleteDiaryCommand -> Could not find diary with id {}", diaryId);
            throw new DiaryNotFoundException(diaryId);
        }
        return delete(diaryId);
    }

    private boolean delete(DiaryId diaryId) {
        log.info("DeleteDiaryCommand -> Delete diary started for {}", diaryId);
        long deletedCount = diaryRepository.deleteById_idValue(diaryId.getIdValue());
        log.info("DeleteDiaryCommand -> {} of diaries has been deleted.", deletedCount);
        return deletedCount > 0;
    }
}
