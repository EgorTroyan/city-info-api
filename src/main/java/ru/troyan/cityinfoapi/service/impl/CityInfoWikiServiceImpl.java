package ru.troyan.cityinfoapi.service.impl;

import io.github.fastily.jwiki.core.NS;
import io.github.fastily.jwiki.core.Wiki;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import ru.troyan.cityinfoapi.exception.CityInfoNotFoundException;
import ru.troyan.cityinfoapi.exception.InvalidCoordinateValueException;
import ru.troyan.cityinfoapi.model.CityInfoResponse;
import ru.troyan.cityinfoapi.service.CityInfoService;
import ru.troyan.cityinfoapi.service.CoordinateCityCheck;
import ru.troyan.cityinfoapi.service.LatinPhraseService;

import java.io.IOException;
import java.util.List;

@Service
@Getter
@Setter
public class CityInfoWikiServiceImpl implements CityInfoService {

    private final Wiki wiki;

    private final LatinPhraseService latinPhraseService;
    private final CoordinateCityCheck coordinateCityCheck;

    public CityInfoWikiServiceImpl(LatinPhraseService latinPhraseService, CoordinateCityCheck coordinateCityCheck, Wiki wiki) {
        this.latinPhraseService = latinPhraseService;
        this.coordinateCityCheck = coordinateCityCheck;
        this.wiki = wiki;
    }

    @Override
    public CityInfoResponse getCityInfo(String city, String country, String latitude, String longitude) throws CityInfoNotFoundException, IOException, InvalidCoordinateValueException {
        List<String> links = wiki.search(city + " " + country,3, NS.MAIN);//
        for(String page : links) {
            if(coordinateCityCheck.isWikiInfoContainCoordinates(page)) {
                if(coordinateCityCheck.isCoordinatesFromPageNearSearching(page, latitude, longitude)) {
                    return new CityInfoResponse(wiki.getTextExtract(page), latinPhraseService.getRandomLatinPhrase());
                } else {
                    throw new CityInfoNotFoundException("The coordinates " + latitude + "|" + longitude + " do not correspond to the city " + city);
                }
            }
        }
            throw new CityInfoNotFoundException("City " + city + " not found.");
    }
}
