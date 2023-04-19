package ru.troyan.cityinfoapi.service.impl;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class LatinPhraseWikiServiceImplTest {

    LatinPhraseWikiServiceImpl service = new LatinPhraseWikiServiceImpl();

    @Test
    void getRandomLatinPhrase() throws IOException {
        System.out.println(service.getRandomLatinPhrase());
    }
}