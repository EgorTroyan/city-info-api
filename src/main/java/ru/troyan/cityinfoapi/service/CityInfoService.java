package ru.troyan.cityinfoapi.service;

import ru.troyan.cityinfoapi.exception.CityInfoNotFoundException;
import ru.troyan.cityinfoapi.model.CityInfoResponse;

import java.io.IOException;

public interface CityInfoService {

    CityInfoResponse getCityInfo(String city, String country) throws CityInfoNotFoundException, IOException;
}
