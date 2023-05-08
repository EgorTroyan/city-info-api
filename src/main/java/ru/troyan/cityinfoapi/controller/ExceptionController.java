package ru.troyan.cityinfoapi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.troyan.cityinfoapi.exception.CityInfoNotFoundException;
import ru.troyan.cityinfoapi.exception.InvalidCoordinateValueException;
import ru.troyan.cityinfoapi.model.ErrorResponse;
import ru.troyan.cityinfoapi.service.LatinPhraseService;

import java.io.IOException;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class ExceptionController {
    LatinPhraseService latinPhraseService;
    public ExceptionController(LatinPhraseService latinPhraseService) {
        this.latinPhraseService = latinPhraseService;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidCoordinateValueException.class)
    public ErrorResponse badRequest(InvalidCoordinateValueException exception) throws IOException {
        return new ErrorResponse(exception.getMessage(), latinPhraseService.getRandomLatinPhrase());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ErrorResponse badRequest(MissingServletRequestParameterException exception) throws IOException {
        return new ErrorResponse(exception.getMessage(), latinPhraseService.getRandomLatinPhrase());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CityInfoNotFoundException.class)
    public ErrorResponse error(CityInfoNotFoundException exception) throws IOException {
        return new ErrorResponse(exception.getMessage(), latinPhraseService.getRandomLatinPhrase());
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({RuntimeException.class, Exception.class})
    public ErrorResponse error(RuntimeException exception) {
        log.error("", exception);
        return new ErrorResponse("Информация о городе оказалась слишком интересной. " +
                "Наш специалист выехал на проверку. Попробуйте повторить запрос позже", "По пути он обязательно заскочит в Рим за свежей латинской фразой");
    }
}
