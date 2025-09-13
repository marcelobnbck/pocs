package org.example.music.controller;

import org.example.music.entity.Band;
import org.example.music.service.BandService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bands")
@CrossOrigin(origins = "*")
public class BandController {

    private final BandService service;

    public BandController(BandService service) {
        this.service = service;
    }

    @GetMapping
    public List<Band> list() {
        return service.listAll();
    }

    @GetMapping("/{id}")
    public Band get(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Band create(@Valid @RequestBody Band band) {
        return service.create(band);
    }

    @PutMapping("/{id}")
    public Band update(@PathVariable Long id, @Valid @RequestBody Band band) {
        return service.update(id, band);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/{id}/lyrics")
    public String lyrics(@PathVariable Long id, @RequestParam String title) {
        return service.getLyrics(id, title);
    }
}
