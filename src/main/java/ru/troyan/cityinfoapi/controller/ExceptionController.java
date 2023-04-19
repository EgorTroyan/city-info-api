package ru.troyan.cityinfoapi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.troyan.cityinfoapi.exception.CityInfoNotFoundException;
import ru.troyan.cityinfoapi.model.ErrorResponse;
import ru.troyan.cityinfoapi.service.LatinPhraseService;

import java.io.IOException;

import static java.util.stream.Collectors.joining;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class ExceptionController {

    @Autowired
    LatinPhraseService latinPhraseService;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse badRequest(MethodArgumentNotValidException exception) {
        final BindingResult bindingResult = exception.getBindingResult();
        return new ErrorResponse(buildMessage(bindingResult));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CityInfoNotFoundException.class)
    public ErrorResponse error(CityInfoNotFoundException exception) throws IOException {
        return new ErrorResponse(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ErrorResponse error(RuntimeException exception) {
        log.error("", exception);
        return new ErrorResponse("Информация о городе оказалась слишком интересной. " +
                "Наш специалист выехал на проверку. Попробуйте повторить запрос позже");
    }

    private String buildMessage(BindingResult bindingResult) {
        return String.format("Error on %s, rejected errors [%s]",
                bindingResult.getTarget(),
                bindingResult.getAllErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(joining(";")));
    }
}
