package org.example.guitarmanager.service;

import org.example.guitarmanager.model.GuitarModel;
import org.example.guitarmanager.repository.GuitarModelRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class GuitarModelService {
    private final GuitarModelRepository repository;

    public GuitarModelService(GuitarModelRepository repository) {
        this.repository = repository;
    }

    public GuitarModel create(GuitarModel guitar) {
        return repository.save(guitar);
    }

    public List<GuitarModel> listAll() {
        return repository.findAll();
    }

    public Optional<GuitarModel> findById(Long id) {
        return repository.findById(id);
    }

    public List<GuitarModel> findByBrand(String brand) {
        return repository.findByBrand(brand);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
