package com.idk.diary.domain.services.impl;

import com.idk.diary.domain.exception.DiaryNotFoundException;
import com.idk.diary.domain.model.Diary;
import com.idk.diary.domain.model.DiaryTestDataBuilder;
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
class RetrieveDiaryQueryTest {

    @Mock
    private DiaryRepository diaryRepository;

    @InjectMocks
    private RetrieveDiaryQuery underTestObjectRetrieveDiaryQuery;

    @Test
    void givenExistingDiaryId_whenRetrieveDiary_thenRetrieveExistingDiarySuccessfully() {
        // given
        Diary existingDiary = new DiaryTestDataBuilder().withTestDefaults().build();
        when(diaryRepository.findById(existingDiary.getId())).thenReturn(Optional.of(existingDiary));

        // when
        var actual = underTestObjectRetrieveDiaryQuery.execute(existingDiary.getId());

        // then
        assertEquals(actual.getId(), existingDiary.getId());
        verify(diaryRepository).findById(existingDiary.getId());
        verifyNoMoreInteractions(diaryRepository);

    }


    @Test
    void givenNonExistingDiaryId_whenRetrieveDiary_thenThrowException() {
        // given
        Diary existingDiary = new DiaryTestDataBuilder().withTestDefaults().build();
        when(diaryRepository.findById(existingDiary.getId())).thenReturn(Optional.empty());

        // when
        // then
        assertThrows(DiaryNotFoundException.class,
                () -> underTestObjectRetrieveDiaryQuery.execute(existingDiary.getId()),
                DiaryNotFoundException.MESSAGE);
        verify(diaryRepository).findById(existingDiary.getId());
        verifyNoMoreInteractions(diaryRepository);

    }

}