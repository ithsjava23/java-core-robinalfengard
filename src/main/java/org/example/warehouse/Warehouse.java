package org.example.warehouse;

import java.math.BigDecimal;
import java.util.*;

public class Warehouse {
    private String name;
    private List<ProductRecord> products = new ArrayList<>();
    private static final Map<String, Warehouse> listOfWarehouses = new HashMap<>();
    private static final List<ProductRecord> changedProducts = new ArrayList<>();


    private Warehouse(String name) {
        this.name = name;
        this.products = new ArrayList<>();
    }

    private Warehouse() {
    }


    public static Warehouse getInstance(String name) {
        Warehouse existingWarehouse = listOfWarehouses.get(name);
        if (existingWarehouse != null) {
            return existingWarehouse;
        }
        return new Warehouse(name);

    }


    public static Warehouse getInstance() {
        return new Warehouse();
    }


    public ProductRecord addProduct(UUID uuid, String productName, Category category, BigDecimal price) {
        if (productName == null || productName.isEmpty()) {
            throw new IllegalArgumentException("Product name can't be null or empty.");
        } else if (category == null) {
            throw new IllegalArgumentException("Category can't be null.");
        } else if (price == null) {
            price = BigDecimal.valueOf(0);
        } else if (uuid == null) {
            uuid = UUID.randomUUID();
        }
        for (ProductRecord product : products) {
            if (product.uuid().equals(uuid)) {

                throw new IllegalArgumentException("Product with that id already exists, use updateProduct for updates.");
            }
        }

        ProductRecord p1 = new ProductRecord(uuid, productName, category, price);
        products.add(p1);
        return p1;

    }


    public Optional<ProductRecord> getProductById(UUID uuid) {
        for (ProductRecord product : products) {
            if (product.uuid() == uuid)
                return Optional.of(product);
        }
        return Optional.empty();

    }

    public List<ProductRecord> getProducts() {
        return List.copyOf(this.products);
    }

    public void updateProductPrice(UUID uuid, BigDecimal bigDecimal) {
        if (getProductById(uuid).isPresent()) {
            ProductRecord product = getProductById(uuid).get();
            product.setPrice(bigDecimal);
            changedProducts.add(product);
        } else throw new IllegalArgumentException("Product with that id doesn't exist.");

    }

    public List<ProductRecord> getProductsBy(Category category) {
        List<ProductRecord> productsFromSameCategory = new ArrayList<>();
        for (ProductRecord product : products) {
            if (product.category().equals(category))
                productsFromSameCategory.add(product);
        }
        return productsFromSameCategory;
    }

    public List<ProductRecord> getChangedProducts() {
        return List.copyOf(changedProducts);
    }

    public Map<Category, List<ProductRecord>> getProductsGroupedByCategories() {
        Map<Category, List<ProductRecord>> sortedByCategory = new HashMap<>();
        for (ProductRecord product : products) {
            sortedByCategory.computeIfAbsent(product.category(), k -> new ArrayList<>()).add(product);
        }
        return sortedByCategory;
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Warehouse warehouse)) return false;
        return Objects.equals(name, warehouse.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }


    @Override
    public String toString() {
        return "Warehouse{" +
                "name='" + name + '\'' +
                ", products=" + products +
                '}';
    }
}
