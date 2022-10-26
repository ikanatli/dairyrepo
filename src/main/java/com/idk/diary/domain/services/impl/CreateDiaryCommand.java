package com.idk.diary.domain.services.impl;

import com.idk.diary.domain.model.Diary;
import com.idk.diary.domain.persistence.DiaryRepository;
import com.idk.diary.domain.services.DiaryCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateDiaryCommand implements DiaryCommand<Diary, Diary> {

    private final DiaryRepository diaryRepository;

    @Override
    public Diary execute(Diary diary) {
        return save(diary);
    }

    private Diary save(Diary diary) {
        return diaryRepository.save(diary);
    }
}
