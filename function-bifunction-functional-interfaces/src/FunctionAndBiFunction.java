import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class FunctionAndBiFunction {

    public static void main(String[] args) {

        List<Product> products = new ArrayList<>(Arrays.asList(
                new Product("Laptop", 1200.00, "Electronics"),
                new Product("Mouse", 25.50, "Electronics"),
                new Product("Keyboard", 75.00, "Electronics"),
                new Product("Book", 15.75, "Books"),
                new Product("Coffee Maker", 89.99, "Home Appliances")
        ));

        System.out.println("\nOriginal List of Products:");
        products.forEach(System.out::println);

        // Function<T, R>

        // 1. Basic Function: Extract Product Name
        Function<Product, String> getProductName = product -> product.getName();
        System.out.println("Name of Laptop: " + getProductName.apply(products.get(0)));
        System.out.println("Name of Book: " + getProductName.apply(products.get(3)));

        // 2. Basic Function: Calculate Discounted Price
        Function<Double, Double> applyTenPercentDiscount = originalPrice -> originalPrice * 0.90;
        System.out.println("Laptop price ($1200) with 10% discount: $"
                + String.format("%.2f", applyTenPercentDiscount.apply(1200.00)));

        // A more direct way without name lookup: Product -> Price -> Discounted Price
        Function<Product, Double> getProductPriceThenDiscount = Product::getPrice;
                // Function<Product, Double>
        Function<Product, Double> finalDiscountedPrice = getProductPriceThenDiscount
                .andThen(applyTenPercentDiscount);

        System.out.println("Laptop's final discounted price: $"
                + String.format("%.2f", finalDiscountedPrice.apply(products.get(0))));
                // Laptop: 1200 * 0.90 = 1080.00
        System.out.println("Coffee Maker's final discounted price: $"
                + String.format("%.2f", finalDiscountedPrice.apply(products.get(4))));
                // Coffee Maker: 89.99 * 0.90 = 80.99

        // 3. Chaining Functions: compose()
        Function<Double, Double> applyFivePercentTax = price -> price * 1.05;

        // Apply discount first, then tax: applyFivePercentTax.compose(applyTenPercentDiscount)
        Function<Double, Double> discountedPriceThenTaxed = applyFivePercentTax
                .compose(applyTenPercentDiscount);
        System.out.println("Original Price $100.00 -> Discounted then Taxed: $"
                + String.format("%.2f", discountedPriceThenTaxed.apply(100.00)));
                // 100 * 0.90 * 1.05 = 94.50

        // BiFunction<T, U, R>

        // 1. Basic BiFunction: Calculate Total Price
        BiFunction<Double, Integer, Double> calculateTotalPrice = (price, quantity)
                -> price * quantity;
        System.out.println("Total for 3 Laptops ($1200 each): $"
                + String.format("%.2f", calculateTotalPrice.apply(1200.00, 3)));
        System.out.println("Total for 5 Mice ($25.50 each): $"
                + String.format("%.2f", calculateTotalPrice.apply(25.50, 5)));

        // 2. Basic BiFunction: Update Product Price
        BiFunction<Product, Double, Product> updateProductPrice = (product, newPrice) -> {
            product.setPrice(newPrice);
            return product;
        };
        Product updatedLaptop = updateProductPrice.apply(products.get(0), 1150.00);
            // Update Laptop's price
        System.out.println("Laptop after price update: " + updatedLaptop);
        System.out.println("Original list still reflects change (same object): " + products.get(0));

        // 3. Chaining BiFunctions: andThen()
        // Calculate total price, then apply a 5% tax to that total.
        BiFunction<Double, Integer, Double> calculateTotalAndApplyTax = calculateTotalPrice
                .andThen(applyFivePercentTax);
        System.out.println("Total for 2 Keyboards ($75 each) with tax: $"
                + String.format("%.2f", calculateTotalAndApplyTax.apply(75.00, 2)));
                // (75 * 2) * 1.05 = 157.50

        // Example: Combining a product and a discount percentage to get final price
        BiFunction<Product, Double, Double> calculateFinalPriceWithDiscount = (
                product,
                discountPercentage) ->
                product.getPrice() * (1 - discountPercentage);

        // Chain with applying tax
        BiFunction<Product, Double, Double> finalPriceWithDiscountAndTax = calculateFinalPriceWithDiscount
                .andThen(applyFivePercentTax);
        System.out.println("Laptop ($1150) with 20% discount and 5% tax: $"
                + String.format("%.2f", finalPriceWithDiscountAndTax.apply(updatedLaptop, 0.20)));
                // 1150 * (1 - 0.20) = 1150 * 0.80 = 920.00
                // 920.00 * 1.05 = 966.00
    }
}
