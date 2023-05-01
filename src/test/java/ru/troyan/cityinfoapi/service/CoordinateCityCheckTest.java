package ru.troyan.cityinfoapi.service;

import io.github.fastily.jwiki.core.Wiki;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.troyan.cityinfoapi.exception.InvalidCoordinateValueException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CoordinateCityCheckTest {

    @Test
    void isWikiInfoContainCoordinates() {
        String page = "page";
        Wiki wiki = Mockito.mock(Wiki.class);
        Mockito.when(wiki.getPageText(page)).thenReturn("Some string with Coord|1|2|3|N|4|5|6|E|type");
        CoordinateCityCheck coordinateCityCheck = new CoordinateCityCheck(wiki);
        assertTrue(coordinateCityCheck.isWikiInfoContainCoordinates(page));
    }

    @Test
    void isWikiInfoNotContainCoordinates() {
        String page = "page";
        Wiki wiki = Mockito.mock(Wiki.class);
        Mockito.when(wiki.getPageText(page)).thenReturn("Some string without coordinate info");
        CoordinateCityCheck coordinateCityCheck = new CoordinateCityCheck(wiki);
        assertFalse(coordinateCityCheck.isWikiInfoContainCoordinates(page));
    }

    @Test
    void isCoordinatesNearSearching() throws InvalidCoordinateValueException {
        String page = "page";
        Wiki wiki = Mockito.mock(Wiki.class);
        Mockito.when(wiki.getPageText(page)).thenReturn("Some string about Moscow Coord|55|11|12|N|37|05|62|E|type");
        CoordinateCityCheck coordinateCityCheck = new CoordinateCityCheck(wiki);
        assertTrue(coordinateCityCheck.isCoordinatesFromPageNearSearching(page, "55.01", "37.02"));
    }

    @Test
    void isCoordinatesNotNearSearching() throws InvalidCoordinateValueException {
        String page = "page";
        Wiki wiki = Mockito.mock(Wiki.class);
        Mockito.when(wiki.getPageText(page)).thenReturn("Some string about Moscow Coord|55|11|12|N|37|05|62|E|type");
        CoordinateCityCheck coordinateCityCheck = new CoordinateCityCheck(wiki);
        assertFalse(coordinateCityCheck.isCoordinatesFromPageNearSearching(page, "57.01", "37.02"));
    }
}