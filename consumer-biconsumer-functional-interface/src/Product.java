import java.util.Objects;

public class Product {
    private String name;
    private double price;
    private String category;
    private boolean isAvailable;

    public Product(String name, double price, String category, boolean isAvailable) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.isAvailable = isAvailable;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }
    public boolean isAvailable() { return isAvailable; }

    // Setters to demonstrate side effects
    public void setPrice(double price) { this.price = price; }
    public void setAvailable(boolean available) { isAvailable = available; }

    @Override
    public String toString() {
        return "Product{name='"
                + name + "', price="
                + String.format("%.2f", price)
                + ", category='"
                + category
                + "', available="
                + isAvailable
                + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.price, price) == 0
                && isAvailable == product.isAvailable
                && name.equals(product.name)
                && category.equals(product.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, category, isAvailable);
    }
}
