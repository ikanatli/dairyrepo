package com.idk.diary.api.dto.converter.request;

import com.idk.diary.api.dto.CreateDiaryDto;
import com.idk.diary.domain.model.Diary;
import com.idk.diary.domain.model.DiaryId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Service
public class CreateDiaryDtoToDiaryConverter implements Converter<CreateDiaryDto, Diary> {
    @Override
    public Diary convert(CreateDiaryDto source) {
        return Diary.builder()
                .id(DiaryId.randomUUID())
                .name(source.name())
                .location(source.location())
                .text(source.text())
                .build();
    }
}
