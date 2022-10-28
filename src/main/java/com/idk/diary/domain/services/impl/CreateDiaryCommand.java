package com.idk.diary.domain.services.impl;

import com.idk.diary.domain.model.Diary;
import com.idk.diary.domain.persistence.DiaryRepository;
import com.idk.diary.domain.services.DiaryCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class CreateDiaryCommand implements DiaryCommand<Diary, Diary> {

    private final DiaryRepository diaryRepository;

    @Override
    public Diary execute(Diary diary) {
        return save(diary);
    }

    private Diary save(Diary diary) {
        log.info("CreateDiaryCommand -> Diary creation requested from Repository with id {}", diary.getId());
        return diaryRepository.save(diary);
    }
}
