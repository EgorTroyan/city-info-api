package ru.troyan.cityinfoapi.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
//@NoArgsConstructor
//@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class CityInfoRequest {

    @NotEmpty(message = "Field must be not empty")
    String name;
    @NotEmpty(message = "Field must be not empty")
    String country;

}
