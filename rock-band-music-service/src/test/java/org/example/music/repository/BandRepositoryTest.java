package org.example.music.repository;

import org.example.music.entity.Band;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BandRepositoryTest {

    @Autowired
    private BandRepository bandRepository;

    @Test
    void testSaveAndFindBand() {
        // Arrange
        Band band = new Band();
        band.setName("Nirvana");
        band.setGenre("Grunge");

        // Act
        Band savedBand = bandRepository.save(band);
        Band foundBand = bandRepository.findById(savedBand.getId()).orElse(null);

        // Assert
        assertThat(Collections.singletonList(foundBand)).isNotNull();
        assertThat(foundBand.getName()).isEqualTo("Nirvana");
        assertThat(foundBand.getGenre()).isEqualTo("Grunge");
    }

}

