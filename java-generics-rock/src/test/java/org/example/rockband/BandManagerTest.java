package org.example.rockband;

import org.example.rockband.model.Album;
import org.example.rockband.model.Band;
import org.example.rockband.model.Musician;
import org.example.rockband.service.BandManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BandManagerTest {

    @Test
    public void testBandMemberCount() {
        Band<Musician> fooFighters = new Band<>("Foo Fighters");
        fooFighters.addMember(new Musician("Dave Grohl", "Vocals/Guitar"));
        fooFighters.addMember(new Musician("Taylor Hawkins", "Drums"));

        BandManager<Musician> manager = new BandManager<>();
        Assertions.assertEquals(2, manager.countMembers(fooFighters));
    }

    @Test
    public void testAlbumAddition() {
        Band<Musician> band = new Band<>("Led Zeppelin");
        band.addAlbum(new Album("IV", 1971));

        Assertions.assertEquals(1, band.getAlbums().size());
        Assertions.assertEquals("IV", band.getAlbums().get(0).getTitle());
    }
}
