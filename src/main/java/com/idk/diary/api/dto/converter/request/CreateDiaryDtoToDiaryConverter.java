package com.idk.diary.api.dto.converter.request;

import com.idk.diary.api.dto.CreateDiaryDto;
import com.idk.diary.domain.model.Diary;
import org.springframework.core.convert.converter.Converter;

public class CreateDiaryDtoToDiaryConverter implements Converter<CreateDiaryDto, Diary> {
    @Override
    public Diary convert(CreateDiaryDto source) {
        return Diary.builder()
                .name(source.name())
                .location(source.location())
                .text(source.text())
                .build();
    }
}
