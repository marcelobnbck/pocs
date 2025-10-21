package org.example.soccer.repository;

import org.example.soccer.model.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryTeamRepository implements TeamRepository {
    private final Map<String, Team> storage = new ConcurrentHashMap<>();

    @Override
    public Team save(Team team) {
        storage.put(team.id(), team);
        return team;
    }

    @Override
    public Optional<Team> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Team> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void deleteById(String id) {
        storage.remove(id);
    }
}
