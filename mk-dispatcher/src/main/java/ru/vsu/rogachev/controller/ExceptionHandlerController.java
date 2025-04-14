package ru.vsu.rogachev.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.vsu.rogachev.exception.BusinessLogicException;
import ru.vsu.rogachev.utils.MessageUtils;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandlerController {

    private final MessageUtils messageUtils;

    @ExceptionHandler(BusinessLogicException.class)
    public void handleBusinessLogicException(BusinessLogicException ex) {
        messageUtils.sendMessage(ex.getChatId(), ex.getText());
    }
}
