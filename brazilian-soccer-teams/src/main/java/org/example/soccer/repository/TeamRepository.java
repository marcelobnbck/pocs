package org.example.soccer.repository;

import org.example.soccer.model.Team;
import java.util.List;
import java.util.Optional;

public interface TeamRepository {
    Team save(Team team);
    Optional<Team> findById(String id);
    List<Team> findAll();
    void deleteById(String id);
}
