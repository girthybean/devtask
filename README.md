### Development Task Project

#### Task at hands
Perform search of a certain keywords on an arbitrary large text in concurrent manner.

#### Technologies used
I decided to use [Project Reactor](https://projectreactor.io/), as it provides wide array of instruments to create powerful pipelines and
perform asynchronous operations on a streams of data.

#### Project components
1. [SearchRunner](./src/main/java/org/acme/SearchRunner.java) - initializes required components, starts search pipeline.
2. [Line](./src/main/java/org/acme/data/Line.java) - wrapper that represents line of text.
3. [Location](./src/main/java/org/acme/data/Location.java) - wrapper that represents token match location.
4. [LineProcessor](./src/main/java/org/acme/processor/LineProcessor.java) - performs token search on a line of text
5. [ReactiveBatchLineReader](./src/main/java/org/acme/processor/ReactiveBatchLineReader.java) - creates and configures Flux with batched file lines
6. [Aggregator](./src/main/java/org/acme/aggregator/Aggregator.java) - wrapper on top of ConcurrentHashMap, consumes pipeline intermediate results, creates final string.

#### Run program
`gradle run`