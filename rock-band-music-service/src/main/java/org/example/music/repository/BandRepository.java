package org.example.music.repository;

import org.example.music.entity.Band;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "bands")
public interface BandRepository extends JpaRepository<Band, Long> {
    // automatically exposed as /bands via Spring Data REST
}
