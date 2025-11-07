package com.company.sales;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import io.confluent.kafka.serializers.KafkaAvroSerde;
import org.apache.kafka.common.serialization.Serde;

import java.util.HashMap;
import java.util.Map;

public class SerdesConfig {
    public static <T> Serde<T> avroSerde(String schemaRegistryUrl, boolean isKey) {
        final KafkaAvroSerde serde = new KafkaAvroSerde();
        Map<String, Object> cfg = new HashMap<>();
        cfg.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);
        cfg.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);
        serde.configure(cfg, isKey);
        return (Serde<T>) serde;
    }
}
