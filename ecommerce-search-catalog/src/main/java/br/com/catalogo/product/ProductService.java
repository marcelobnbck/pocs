package br.com.catalogo.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository repository;
    private final ElasticsearchOperations operations;

    public ProductService(ProductRepository repository, ElasticsearchOperations operations) {
        this.repository = repository;
        this.operations = operations;
    }

    public Product create(Product product) {
        if (product.getCreatedAt() == null) product.setCreatedAt(Instant.now());
        product.setId(null);
        return repository.save(product);
    }

    public Product update(String id, Product product) {
        if (!repository.existsById(id)) throw new ProductNotFoundException(id);
        product.setId(id);
        if (product.getCreatedAt() == null) product.setCreatedAt(Instant.now());
        return repository.save(product);
    }

    public Product getById(String id) {
        return repository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    public void delete(String id) {
        if (!repository.existsById(id)) throw new ProductNotFoundException(id);
        repository.deleteById(id);
    }

    public Page<Product> list(Pageable pageable) {
        return repository.findAll(PageRequest.of(pageable.getPageNumber(), Math.min(pageable.getPageSize(), 100),
                pageable.getSort()));
    }

    public List<Product> search(String text, String category, String brand, Double minPrice, Double maxPrice) {
        Criteria criteria = new Criteria();
        boolean hasCriteria = false;
        if (StringUtils.hasText(text)) {
            criteria = criteria.and(new Criteria("name").matches(text)
                    .or(new Criteria("description").matches(text)));
            hasCriteria = true;
        }
        if (StringUtils.hasText(category)) {
            criteria = hasCriteria ? criteria.and("category").is(category) : new Criteria("category").is(category);
            hasCriteria = true;
        }
        if (StringUtils.hasText(brand)) {
            criteria = hasCriteria ? criteria.and("brand").is(brand) : new Criteria("brand").is(brand);
            hasCriteria = true;
        }
        if (minPrice != null) {
            criteria = hasCriteria ? criteria.and("price").greaterThanEqual(minPrice)
                    : new Criteria("price").greaterThanEqual(minPrice);
            hasCriteria = true;
        }
        if (maxPrice != null) {
            criteria = hasCriteria ? criteria.and("price").lessThanEqual(maxPrice)
                    : new Criteria("price").lessThanEqual(maxPrice);
            hasCriteria = true;
        }
        if (!hasCriteria) return repository.findAll(PageRequest.of(0, 100)).getContent();
        CriteriaQuery query = new CriteriaQuery(criteria);
        query.setPageable(PageRequest.of(0, 100));
        SearchHits<Product> hits = operations.search(query, Product.class);
        return hits.getSearchHits().stream().map(SearchHit::getContent).toList();
    }
}
