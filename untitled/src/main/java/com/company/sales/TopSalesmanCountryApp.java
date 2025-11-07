package com.company.sales;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.Topology;
import java.util.Properties;

public class TopSalesmanCountryApp {
    public static void run() {
        Properties props = StreamsConfigLoader.load();
        Topology topology = TopologyBuilder.buildTopSalesmanCountryTopology(props);
        KafkaStreams streams = new KafkaStreams(topology, props);
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
        streams.start();
    }
}
