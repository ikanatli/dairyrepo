package com.idk.diary.domain.services.impl;

import com.idk.diary.domain.exception.DiaryNotFoundException;
import com.idk.diary.domain.model.Diary;
import com.idk.diary.domain.model.DiaryId;
import com.idk.diary.domain.persistence.DiaryRepository;
import com.idk.diary.domain.services.DiaryQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Component
@RequiredArgsConstructor
public class RetrieveDiaryQuery implements DiaryQuery<DiaryId, Diary> {

    private final DiaryRepository diaryRepository;

    @Transactional(readOnly = true)
    @Override
    public Diary execute(DiaryId diaryId) {
        Diary foundDiary = diaryRepository.findById(diaryId).orElse(null);
        if (foundDiary == null) {
            throw new DiaryNotFoundException(diaryId);
        }
        return foundDiary;
    }

}
