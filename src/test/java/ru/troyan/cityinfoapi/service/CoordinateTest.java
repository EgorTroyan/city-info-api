package ru.troyan.cityinfoapi.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.troyan.cityinfoapi.exception.InvalidCoordinateValueException;
import ru.troyan.cityinfoapi.model.Coordinate;

import static org.junit.jupiter.api.Assertions.*;

class CoordinateTest {

    @Test
    void isNearCoordinates() throws InvalidCoordinateValueException {
        Coordinate coordinate1 = new Coordinate("51|30|26|N|00|07|39|W");
        Coordinate coordinate2 = new Coordinate(51.05, 00.09);
        assertTrue(Coordinate.isNearCoordinates(coordinate1, coordinate2));
    }
    @Test
    void isNotNearCoordinates() throws InvalidCoordinateValueException {
        Coordinate coordinate1 = new Coordinate("51|30|26|N|00|07|39|W");
        Coordinate coordinate2 = new Coordinate(51.05, 01.09);
        assertFalse(Coordinate.isNearCoordinates(coordinate1, coordinate2));
    }

    @Test
    void wikiCoordinateFormatToFloatRealCoordinate() throws InvalidCoordinateValueException {
        Coordinate coordinate = new Coordinate("51|30|26|N|00|07|39|W");
        Assertions.assertEquals(51.50722, coordinate.getLatitude());
        Assertions.assertEquals(-0.1275, coordinate.getLongitude());
    }

    @Test
    void wikiCoordinateFormatToFloatRealCoordinateWithEmptyField() throws InvalidCoordinateValueException {
        Coordinate coordinate = new Coordinate("51|30|26|N| |07|39|W");
        Assertions.assertEquals(51.50722, coordinate.getLatitude());
        Assertions.assertEquals(-0.1275, coordinate.getLongitude());
    }

    @Test
    void wikiCoordinateFormatToFloatRealCoordinateWithWrongFormat() {
        Assertions.assertThrows(InvalidCoordinateValueException.class, () -> new Coordinate("51.30|26|N| |07|39|W"));
    }

    @Test
    void decCoordinateFormatWithWrongLimit() {
        Assertions.assertThrows(InvalidCoordinateValueException.class, () -> new Coordinate(100, -180));
        Assertions.assertThrows(InvalidCoordinateValueException.class, () -> new Coordinate(90, -181));
    }

    @Test
    void decCoordinateFormatWithValidLimit() throws InvalidCoordinateValueException {
        Coordinate coordinate = new Coordinate(55.12345, 37.12345);
        Assertions.assertEquals(55.12345, coordinate.getLatitude());
        Assertions.assertEquals(37.12345, coordinate.getLongitude());
    }
}