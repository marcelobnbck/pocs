package com.company.sales;

import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;

import java.util.Properties;

public class TopologyBuilder {
    public static Topology buildTopSalesPerCityTopology(Properties props) {
        StreamsBuilder builder = new StreamsBuilder();

        return builder.build();
    }

    public static Topology buildTopSalesmanCountryTopology(Properties props) {
        StreamsBuilder builder = new StreamsBuilder();

        return builder.build();
    }
}
