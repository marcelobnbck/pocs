package br.com.catalogo.product;

import jakarta.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.math.BigDecimal;
import java.time.Instant;

@Document(indexName = "products", createIndex = true)
public class Product {
    @Id
    private String id;

    @NotBlank
    @MultiField(
            mainField = @Field(
                    type = FieldType.Text,
                    analyzer = "brazilian"
            ),
            otherFields = {
                    @InnerField(
                            suffix = "keyword",
                            type = FieldType.Keyword
                    )
            }
    )
    private String name;

    @NotBlank
    @Field(type = FieldType.Text, analyzer = "brazilian")
    private String description;

    @NotBlank
    @Field(type = FieldType.Keyword)
    private String category;

    @NotBlank
    @Field(type = FieldType.Keyword)
    private String brand;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    @Field(type = FieldType.Double)
    private BigDecimal price;

    @Min(0)
    @Field(type = FieldType.Integer)
    private int stock;

    @Field(type = FieldType.Date)
    private Instant createdAt;

    public Product() {}

    public Product(String id, String name, String description, String category, String brand,
                   BigDecimal price, int stock, Instant createdAt) {
        this.id = id; this.name = name; this.description = description; this.category = category;
        this.brand = brand; this.price = price; this.stock = stock; this.createdAt = createdAt;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
