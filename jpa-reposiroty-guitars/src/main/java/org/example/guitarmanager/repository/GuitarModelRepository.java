package org.example.guitarmanager.repository;

import org.example.guitarmanager.model.GuitarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GuitarModelRepository extends JpaRepository<GuitarModel, Long> {
    List<GuitarModel> findByBrand(String brand);
}
