package ru.troyan.cityinfoapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.troyan.cityinfoapi.exception.CityInfoNotFoundException;
import ru.troyan.cityinfoapi.model.CityInfoResponse;
import ru.troyan.cityinfoapi.service.CityInfoService;

import java.io.IOException;

@RestController
public class CityInfoController {

    @Autowired
    CityInfoService cityInfoService;

    @GetMapping("/wikilocation")
    public CityInfoResponse getCityInfo(@RequestParam(name = "city") String city,
                                        @RequestParam(name = "country") String country) throws CityInfoNotFoundException, IOException {
        return cityInfoService.getCityInfo(city, country);

    }
}
