package org.example.soccer.repository;

import org.example.soccer.model.Team;
import org.example.soccer.model.TeamImpl;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTeamRepositoryTest {

    @Test
    void saveAndFindById() {
        var repo = new InMemoryTeamRepository();
        Team t = new TeamImpl("c1","Cruzeiro","Belo Horizonte",1921);
        repo.save(t);

        Optional<Team> found = repo.findById("c1");
        assertTrue(found.isPresent());
        assertEquals("Cruzeiro", found.get().name());
    }

    @Test
    void findAllAndDelete() {
        var repo = new InMemoryTeamRepository();
        repo.save(new TeamImpl("t1","Vasco","Rio",1898));
        repo.save(new TeamImpl("t2","Fluminense","Rio",1902));

        List<Team> all = repo.findAll();
        assertEquals(2, all.size());

        repo.deleteById("t1");
        assertTrue(repo.findById("t1").isEmpty());
        assertEquals(1, repo.findAll().size());
    }
}
