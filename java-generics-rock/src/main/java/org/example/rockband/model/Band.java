package org.example.rockband.model;

import java.util.ArrayList;
import java.util.List;

public class Band<T extends Musician> {
    private String name;
    private final List<T> members;
    private final List<Album> albums;

    public Band(String name) {
        this.name = name;
        this.members = new ArrayList<>();
        this.albums = new ArrayList<>();
    }

    public void addMember(T musician) {
        members.add(musician);
    }

    public void addAlbum(Album album) {
        albums.add(album);
    }

    public List<T> getMembers() {
        return members;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public String getName() {
        return name;
    }
}
