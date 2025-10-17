package org.example.rockband.model;

public class Musician {
    private String name;
    private String instrument;

    public Musician(String name, String instrument) {
        this.name = name;
        this.instrument = instrument;
    }

    public String getName() {
        return name;
    }

    public String getInstrument() {
        return instrument;
    }

    @Override
    public String toString() {
        return name + " (" + instrument + ")";
    }
}
