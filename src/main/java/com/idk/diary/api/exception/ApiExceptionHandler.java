package com.idk.diary.api.exception;

import com.idk.diary.api.controller.DiaryController;
import com.idk.diary.api.dto.ErrorDto;
import com.idk.diary.domain.exception.DiaryAlreadyExistsException;
import com.idk.diary.domain.exception.DiaryNotFoundException;
import com.idk.diary.domain.exception.DiaryVersionNotMatchesToExistingRecord;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;

/**
 * - NOTE: RestControllerAdvice -> assignableTypes -> DiaryController.class = ControllerAdvice of Exception Handler
 * will only work for DiaryController.
 */

@RestControllerAdvice(assignableTypes = {DiaryController.class})
public class ApiExceptionHandler {

    @ExceptionHandler(DiaryVersionNotMatchesToExistingRecord.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    ErrorDto handleException(DiaryVersionNotMatchesToExistingRecord exception, HttpServletRequest request) {
        return ErrorDto.builder().
                code(HttpStatus.CONFLICT.value()).
                title(HttpStatus.CONFLICT.name()).
                referenceError(request.getRequestURI()).
                reason(exception.toString()).
                message(exception.getMessage()).
                build();
    }

    @ExceptionHandler(DiaryAlreadyExistsException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    ErrorDto handleException(DiaryAlreadyExistsException exception, HttpServletRequest request) {
        return ErrorDto.builder().
                code(HttpStatus.CONFLICT.value()).
                title(HttpStatus.CONFLICT.name()).
                referenceError(request.getRequestURI()).
                reason(exception.toString()).
                message(exception.getMessage()).
                build();
    }

    @ExceptionHandler(DiaryNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    ErrorDto handleException(DiaryNotFoundException exception, HttpServletRequest request) {
        return ErrorDto.builder().
                code(HttpStatus.NOT_FOUND.value()).
                title(HttpStatus.NOT_FOUND.name()).
                referenceError(request.getRequestURI()).
                reason(exception.toString()).
                message(exception.getMessage()).
                build();
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    ErrorDto handleException(HttpMediaTypeNotSupportedException exception, HttpServletRequest request) {
        return ErrorDto.builder().
                code(HttpStatus.BAD_REQUEST.value()).
                title(HttpStatus.BAD_REQUEST.name()).
                referenceError(request.getRequestURI()).
                reason(exception.toString()).
                message(exception.getMessage()).
                build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    ErrorDto handleException(IllegalArgumentException exception, HttpServletRequest request) {
        return ErrorDto.builder().
                code(HttpStatus.BAD_REQUEST.value()).
                title(HttpStatus.BAD_REQUEST.name()).
                referenceError(request.getRequestURI()).
                reason(exception.toString()).
                message(exception.getMessage()).
                build();
    }

    @ExceptionHandler(HttpMessageConversionException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    ErrorDto handleException(HttpMessageConversionException exception, HttpServletRequest request) {
        return ErrorDto.builder().
                code(HttpStatus.BAD_REQUEST.value()).
                title(HttpStatus.BAD_REQUEST.name()).
                referenceError(request.getRequestURI()).
                reason(exception.toString()).
                message(exception.getMessage()).
                build();
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    ErrorDto handleException(MethodArgumentTypeMismatchException exception, HttpServletRequest request) {
        return ErrorDto.builder().
                code(HttpStatus.BAD_REQUEST.value()).
                title(HttpStatus.BAD_REQUEST.name()).
                referenceError(request.getRequestURI()).
                reason(exception.toString()).
                message(exception.getMessage()).
                build();
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    ErrorDto handleException(MethodArgumentNotValidException exception, HttpServletRequest request) {
        return ErrorDto.builder().
                code(HttpStatus.BAD_REQUEST.value()).
                title(HttpStatus.BAD_REQUEST.name()).
                referenceError(request.getRequestURI()).
                reason(exception.toString()).
                message(exception.getMessage()).
                build();
    }


    @ExceptionHandler({RuntimeException.class, Exception.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    ErrorDto handleException(Exception exception, HttpServletRequest request) {
        return ErrorDto.builder().
                code(HttpStatus.INTERNAL_SERVER_ERROR.value()).
                title(HttpStatus.INTERNAL_SERVER_ERROR.name()).
                referenceError(request.getRequestURI()).
                reason(exception.toString()).
                message(exception.getMessage()).
                build();
    }


}
