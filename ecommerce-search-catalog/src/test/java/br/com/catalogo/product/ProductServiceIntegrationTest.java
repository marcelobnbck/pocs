package br.com.catalogo.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Testcontainers
class ProductServiceIntegrationTest {
    @Container
    static final ElasticsearchContainer ELASTICSEARCH =
            new ElasticsearchContainer("docker.elastic.co/elasticsearch/elasticsearch:8.15.5")
                    .withEnv("xpack.security.enabled", "false");

    @DynamicPropertySource
    static void elasticsearchProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.elasticsearch.uris", ELASTICSEARCH::getHttpHostAddress);
    }

    @Autowired ProductService service;
    @Autowired ProductRepository repository;
    @Autowired ElasticsearchOperations operations;

    @BeforeEach
    void cleanIndex() {
        if (operations.indexOps(Product.class).exists()) operations.indexOps(Product.class).delete();
        operations.indexOps(Product.class).createWithMapping();
        repository.saveAll(List.of(
                product("headphone-1", "Bluetooth Headphones", "Wireless headphones with noise cancellation", "audio", "Acme", "299.90", 12),
                product("keyboard-1", "Mechanical Keyboard", "Gaming keyboard", "peripherals", "KeyPro", "450.00", 7),
                product("headphone-2", "Wired Headphones", "Compact stereo headphones", "audio", "Acme", "79.90", 0)
        ));
        operations.indexOps(Product.class).refresh();
    }

    @Test
    void indexesAndRetrievesDocument() {
        Product found = service.getById("headphone-1");
        assertThat(found.getName()).isEqualTo("Bluetooth Headphones");
        assertThat(found.getPrice()).isEqualByComparingTo("299.90");
    }

    @Test
    void searchesByTextAndFilters() {
        List<Product> results = service.search("headphone", "audio", "Acme", 100.0, 350.0);
        assertThat(results).extracting(Product::getId).containsExactly("headphone-1");
    }

    @Test
    void returnsEmptySearchWhenNoDocumentMatches() {
        assertThat(service.search("non-existent-product", null, null, null, null)).isEmpty();
    }

    @Test
    void missingDocumentRaisesDomainException() {
        assertThatThrownBy(() -> service.getById("id-that-doesnt-exist"))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining("id-that-doesnt-exist");
    }

    @Test
    void deletesIndexedDocument() {
        service.delete("keyboard-1");
        operations.indexOps(Product.class).refresh();
        assertThat(repository.findById("keyboard-1")).isEmpty();
    }

    private Product product(String id, String name, String description, String category,
                            String brand, String price, int stock) {
        return new Product(id, name, description, category, brand,
                new BigDecimal(price), stock, Instant.now());
    }
}
