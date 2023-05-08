package ru.troyan.cityinfoapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CityInfoResponse {

    private String extract;
    private String phrase;
}
