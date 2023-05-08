package ru.troyan.cityinfoapi.service.impl;

import io.github.fastily.jwiki.core.NS;
import io.github.fastily.jwiki.core.Wiki;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.troyan.cityinfoapi.exception.CityInfoNotFoundException;
import ru.troyan.cityinfoapi.exception.InvalidCoordinateValueException;
import ru.troyan.cityinfoapi.service.CoordinateCityCheck;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class CityInfoWikiServiceImplTest {

    Wiki wiki;
    CoordinateCityCheck coordinateCityCheck;
    LatinPhraseWikiServiceImpl latinPhraseWikiService;

    @BeforeEach
    public void init() throws IOException, InvalidCoordinateValueException {
        wiki = Mockito.mock(Wiki.class);
        Mockito.when(wiki.getPageText("page")).thenReturn("Some string about Moscow Coord|55|11|12|N|37|05|62|E|type");
        Mockito.when(wiki.search("Moscow Russia", 3 , NS.MAIN)).thenReturn(new ArrayList<>(List.of("Moscow", "London")));
        Mockito.when(wiki.getTextExtract("Moscow")).thenReturn("City info");
        coordinateCityCheck = Mockito.mock(CoordinateCityCheck.class);
        Mockito.when(coordinateCityCheck.isWikiInfoContainCoordinates("Moscow")).thenReturn(true);
        Mockito.when(coordinateCityCheck.isWikiInfoContainCoordinates("London")).thenReturn(false);
        Mockito.when(coordinateCityCheck.isCoordinatesFromPageNearSearching("Moscow", "1", "2")).thenReturn(true);
        Mockito.when(coordinateCityCheck.isCoordinatesFromPageNearSearching("Moscow", "3", "4")).thenReturn(false);
        latinPhraseWikiService = Mockito.mock(LatinPhraseWikiServiceImpl.class);
        Mockito.when(latinPhraseWikiService.getRandomLatinPhrase()).thenReturn("Phrase");

    }
    @Test
    void getCityInfo() throws InvalidCoordinateValueException, IOException, CityInfoNotFoundException {
        CityInfoWikiServiceImpl cityInfoWikiService = new CityInfoWikiServiceImpl(latinPhraseWikiService, coordinateCityCheck, wiki);
        Assertions.assertEquals("City info", cityInfoWikiService.getCityInfo("Moscow", "Russia", "1", "2").getExtract());
        Assertions.assertEquals("Phrase", cityInfoWikiService.getCityInfo("Moscow", "Russia", "1", "2").getPhrase());
    }
    @Test
    void getCityInfoWithoutCoordinate() {
        CityInfoWikiServiceImpl cityInfoWikiService = new CityInfoWikiServiceImpl(latinPhraseWikiService, coordinateCityCheck, wiki);
        CityInfoNotFoundException e = Assertions.assertThrows(CityInfoNotFoundException.class, () -> cityInfoWikiService.getCityInfo("London", "Russia", "1", "2"));
        Assertions.assertTrue(e.getMessage().contains("not found"));
    }

    @Test
    void getCityInfoNotNearCoordinate() {
        CityInfoWikiServiceImpl cityInfoWikiService = new CityInfoWikiServiceImpl(latinPhraseWikiService, coordinateCityCheck, wiki);
        CityInfoNotFoundException e = Assertions.assertThrows(CityInfoNotFoundException.class, () -> cityInfoWikiService.getCityInfo("Moscow", "Russia", "3", "4"));
        Assertions.assertTrue(e.getMessage().contains("do not correspond"));
    }
}