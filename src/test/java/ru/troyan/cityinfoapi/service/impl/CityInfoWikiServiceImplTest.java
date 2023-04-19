package ru.troyan.cityinfoapi.service.impl;

import org.junit.jupiter.api.Test;
import ru.troyan.cityinfoapi.exception.CityInfoNotFoundException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CityInfoWikiServiceImplTest {

    CityInfoWikiServiceImpl service = new CityInfoWikiServiceImpl();

    @Test
    void getCityInfo() throws CityInfoNotFoundException, IOException {

        System.out.println(service.getCityInfo("Воркута", "Россия"));
    }
}