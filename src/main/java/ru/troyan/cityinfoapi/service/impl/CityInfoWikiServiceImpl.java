package ru.troyan.cityinfoapi.service.impl;

import io.github.fastily.jwiki.core.NS;
import io.github.fastily.jwiki.core.WParser;
import io.github.fastily.jwiki.core.Wiki;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.troyan.cityinfoapi.exception.CityInfoNotFoundException;
import ru.troyan.cityinfoapi.model.CityInfoResponse;
import ru.troyan.cityinfoapi.service.CityInfoService;
import ru.troyan.cityinfoapi.service.LatinPhraseService;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@Service
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CityInfoWikiServiceImpl implements CityInfoService {

    Wiki wiki;
    @Autowired
    LatinPhraseService latinPhraseService;

    public CityInfoWikiServiceImpl() {
        wiki = new Wiki.Builder().build();
    }

    @Override
    public CityInfoResponse getCityInfo(String city, String country) throws CityInfoNotFoundException, IOException {
        List<String> links = wiki.search(city + " " + country,3, NS.MAIN).stream()
                .filter(s -> s.contains(city))
                .toList();

        if(links.size() == 0) {
            throw new CityInfoNotFoundException(latinPhraseService.getRandomLatinPhrase());
        }

        return new CityInfoResponse(links.get(0), wiki.getTextExtract(links.get(0)));
    }
}
