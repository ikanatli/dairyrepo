package com.idk.diary.api.dto.converter.request;

import com.idk.diary.api.dto.PatchDiaryDto;
import com.idk.diary.domain.model.Diary;
import com.idk.diary.domain.model.DiaryId;
import com.idk.diary.domain.services.impl.UpdateDiaryCommand;
import org.springframework.core.convert.converter.Converter;

public class PatchDiaryDtoToDiaryConverter implements Converter<PatchDiaryDto, UpdateDiaryCommand.DiaryUpdate> {
    @Override
    public UpdateDiaryCommand.DiaryUpdate convert(PatchDiaryDto source) {
        return new UpdateDiaryCommand.DiaryUpdate() {
            @Override
            public String getName() {
                return source.name();
            }

            @Override
            public String getLocation() {
                return source.location();
            }

            @Override
            public String getText() {
                return source.text();
            }
        };
    }
}
