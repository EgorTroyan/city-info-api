package ru.troyan.cityinfoapi.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import ru.troyan.cityinfoapi.service.LatinPhraseService;

import java.io.IOException;
import java.util.Random;

@Service
public class LatinPhraseWikiServiceImpl implements LatinPhraseService {

    private static final String WIKI_LATIN_PHRASES_LINK = "https://en.m.wikipedia.org/wiki/List_of_Latin_phrases_(full)";

    @Override
    public String getRandomLatinPhrase() throws IOException {
        Document doc = Jsoup.connect(WIKI_LATIN_PHRASES_LINK)
                .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0")
                .get();
        Element table = doc.select(".wikitable").get(0);
        Elements elements = table.select("tr");
        Elements elementsOfRandomElement = null;
        while (elementsOfRandomElement == null || elementsOfRandomElement.size() != 3) {
            elementsOfRandomElement = getElementsOfRandomLatinPhrase(elements);
        }
        String message = "Did you know, that " + elementsOfRandomElement.get(0).wholeText() + " in Latin means - " + elementsOfRandomElement.get(1).wholeText();
        System.out.println(message);
        return message;
    }

    private Elements getElementsOfRandomLatinPhrase(Elements fullTable) {
        Element randomElement = fullTable.get(new Random().nextInt(fullTable.size()));
        return randomElement.select("td");
    }
}
