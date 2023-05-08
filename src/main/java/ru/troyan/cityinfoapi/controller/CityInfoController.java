package ru.troyan.cityinfoapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.troyan.cityinfoapi.exception.CityInfoNotFoundException;
import ru.troyan.cityinfoapi.exception.InvalidCoordinateValueException;
import ru.troyan.cityinfoapi.model.CityInfoResponse;
import ru.troyan.cityinfoapi.service.CityInfoService;

import java.io.IOException;

@RestController
public class CityInfoController {

    CityInfoService cityInfoService;

    public CityInfoController(CityInfoService cityInfoService) {
        this.cityInfoService = cityInfoService;
    }

    @GetMapping("/city-info")
    public CityInfoResponse getCityInfo(@RequestParam(name = "city") String city,
                                        @RequestParam(name = "country") String country,
                                        @RequestParam(name = "latitude") String latitude,
                                        @RequestParam(name = "longitude") String longitude) throws CityInfoNotFoundException, IOException, InvalidCoordinateValueException {
        return cityInfoService.getCityInfo(city, country, latitude, longitude);

    }
}
