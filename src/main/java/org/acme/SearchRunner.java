package org.acme;

import org.acme.aggregator.Aggregator;
import org.acme.processor.LineProcessor;
import org.acme.processor.ReactiveBatchLineReader;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.nio.file.Path;

public class SearchRunner {

    private final ReactiveBatchLineReader batchLineReader;
    private final LineProcessor lineProcessor;
    private final Aggregator aggregator;

    public SearchRunner(Path text, Path searchTokens, int batchSize) {
        try {
            this.batchLineReader = new ReactiveBatchLineReader(text, batchSize);
            this.lineProcessor = new LineProcessor(searchTokens);
        } catch (IOException e) {
            throw new AcmeException(e);
        }
        this.aggregator = new Aggregator();
    }

    /**
     * Performs search result transformation and send results to {@link Aggregator}
     */
    public void runSearch() {
            batchLineReader.fluxWrapped()
                    .flatMap(Flux::fromIterable)
                    .map(lineProcessor::processLine)
                    .doOnNext(aggregator::merge)
                    .blockLast();
    }

    public String getResult() {
        return aggregator.toString();
    }
}
