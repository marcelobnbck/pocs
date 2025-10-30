# Kafka Streams WordCount Example

## Overview
This project demonstrates a simple **Kafka Streams** app that performs a real-time word count.

- Reads text from input topic `text-input`
- Splits lines into words
- Counts word occurrences
- Writes results to output topic `word-count-output`

## Requirements
- Java 21+
- Maven 3.8+
- Docker & Docker Compose

## Run Instructions

1. Start Kafka:
   ```bash
   docker-compose up -d
   ```

2. Create Kafka topics:
   ```bash
   docker exec -it <kafka_container_name> kafka-topics.sh --create --topic text-input --bootstrap-server localhost:9092
   docker exec -it <kafka_container_name> kafka-topics.sh --create --topic word-count-output --bootstrap-server localhost:9092
   ```

3. Run the app:
   ```bash
   mvn clean compile exec:java -Dexec.mainClass=org.example.streams.WordCountApp
   ```

4. Send messages:
   ```bash
   kafka-console-producer.sh --topic text-input --bootstrap-server localhost:9092
   > Hello Kafka Streams
   > Kafka Streams is awesome
   ```

5. Read results:
   ```bash
   kafka-console-consumer.sh --topic word-count-output --from-beginning --bootstrap-server localhost:9092
   ```

## Docker Compose
A minimal Kafka + Zookeeper setup is included in `docker-compose.yml`.
