package com.idk.diary.domain.services.impl;

import com.idk.diary.domain.exception.DiaryNotFoundException;
import com.idk.diary.domain.exception.DiaryVersionNotMatchesToExistingRecord;
import com.idk.diary.domain.model.Diary;
import com.idk.diary.domain.model.DiaryId;
import com.idk.diary.domain.model.DiaryTestBuilder;
import com.idk.diary.domain.persistence.DiaryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateDiaryCommandTest {

    @Mock
    private DiaryRepository diaryRepository;

    @InjectMocks
    private UpdateDiaryCommand underTestObjectUpdateDiaryCommand;

    private UpdateDiaryCommand.DiaryUpdate diaryUpdate;
    private Diary existingDiary;
    private UpdateDiaryCommand.DiaryUpdate diaryUpdateOutDated;

    @BeforeEach
    void setup(){
        existingDiary = new DiaryTestBuilder().withTestDefaults().build();
        diaryUpdate = new UpdateDiaryCommand.DiaryUpdate() {
            @Override
            public DiaryId getDiaryId() {
                return existingDiary.getId();
            }

            @Override
            public String getName() {
                return "Name Updated";
            }

            @Override
            public String getLocation() {
                return  "Location Updated";
            }

            @Override
            public String getText() {
                return  "Text Updated";
            }

            @Override
            public Integer getVersion() {
                return 1;
            }
        };

        diaryUpdateOutDated = new UpdateDiaryCommand.DiaryUpdate() {
            @Override
            public DiaryId getDiaryId() {
                return existingDiary.getId();
            }

            @Override
            public String getName() {
                return "Name Updated";
            }

            @Override
            public String getLocation() {
                return  "Location Updated";
            }

            @Override
            public String getText() {
                return  "Text Updated";
            }

            @Override
            public Integer getVersion() {
                return 2;
            }
        };
    }



    @Test
    void givenExistingDiaryId_whenUpdateDiary_thenUpdateExistingDiarySuccessfully() {
        // given

        existingDiary.setName(diaryUpdate.getName());
        existingDiary.setText(diaryUpdate.getText());
        existingDiary.setLocation(diaryUpdate.getLocation());
        existingDiary.setVersion(diaryUpdate.getVersion());
        when(diaryRepository.findById(existingDiary.getId())).thenReturn(Optional.of(existingDiary));
        when(diaryRepository.save(existingDiary)).thenReturn(existingDiary);

        // when
        var actual = underTestObjectUpdateDiaryCommand.execute(diaryUpdate);

        // then
        assertEquals(actual.getId(), existingDiary.getId());
        assertEquals(diaryUpdate.getName(), actual.getName());
    }


    @Test
    void givenNonExistingDiaryId_whenUpdateDiary_thenThrowException() {
        // given
        when(diaryRepository.findById(existingDiary.getId())).thenReturn(Optional.empty());

        // when
        // then
        assertThrows(DiaryNotFoundException.class,
                () -> underTestObjectUpdateDiaryCommand.execute(diaryUpdate),
                DiaryNotFoundException.MESSAGE);
        verify(diaryRepository).findById(existingDiary.getId());
        verifyNoMoreInteractions(diaryRepository);

    }

    @Test
    void givenExistingDiaryIdButOutdatedVersion_whenUpdateDiary_thenThrowException() {
        // given
        // when
        when(diaryRepository.findById(existingDiary.getId())).thenReturn(Optional.of(existingDiary));

        // then
        assertThrows(DiaryVersionNotMatchesToExistingRecord.class,
                () -> underTestObjectUpdateDiaryCommand.execute(diaryUpdateOutDated),
                DiaryVersionNotMatchesToExistingRecord.MESSAGE);
        verify(diaryRepository).findById(existingDiary.getId());
        verifyNoMoreInteractions(diaryRepository);

    }

}