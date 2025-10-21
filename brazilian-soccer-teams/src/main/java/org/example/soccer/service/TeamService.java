package org.example.soccer.service;

import org.example.soccer.model.Team;

import java.util.List;

public interface TeamService {
    Team createTeam(String id, String name, String city, int foundedYear);
    List<Team> listAllTeams();
    List<Team> listTeamsSortedByFoundedYearDesc();
}
