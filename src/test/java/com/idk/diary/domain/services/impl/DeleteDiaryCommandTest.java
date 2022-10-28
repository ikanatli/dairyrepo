package com.idk.diary.domain.services.impl;

import com.idk.diary.domain.exception.DiaryNotFoundException;
import com.idk.diary.domain.model.Diary;
import com.idk.diary.domain.model.DiaryTestBuilder;
import com.idk.diary.domain.persistence.DiaryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteDiaryCommandTest {

    @Mock
    private DiaryRepository diaryRepository;

    @InjectMocks
    private DeleteDiaryCommand underTestObjectDeleteDiaryCommand;

    @Test
    void givenExistingDiaryId_whenDeleteDiary_thenDeleteDiarySuccessfully() {
        // given
        var name = "01.01.2022";
        Diary diary = new DiaryTestBuilder().withTestDefaults().name(name).build();
        when(diaryRepository.findById(diary.getId())).thenReturn(Optional.of(diary));

        // when
        var actual = underTestObjectDeleteDiaryCommand.execute(diary.getId());

        // then
        assertFalse(actual);
        verify(diaryRepository, times(1)).deleteById_idValue(diary.getId().getIdValue());
        verifyNoMoreInteractions(diaryRepository);
    }


    @Test
    void givenNonExistingDiaryId_whenDeleteDiary_thenThrowException() {
        // given
        var name = "01.01.2022";
        Diary diary = new DiaryTestBuilder().withTestDefaults().name(name).build();
        when(diaryRepository.findById(diary.getId())).thenReturn(Optional.empty());

        // when
        // then
        assertThrows(DiaryNotFoundException.class,
                () -> underTestObjectDeleteDiaryCommand.execute(diary.getId()),
                DiaryNotFoundException.MESSAGE);
        verify(diaryRepository).findById(diary.getId());
        verifyNoMoreInteractions(diaryRepository);
    }

}