package org.example.warehouse;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductRecord {
    private String name;
    private Category category;
    private UUID uuid;
    private BigDecimal price;

    public ProductRecord(UUID uuid, String name, Category category, BigDecimal price) {
        this.uuid = uuid;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public UUID uuid() {
        return this.uuid;
    }

    public BigDecimal price() {
        return this.price;
    }

    public Category category() {
        return this.category;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
