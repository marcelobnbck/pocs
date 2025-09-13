package org.example.music.service;

import org.example.music.entity.Band;
import org.example.music.exception.NotFoundException;
import org.example.music.external.LyricsClient;
import org.example.music.repository.BandRepository;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import java.util.List;

@Service
@CacheConfig(cacheNames = "bands")
public class BandService {

    private final BandRepository repository;
    private final LyricsClient lyricsClient;

    public BandService(BandRepository repository, LyricsClient lyricsClient) {
        this.repository = repository;
        this.lyricsClient = lyricsClient;
    }

    @Cacheable(key = "#id")
    public Band getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Band not found: " + id));
    }

    public List<Band> listAll() {
        return repository.findAll();
    }

    @Transactional
    @CacheEvict(allEntries = true)
    public Band create(Band band) {
        return repository.save(band);
    }

    @Transactional
    @CachePut(key = "#id")
    public Band update(Long id, Band data) {
        Band band = getById(id);
        band.setName(data.getName());
        band.setGenre(data.getGenre());
        band.setFormedIn(data.getFormedIn());
        return repository.save(band);
    }

    @Transactional
    @CacheEvict(key = "#id")
    public void delete(Long id) {
        repository.deleteById(id);
    }

    public String getLyrics(Long id, String albumTitle) {
        Band band = getById(id);
        return lyricsClient.fetchLyrics(band.getName(), albumTitle);
    }
}