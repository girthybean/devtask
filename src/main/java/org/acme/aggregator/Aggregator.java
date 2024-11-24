package org.acme.aggregator;

import org.acme.data.Location;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Aggregator {

    private final Map<String, Set<Location>> results;

    public Aggregator() {
        this.results = new ConcurrentHashMap<>();
    }

    public void merge(Map<String, Set<Location>> locations) {
        locations.forEach((k, v) ->
                results.merge(k, v, (set1, set2) -> {
                    set1.addAll(set2);
                    return set1;
                })
        );
    }

    @Override
    public String toString() {
        return results.toString();
    }
}
