package com.idk.diary.domain.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.AttributeConverter;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

/**
 * Since a primary key of a Diary is composition of the user id and diary id then
 * we use DiaryId to define/find exact diary.
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class DiaryId implements Serializable, Comparable<DiaryId> {

    private UUID idValue;

    public static DiaryId randomUUID() {
        return new DiaryId(UUID.randomUUID());
    }

    public static DiaryId from(UUID idValue) {
        return new DiaryId(idValue);
    }

    @Override
    public int compareTo(DiaryId diaryId) {
        return this.idValue.compareTo(diaryId.getIdValue());
    }

    @Override
    public String toString() {
        return String.valueOf(idValue);
    }

    public static class DbConverter implements AttributeConverter<DiaryId, UUID> {

        @Override
        public UUID convertToDatabaseColumn(DiaryId id) {
            return id.idValue;
        }

        @Override
        public DiaryId convertToEntityAttribute(UUID value) {
            return new DiaryId(value);
        }
    }

}
