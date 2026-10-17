package br.com.catalogo.product;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) { this.service = service; }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@Valid @RequestBody Product product) { return service.create(product); }

    @GetMapping("/{id}")
    public Product get(@PathVariable String id) { return service.getById(id); }

    @PutMapping("/{id}")
    public Product update(@PathVariable String id, @Valid @RequestBody Product product) {
        return service.update(id, product);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) { service.delete(id); }

    @GetMapping
    public Page<Product> list(Pageable pageable) { return service.list(pageable); }

    @GetMapping("/search")
    public List<Product> search(@RequestParam(required = false) String q,
                                @RequestParam(required = false) String category,
                                @RequestParam(required = false) String brand,
                                @RequestParam(required = false) Double minPrice,
                                @RequestParam(required = false) Double maxPrice) {
        return service.search(q, category, brand, minPrice, maxPrice);
    }
}
