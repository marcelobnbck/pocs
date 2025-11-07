package com.company.sales;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import java.util.Properties;

public class TopSalesPerCityApp {
    public static void run() {
        Properties props = StreamsConfigLoader.load(); // loads from application.properties or env
        Topology topology = TopologyBuilder.buildTopSalesPerCityTopology(props);
        KafkaStreams streams = new KafkaStreams(topology, props);
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
        streams.start();
    }
}
