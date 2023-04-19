package ru.troyan.cityinfoapi.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.net.URL;
import java.util.List;

@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class CityInfoResponse {

    String id;
    String text;
}
