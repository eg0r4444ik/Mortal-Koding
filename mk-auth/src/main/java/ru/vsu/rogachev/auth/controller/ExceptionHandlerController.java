package ru.vsu.rogachev.auth.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.vsu.rogachev.client.mk.dto.ResponseContainer;

import static ru.vsu.rogachev.exception.BusinessLogicExceptions.COMMON_LOGIC_EXCEPTION;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandlerController {

    @ExceptionHandler(RuntimeException.class)
    public <T> ResponseContainer<T> handleRuntimeException(RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseContainer.withException(COMMON_LOGIC_EXCEPTION);
    }

}
