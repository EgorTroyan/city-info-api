package ru.troyan.cityinfoapi.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ErrorResponse {
    private String message;
    private String phrase;
}
