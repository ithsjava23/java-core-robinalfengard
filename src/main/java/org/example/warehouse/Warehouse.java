package org.example.warehouse;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class Warehouse {
    private String name;
    private List<ProductRecord> products = new ArrayList<>();
    private static final List<ProductRecord> changedProducts = new ArrayList<>();


    private Warehouse(String name) {
        this.name = name;
        this.products = new ArrayList<>();
    }

    private Warehouse() {}

    public static Warehouse getInstance(String name) {return new Warehouse(name);}

    public static Warehouse getInstance() {return new Warehouse();}


    public ProductRecord addProduct(UUID uuid, String productName, Category category, BigDecimal price) {
        if (productName == null || productName.isEmpty()) {
            throw new IllegalArgumentException("Product name can't be null or empty.");
        }
        if (category == null) {
            throw new IllegalArgumentException("Category can't be null.");
        }
        if (price == null) {
            price = BigDecimal.valueOf(0);
        }
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
        UUID finalUuid = uuid;
        if(products.stream().anyMatch(product -> product.uuid().equals(finalUuid))){
            throw new IllegalArgumentException("Product with that id already exists, use updateProduct for updates.");
        }

        ProductRecord p1 = new ProductRecord(uuid, productName, category, price);
        products.add(p1);
        return p1;
    }


    public Optional<ProductRecord> getProductById(UUID uuid) {
        return products.stream()
                .filter(product -> product.uuid().equals(uuid))
                .findFirst();
    }


    public List<ProductRecord> getProducts() {return List.copyOf(this.products);}


    public void updateProductPrice(UUID uuid, BigDecimal bigDecimal) {
        Optional<ProductRecord> productToChange = getProductById(uuid);

        productToChange.ifPresent(product -> {
            product.setPrice(bigDecimal);
            changedProducts.add(product);
        });

        if(productToChange.isEmpty())
            throw  new IllegalArgumentException("Product with that id doesn't exist.");

    }

    public List<ProductRecord> getProductsBy(Category category) {
        return products.stream()
                .filter(product -> product.category().equals(category))
                .collect(Collectors.toList());
    }

    public List<ProductRecord> getChangedProducts() {
        return List.copyOf(changedProducts);
    }

    public Map<Category, List<ProductRecord>> getProductsGroupedByCategories(){
        return products.stream()
                .collect(Collectors.groupingBy(
                        ProductRecord::category
                        ));
    }

    public boolean isEmpty() {return products.isEmpty();}


}
