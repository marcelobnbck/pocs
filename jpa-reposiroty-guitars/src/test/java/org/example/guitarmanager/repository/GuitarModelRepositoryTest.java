package org.example.guitarmanager.repository;

import org.example.guitarmanager.model.GuitarModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class GuitarModelRepositoryTest {

    @Autowired
    private GuitarModelRepository repository;

    @Test
    @DisplayName("save and find guitar model")
    void saveAndFind() {
        GuitarModel g = new GuitarModel("Fender", "Stratocaster", "electric", 6);
        GuitarModel saved = repository.save(g);

        assertThat(saved.getId()).isNotNull();

        List<GuitarModel> found = repository.findByBrand("Fender");
        assertThat(found).isNotEmpty();
        assertThat(found.get(0).getModel()).isEqualTo("Stratocaster");
    }
}
