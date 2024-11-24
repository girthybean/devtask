package org.acme;

import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class TextSearchApplication {

    public static void main(String[] args) {
        //Config this
        Path bigText = Paths.get("src/main/resources/big.txt");
        Path searchTokens = Paths.get("src/main/resources/tokens.txt");
        int batchSize = 1000;

        SearchRunner searchRunner = new SearchRunner(bigText, searchTokens, batchSize);
        searchRunner.runSearch();
        log.info("Search results: {}", searchRunner.getResult());
    }

}
