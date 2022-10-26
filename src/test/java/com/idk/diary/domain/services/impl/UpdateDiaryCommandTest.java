package com.idk.diary.domain.services.impl;

import com.idk.diary.domain.exception.DiaryNotFoundException;
import com.idk.diary.domain.exception.DiaryVersionNotMatchesToExistingRecord;
import com.idk.diary.domain.model.Diary;
import com.idk.diary.domain.model.DiaryTestBuilder;
import com.idk.diary.domain.persistence.DiaryRepository;
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

    @Test
    void givenExistingDiaryId_whenUpdateDiary_thenUpdateExistingDiarySuccessfully() {
        // given
        Diary existingDiary = new DiaryTestBuilder().withTestDefaults().build();
        Diary updatedDiary = new DiaryTestBuilder().id(existingDiary.getId())
                .name("UdatedDiaryName")
                .createdAt(existingDiary.getCreatedAt())
                .build();
        when(diaryRepository.findById(existingDiary.getId())).thenReturn(Optional.of(existingDiary));
        when(diaryRepository.save(updatedDiary)).thenReturn(updatedDiary);

        // when
        var actual = underTestObjectUpdateDiaryCommand.execute(updatedDiary);

        // then
        assertEquals(actual.getId(), existingDiary.getId());
        assertEquals(updatedDiary.getName(), "UdatedDiaryName");
    }


    @Test
    void givenNonExistingDiaryId_whenUpdateDiary_thenThrowException() {
        // given
        Diary existingDiary = new DiaryTestBuilder().withTestDefaults().build();
        when(diaryRepository.findById(existingDiary.getId())).thenReturn(Optional.empty());

        // when
        // then
        assertThrows(DiaryNotFoundException.class,
                () -> underTestObjectUpdateDiaryCommand.execute(existingDiary),
                DiaryNotFoundException.MESSAGE);
        verify(diaryRepository).findById(existingDiary.getId());
        verifyNoMoreInteractions(diaryRepository);

    }

    @Test
    void givenExistingDiaryIdButOutdatedVersion_whenUpdateDiary_thenThrowException() {
        // given
        Diary existingDiary = new DiaryTestBuilder().withTestDefaults().version(2).build();
        Diary toBeUpdatedDiary = new DiaryTestBuilder().withTestDefaults()
                .id(existingDiary.getId()).name("UpdatedNameForOutdatedVersion").version(1).build();
        when(diaryRepository.findById(toBeUpdatedDiary.getId())).thenReturn(Optional.of(existingDiary));

        // when
        // then
        assertThrows(DiaryNotFoundException.class,
                () -> underTestObjectUpdateDiaryCommand.execute(toBeUpdatedDiary),
                DiaryVersionNotMatchesToExistingRecord.MESSAGE);
        verify(diaryRepository).findById(existingDiary.getId());
        verifyNoMoreInteractions(diaryRepository);

    }

}