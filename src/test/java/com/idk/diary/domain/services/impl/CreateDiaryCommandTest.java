package com.idk.diary.domain.services.impl;

import com.idk.diary.domain.model.Diary;
import com.idk.diary.domain.model.DiaryTestBuilder;
import com.idk.diary.domain.persistence.DiaryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CreateDiaryCommandTest {

    @Mock
    private DiaryRepository diaryRepository;

    @InjectMocks
    private CreateDiaryCommand underTestObjectCreateDiaryCommand;

    @Test
    void givenDefaultDiary_whenCreateDiary_thenCreateNewDiarySuccessfully() {
        // given
        var name = "01.01.2022";
        Diary diary = new DiaryTestBuilder().withTestDefaults().name(name).build();
        when(diaryRepository.save(diary)).thenReturn(diary);

        // when
        var actual = underTestObjectCreateDiaryCommand.execute(diary);

        // then
        assertEquals(actual, diary);
        verify(diaryRepository).save(diary);
        verifyNoMoreInteractions(diaryRepository);
    }

    // TODO
    @Test
    void givenNoDiaryId_whenCreateDiary_thenThrowException() {
    }

    // TODO
    @Test
    void givenExistingDiary_whenCreateDiary_thenThrowException() {
    }

    // TODO
    @Test
    void givenNoCreatedAtDiary_whenCreateDiary_thenThrowException() {
    }

    // TODO
    @Test
    void givenNoUpdatedAtDiary_whenCreateDiary_thenThrowException() {
    }

    // TODO
    @Test
    void givenNoNameDiary_whenCreateDiary_thenThrowException() {
    }

}