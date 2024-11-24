package org.acme.processor;

import org.acme.data.Line;
import org.acme.data.Location;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Performs search of all tokens in given line
 */
public class LineProcessor {

    private final Set<Pattern> patterns;

    public LineProcessor(Path tokenFile) throws IOException {
        try (var tokens = Files.lines(tokenFile)) {
            patterns = tokens
                    .map(Pattern::compile)
                    .collect(Collectors.toSet());
        }
    }

    /**
     * performs search on a line and combines results
     * @param line text line
     * @return {@link Map} which contains tokens as keys and {@link Set} of match {@link Location}'s as values
     */
    public Map<String, Set<Location>> processLine(Line line) {
        return patterns.stream()
                .map(p -> p.matcher(line.line()))
                .flatMap(Matcher::results)
                .collect(Collectors.groupingBy(MatchResult::group,
                        Collectors.mapping(res -> new Location(line.lineNumber(), res.start()), Collectors.toSet())));
    }

}
