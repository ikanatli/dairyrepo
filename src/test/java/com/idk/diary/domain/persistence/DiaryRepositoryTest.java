package com.idk.diary.domain.persistence;

import com.idk.diary.domain.model.Diary;
import com.idk.diary.domain.model.DiaryTestDataBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
class DiaryRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private DiaryRepository diaryRepository;

    @Test
    void givenDiaryWithLongestNameField_whenCreateDiary_thenCreateNewDiarySuccessfully(){
        // given
        Diary diary = new DiaryTestDataBuilder().withTestDefaults().name("ilkerilkerilkerilkerilkerilkerilkerilker").build();

        // when
        Diary actualDiary = diaryRepository.save(diary);
        diaryRepository.flush();

        // then
        Diary diaryInDB = testEntityManager.find(Diary.class, diary.getId());
        assertThat(actualDiary.getId()).isEqualTo(diaryInDB.getId());
        assertThat(actualDiary.getText()).isEqualTo(diaryInDB.getText());
        assertThat(actualDiary.getName()).isEqualTo(diaryInDB.getName());
        assertThat(actualDiary.getLocation()).isEqualTo(diaryInDB.getLocation());
    }

    @Test
    void givenDiaryWithOverSizedNameField_whenCreateDiary_thenThrow(){
        // given
        Diary diary = new DiaryTestDataBuilder().withTestDefaults().name("ilkerilkerilkerilkerilkerilkerilkerilker1").build();
        // when
        // then
        ConstraintViolationException constraintViolationException = assertThrows(ConstraintViolationException.class, () -> {
            diaryRepository.save(diary);
            diaryRepository.flush();
        });
        assertThat(constraintViolationException.getMessage()).contains("Name length must not be at least 4 and at most 40 characters.");
    }


    @Test
    void givenDiaryWithShortNameField_whenCreateDiary_thenThrow(){
        // given
        Diary diary = new DiaryTestDataBuilder().withTestDefaults().name("il").build();
        // when
        // then
        ConstraintViolationException constraintViolationException = assertThrows(ConstraintViolationException.class, () -> {
            diaryRepository.save(diary);
            diaryRepository.flush();
        });
        assertThat(constraintViolationException.getMessage()).contains("Name length must not be at least 4 and at most 40 characters.");
    }

    @Test
    void givenDiaryWithNullNameField_whenCreateDiary_thenThrow(){
        // given
        Diary diary = new DiaryTestDataBuilder().withTestDefaults().name(null).build();

        // when
        // NOTE TO ALL OF US!!! if just save() the entity it will not be flushed yet. So,
        // Diary actualDiary = diaryRepository.save(diary);
        // The code above will work when name field of entity is null. This leads to wrong test result.
        // Hibernate ORM and a few other ORMs try to batch as many operations as possible when accessing the database. It is likely that the actual entity "persist" operation only happens when you call flush() or when the transaction commits.

        // when
        // then
        ConstraintViolationException constraintViolationException = assertThrows(ConstraintViolationException.class, () -> {
            diaryRepository.save(diary);
            diaryRepository.flush();
        });
        assertThat(constraintViolationException.getMessage()).contains("Name must not be empty.");
    }

    @Test
    void givenDefaultDiary_whenCreateDiary_thenCreateNewDiarySuccessfully(){
        // given
        Diary diary = new DiaryTestDataBuilder().withTestDefaults().build();

        // when
        Diary actualDiary = diaryRepository.save(diary);

        // then
        Diary diaryInDB = testEntityManager.find(Diary.class, diary.getId());
        assertThat(actualDiary.getId()).isEqualTo(diaryInDB.getId());
        assertThat(actualDiary.getText()).isEqualTo(diaryInDB.getText());
        assertThat(actualDiary.getName()).isEqualTo(diaryInDB.getName());
        assertThat(actualDiary.getLocation()).isEqualTo(diaryInDB.getLocation());
    }


    @Test
    void givenNonExistingDiaryId_whenFindById_thenReturnNothing(){
        // given
        Diary existingDiary = new DiaryTestDataBuilder().withTestDefaults().build();

        // when
        Optional<Diary> actual = diaryRepository.findById(existingDiary.getId());

        // then
        assertFalse(actual.isPresent());
    }


    @Test
    void givenExistingDiaryId_whenFindById_thenReturnDiarySuccessfully(){
        // given
        Diary existingDiary = new DiaryTestDataBuilder().withTestDefaults().build();
        testEntityManager.persist(existingDiary);
        testEntityManager.flush();

        // when
        Optional<Diary> actual = diaryRepository.findById(existingDiary.getId());

        // then
        assertTrue(actual.isPresent());
        assertThat(actual).hasValueSatisfying(actualDiary -> {
            assertThat(actualDiary.getId()).isEqualTo(existingDiary.getId());
            assertThat(actualDiary.getName()).isEqualTo(existingDiary.getName());
            assertThat(actualDiary.getText()).isEqualTo(existingDiary.getText());
            assertThat(actualDiary.getLocation()).isEqualTo(existingDiary.getLocation());
        });

    }

}