package br.com.catalogo.product;

import org.junit.jupiter.api.Test;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceUnitTest {
    @Test
    void missingProductIsTranslatedToDomainException() {
        ProductRepository repository = mock(ProductRepository.class);
        ElasticsearchOperations operations = mock(ElasticsearchOperations.class);
        when(repository.findById("missing")).thenReturn(Optional.empty());
        ProductService service = new ProductService(repository, operations);

        assertThatThrownBy(() -> service.getById("missing"))
                .isInstanceOf(ProductNotFoundException.class);
    }
}
