package org.example.guitarmanager.service;

import org.example.guitarmanager.model.GuitarModel;
import org.example.guitarmanager.repository.GuitarModelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GuitarModelServiceTest {

    @Mock
    private GuitarModelRepository repository;

    @InjectMocks
    private GuitarModelService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createDelegatesToRepository() {
        GuitarModel g = new GuitarModel("Gibson", "Les Paul", "electric", 6);
        when(repository.save(g)).thenReturn(g);

        GuitarModel saved = service.create(g);
        verify(repository, times(1)).save(g);
        assertThat(saved).isEqualTo(g);
    }

    @Test
    void listAllReturnsAll() {
        when(repository.findAll()).thenReturn(List.of(
                new GuitarModel("Fender", "Telecaster", "electric", 6),
                new GuitarModel("Martin", "D-28", "acoustic", 6)
        ));

        List<GuitarModel> all = service.listAll();
        assertThat(all).hasSize(2);
    }

    @Test
    void findByIdForwards() {
        GuitarModel g = new GuitarModel("Ibanez", "RG", "electric", 6);
        when(repository.findById(1L)).thenReturn(Optional.of(g));

        Optional<GuitarModel> found = service.findById(1L);
        assertThat(found).isPresent();
        assertThat(found.get().getBrand()).isEqualTo("Ibanez");
    }
}
