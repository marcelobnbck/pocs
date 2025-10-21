package org.example.soccer;

import org.example.soccer.model.Team;
import org.example.soccer.repository.InMemoryTeamRepository;
import org.example.soccer.service.TeamService;
import org.example.soccer.service.TeamServiceImpl;

import java.util.List;

/**
 * Simple App demonstrating the service usage.
 */
public class App {
    public static void main(String[] args) {
        var repo = new InMemoryTeamRepository();
        TeamService service = new TeamServiceImpl(repo);

        service.createTeam("flamengo", "Clube de Regatas do Flamengo", "Rio de Janeiro", 1895);
        service.createTeam("palmeiras", "Sociedade Esportiva Palmeiras", "São Paulo", 1914);
        service.createTeam("gremio", "Grêmio Foot-Ball Porto Alegrense", "Porto Alegre", 1903);

        List<Team> teams = service.listTeamsSortedByFoundedYearDesc();
        System.out.println("Brazilian teams (sorted by founded year desc):");
        teams.forEach(t -> System.out.printf("- %s (%s) founded %d%n", t.name(), t.city(), t.foundedYear()));
    }
}
