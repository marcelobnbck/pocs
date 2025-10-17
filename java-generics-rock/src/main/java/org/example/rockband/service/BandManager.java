package org.example.rockband.service;

import org.example.rockband.model.Album;
import org.example.rockband.model.Band;
import org.example.rockband.model.Musician;

public class BandManager<T extends Musician> {

    public void printBandInfo(Band<T> band) {
        System.out.println("Band: " + band.getName());
        band.getMembers().forEach(m -> System.out.println("- " + m));
    }

    public void listAlbums(Band<T> band) {
        System.out.println("\nAlbums:");
        for (Album album : band.getAlbums()) {
            System.out.println("* " + album);
        }
    }

    public int countMembers(Band<T> band) {
        return band.getMembers().size();
    }
}
