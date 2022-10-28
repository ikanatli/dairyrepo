package com.idk.diary.api.dto.converter.request;

import com.idk.diary.api.dto.PatchDiaryDto;
import com.idk.diary.domain.model.DiaryId;
import com.idk.diary.domain.services.impl.UpdateDiaryCommand;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PatchDiaryDtoToDiaryConverter {
    public UpdateDiaryCommand.DiaryUpdate convert(UUID id, Integer version, PatchDiaryDto source) {
        return new UpdateDiaryCommand.DiaryUpdate() {
            @Override
            public DiaryId getDiaryId() {
                return DiaryId.from(id);
            }

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

            @Override
            public Integer getVersion() {
                return version;
            }
        };
    }
}
