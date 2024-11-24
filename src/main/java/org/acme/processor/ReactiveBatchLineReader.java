package org.acme.processor;

import org.acme.data.Line;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * Initializes reactive pipeline
 */
public class ReactiveBatchLineReader {

    private final AtomicInteger lineCounter;
    private final Path path;
    private final int batchSize;

    public ReactiveBatchLineReader(Path path, int batchSize) {
        this.path = path;
        this.batchSize = batchSize;
        lineCounter = new AtomicInteger(0);
    }

    /**
     * Initializes reading of a file from the given {@link ReactiveBatchLineReader#path},
     * splits stream into batches of a given size {@link ReactiveBatchLineReader#batchSize},
     * wraps text lines into {@link Line}
     * @return {@link Flux} which contains batches of {@link Line}'s
     */
    public Flux<List<Line>> fluxWrapped() {
        return Flux.using(
                        () -> Files.lines(path),
                        Flux::fromStream,
                        Stream::close
                )
                //it's recommended to use bounded elastic scheduler for IO operations
                .subscribeOn(Schedulers.boundedElastic())
                //split file into line batches
                .buffer(batchSize)
                //wraps into numbered lines
                .map(this::wrap)
                //consecutive operations are running on a standard parallel scheduler
                .publishOn(Schedulers.parallel());
    }

    private List<Line> wrap (List<String> lines) {
        return lines.stream()
                .map(line -> new Line(lineCounter.getAndIncrement(), line))
                .toList();
    }

}
