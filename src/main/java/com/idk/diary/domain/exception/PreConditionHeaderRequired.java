package com.idk.diary.domain.exception;

public class PreConditionHeaderRequired extends RuntimeException {

    public static final String MESSAGE = "Pre-condition header %s is required. ";
    public PreConditionHeaderRequired(String headerName) {
        super(String.format(MESSAGE, headerName));
    }

}
