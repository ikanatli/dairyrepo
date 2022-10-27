package com.idk.diary.api.dto.converter.response;

import com.idk.diary.api.dto.GetDiaryDto;
import com.idk.diary.domain.model.Diary;
import org.springframework.core.convert.converter.Converter;

public class DiaryToGetDiaryDtoConvertor implements Converter<Diary, GetDiaryDto> {

    @Override
    public GetDiaryDto convert(Diary source) {
        return new GetDiaryDto(
                source.getId().getIdValue(),
                source.getName(),
                source.getLocation(),
                source.getText()
                );
    }

}
