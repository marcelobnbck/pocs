# Spring Data Elasticsearch

### Marcelo de Bona Beck

## Introduction

- Spring Data Elasticsearch integrates Spring applications with Elasticsearch.
- Provides repositories, templates, and query support.
- Simplifies indexing and searching data.
- Ideal for full-text search, analytics, and log management.

## Main Features
- Repository abstraction for Elasticsearch.
- CRUD operations on documents.
- Derived queries and custom queries.
- Seamless integration with Spring Boot.
- Easy migration from relational DB mindset.

## Project Setup (Maven)
```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-data-elasticsearch</artifactId>
</dependency>
```

## Entity Example
```java
@Document(indexName = "products")
public class Product {
    @Id
    private String id;
    private String name;
    private Double price;
}
```
## Repository Example
```java
public interface ProductRepository 
      extends ElasticsearchRepository<Product, String> {
   List<Product> findByName(String name);
}
```

## Service Usage
```java
@Service
public class ProductService {
    @Autowired
    private ProductRepository repo;

    public void demo() {
        repo.save(new Product("1", "Laptop", 1200.0));
        List<Product> results = repo.findByName("Laptop");
    }
}
```
