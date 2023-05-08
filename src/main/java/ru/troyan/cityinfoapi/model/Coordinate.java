package ru.troyan.cityinfoapi.model;


import lombok.Getter;
import ru.troyan.cityinfoapi.exception.InvalidCoordinateValueException;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;

@Getter
public class Coordinate {

    private static final int ACCURACY = 1;

    private final double latitude;

    private final double longitude;

    public static boolean isNearCoordinates(Coordinate first, Coordinate second) {
        return Math.abs(first.latitude - second.latitude) < ACCURACY &&
                Math.abs(first.longitude - second.longitude) < ACCURACY;
    }

    public Coordinate(String wikiCoordField) throws InvalidCoordinateValueException {
        Map.Entry<Double, Double> latAndLon = wikiCoordinateFormatToFloat(wikiCoordField);
        this.latitude = Math.round(latAndLon.getKey() * 100000d) / 100000d;
        this.longitude = Math.round(latAndLon.getValue() * 100000d) / 100000d;
    }

    public Coordinate(double latitude, double longitude) throws InvalidCoordinateValueException {
        if(latitude > 90 || latitude < -90 || longitude > 180 || longitude < -180)
            throw new InvalidCoordinateValueException("Invalid data. Latitude range [90:-90]; Longitude range [180:-180]");
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Map.Entry<Double, Double> wikiCoordinateFormatToFloat(String wikiCoordinate) throws InvalidCoordinateValueException {
        String[] temp = Arrays.stream(wikiCoordinate.split("\\|"))
                .map(s -> s.isEmpty() || s.equals(" ") ? "0" : s)
                .toArray(String[]::new);

        if (temp.length == 8) {
            double lat = 0;
            double lon = 0;
            for (int i = 0; i < 3; i++) {
                lat = lat + (Double.parseDouble(temp[i]) / Math.pow(60, i));
                lon = lon + (Double.parseDouble(temp[i + 4]) / Math.pow(60, i));
            }
            return new AbstractMap.SimpleEntry<> (
                    temp[3].equals("N") ? lat : 0 - lat,
                    temp[7].equals("E") ? lon : 0 - lon);
        }
        throw new InvalidCoordinateValueException("Bad wiki coordinate format: " + wikiCoordinate);
    }
}
