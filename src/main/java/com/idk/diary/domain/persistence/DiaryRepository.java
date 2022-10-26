package com.idk.diary.domain.persistence;

import com.idk.diary.domain.model.Diary;
import com.idk.diary.domain.model.DiaryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, DiaryId> {

    @Override
    Optional<Diary> findById(DiaryId diaryId);

    long deleteById_idValue(UUID uuid);
}
