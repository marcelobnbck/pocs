package org.example.guitarmanager.controller;

import org.example.guitarmanager.model.GuitarModel;
import org.example.guitarmanager.service.GuitarModelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/guitars")
public class GuitarModelController {
    private final GuitarModelService service;

    public GuitarModelController(GuitarModelService service) {
        this.service = service;
    }

    @GetMapping
    public List<GuitarModel> list() {
        return service.listAll();
    }

    @PostMapping
    public ResponseEntity<GuitarModel> create(@RequestBody GuitarModel guitar) {
        GuitarModel saved = service.create(guitar);
        return ResponseEntity.created(URI.create("/api/guitars/" + saved.getId())).body(saved);
    }
}
