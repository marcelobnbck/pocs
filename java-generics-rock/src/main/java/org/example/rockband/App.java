package org.example.rockband;

import com.example.rockband.model.*;
import org.example.rockband.model.Album;
import org.example.rockband.model.Band;
import org.example.rockband.model.Musician;
import org.example.rockband.service.BandManager;

public class App {
    public static void main(String[] args) {
        Band<Musician> nirvana = new Band<>("Nirvana");
        nirvana.addMember(new Musician("Kurt Cobain", "Vocals/Guitar"));
        nirvana.addMember(new Musician("Krist Novoselic", "Bass"));
        nirvana.addMember(new Musician("Dave Grohl", "Drums"));

        BandManager<Musician> manager = new BandManager<>();
        manager.printBandInfo(nirvana);

        nirvana.addAlbum(new Album("Nevermind", 1991));
        nirvana.addAlbum(new Album("In Utero", 1993));

        manager.listAlbums(nirvana);
    }
}
