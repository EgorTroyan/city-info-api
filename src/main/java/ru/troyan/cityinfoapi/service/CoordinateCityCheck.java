package ru.troyan.cityinfoapi.service;

import io.github.fastily.jwiki.core.Wiki;
import org.springframework.stereotype.Service;
import ru.troyan.cityinfoapi.exception.InvalidCoordinateValueException;
import ru.troyan.cityinfoapi.model.Coordinate;

@Service
public class CoordinateCityCheck {


    private final Wiki wiki;

    public CoordinateCityCheck(Wiki wiki) {
        this.wiki = wiki;
    }

    public boolean isWikiInfoContainCoordinates(String page) {
        return wiki.getPageText(page).contains("Coord");
    }

    public boolean isCoordinatesFromPageNearSearching(String page, String latitude, String longitude) throws InvalidCoordinateValueException {
        String pageText = wiki.getPageText(page);
        String wikiCoord = pageText.substring(pageText.indexOf("Coord|") + 6, pageText.indexOf("|type"));
        Coordinate wikiCoordinate = new Coordinate(wikiCoord);
        Coordinate decCoordinate = new Coordinate(Float.parseFloat(latitude), Float.parseFloat(longitude));
        return Coordinate.isNearCoordinates(wikiCoordinate, decCoordinate);
    }
}
