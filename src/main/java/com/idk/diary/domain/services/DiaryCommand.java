package com.idk.diary.domain.services;

@FunctionalInterface
public interface DiaryCommand<Input, Result> {
    Result execute(Input input);
}
