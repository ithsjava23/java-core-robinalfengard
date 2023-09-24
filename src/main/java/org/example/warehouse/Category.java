package org.example.warehouse;

import java.util.HashMap;
import java.util.Map;

public class Category {
    private final String name;
    private static Map<String, Category> categoryList = new HashMap<>();

    private Category(String name) {
        this.name = name.substring(0, 1).toUpperCase() + name.substring(1);
    }


    public static Category of(String name) {
        if (name == null)
            throw new IllegalArgumentException("Category name can't be null");

        Category category = categoryList.get(name);

        if (category == null) {
            category = new Category(name);
            categoryList.put(name, category);
        }
        return category;
    }


    public String getName() {
        return name;
    }


}
