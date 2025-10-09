# Spring Data Elasticsearch

### Marcelo de Bona Beck


## What SpringData Elasticsearch Is - Slide 2

- A Spring module that makes Elasticsearch accessible through Spring idioms: repositories, templates, annotations, and auto-configuration for Spring Boot.

## Core Features - Slide 3
- Repository abstraction: CRUD and query derivation via repository interfaces.
- ElasticsearchRestTemplate: lower-level operations, custom queries, bulk operations.
- Automatic index mapping: entity annotations create/drive mappings.
- Pagination, sorting, nested fields, full-text search supported out of the box.

## Minimal Setup - Slide 4
- Add dependency: spring-boot-starter-data-elasticsearch.
- Configure connection: spring.elasticsearch.rest.uris=http://localhost:9200 (or Java config).
- Annotate entities with @Document and @Field; create repository by extending ElasticsearchRepository.

## Comparison — Spring Data Elasticsearch vs Apache Solr - Slide 5

## Pros and Cons - Slide 6
- Pros:
  - Fast full-text search, aggregations, near-real-time indexing.
  - Repository abstraction reduces boilerplate.
  - Spring Boot auto-config and annotations speed setup.
  - Scales horizontally via Elasticsearch clusters.
- Cons:
  - Operational complexity of managing ES clusters (resource-heavy).
  - Mapping and analysis tuning required to avoid unexpected search behavior.
  - Version compatibility and client/cluster mismatches can cause friction.

## Code Demonstration
```java
// Product.java
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
@Document(indexName = "products")
public class Product {
  @Id private String id;
  private String name;
  private String category;
  private Double price;
  // getters/setters
}

// ProductRepository.java
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import java.util.List;
public interface ProductRepository extends ElasticsearchRepository<Product, String> {
  List<Product> findByCategory(String category);
}

// ElasticsearchConfig.java 
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.example")
public class ElasticsearchConfig { /* Spring Boot auto-config usually suffices */ }

// ProductController.java (brief)
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/products")
public class ProductController {
  private final ProductRepository repo;
  public ProductController(ProductRepository repo) { this.repo = repo; }
  @PostMapping public Product add(@RequestBody Product p) { return repo.save(p); }
  @GetMapping("/by-category/{c}") public List<Product> byCategory(@PathVariable String c) {
    return repo.findByCategory(c);
  }
}
```
## When to choose Spring Data Elastic Search - Slide 7
- Choose SDE when you need production-grade full-text search, analytics, and tight Spring integration.
- Use simpler DB searches or Solr when organizational constraints or operational familiarity favor Solr.
