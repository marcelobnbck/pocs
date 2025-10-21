package org.example.soccer.service;

import org.example.soccer.model.Team;
import org.example.soccer.repository.InMemoryTeamRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TeamServiceTest {

    @Test
    void createAndListSorted() {
        var repo = new InMemoryTeamRepository();
        TeamService service = new TeamServiceImpl(repo);

        service.createTeam("a","Club A","City A",2000);
        service.createTeam("b","Club B","City B",1990);
        service.createTeam("c","Club C","City C",2010);

        List<Team> sorted = service.listTeamsSortedByFoundedYearDesc();
        assertEquals(3, sorted.size());
        assertEquals("Club C", sorted.get(0).name()); // 2010 first
        assertEquals("Club A", sorted.get(1).name()); // 2000
    }
}
