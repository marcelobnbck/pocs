package org.example.soccer.service;

import org.example.soccer.model.Team;
import org.example.soccer.model.TeamImpl;
import org.example.soccer.repository.TeamRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TeamServiceImpl implements TeamService {

    private final TeamRepository repository;

    public TeamServiceImpl(TeamRepository repository) {
        this.repository = repository;
    }

    @Override
    public Team createTeam(String id, String name, String city, int foundedYear) {
        Team team = new TeamImpl(id, name, city, foundedYear);
        return repository.save(team);
    }

    @Override
    public List<Team> listAllTeams() {
        return repository.findAll();
    }

    @Override
    public List<Team> listTeamsSortedByFoundedYearDesc() {
        return repository.findAll().stream()
                .sorted(Comparator.comparingInt(Team::foundedYear).reversed())
                .collect(Collectors.toList());
    }
}
