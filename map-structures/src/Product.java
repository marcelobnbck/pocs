import java.util.Objects;

public class Product {
    private String productId;
    private String name;
    private double price;
    private ProductStatus status;

    public Product(String productId, String name, double price, ProductStatus status) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.status = status;
    }

    public String getProductId() { return productId; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public ProductStatus getStatus() { return status; }

    public void setPrice(double price) { this.price = price; }
    public void setStatus(ProductStatus status) { this.status = status; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return productId.equals(product.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + productId + '\'' +
                ", name='" + name + '\'' +
                ", price=" + String.format("%.2f", price) + // Format price for better output
                ", status=" + status +
                '}';
    }
}