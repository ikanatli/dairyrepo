package com.idk.diary.domain.services.impl;

import com.idk.diary.domain.exception.DiaryNotFoundException;
import com.idk.diary.domain.exception.DiaryVersionNotMatchesToExistingRecord;
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
public class UpdateDiaryCommand implements DiaryCommand<UpdateDiaryCommand.DiaryUpdate, Diary> {

    private final DiaryRepository diaryRepository;

    @Transactional
    public Diary execute(DiaryUpdate diaryUpdate) {
        Diary existingDiary = diaryRepository.findById(diaryUpdate.getDiaryId()).orElse(null);
        if (existingDiary == null) {
            log.info("UpdateDiaryCommand -> Could not find diary with id {}", diaryUpdate.getName());
            throw new DiaryNotFoundException(diaryUpdate.getDiaryId());
        }
        if(!existingDiary.getVersion().equals(diaryUpdate.getVersion())){
            log.info("UpdateDiaryCommand -> Diary version mismatch, outdated version is {}", diaryUpdate.getVersion());
            throw new DiaryVersionNotMatchesToExistingRecord(diaryUpdate);
        }

        existingDiary.setName(diaryUpdate.getName());
        existingDiary.setText(diaryUpdate.getText());
        existingDiary.setLocation(diaryUpdate.getLocation());
        return update(existingDiary);
    }

    private Diary update(Diary diary) {
        log.info("UpdateDiaryCommand -> Diary update requested from Repository.");
        return diaryRepository.save(diary);
    }

    public interface DiaryUpdate{
        DiaryId getDiaryId();
        String getName();
        String getLocation();
        String getText();
        Integer getVersion();
    }

}
