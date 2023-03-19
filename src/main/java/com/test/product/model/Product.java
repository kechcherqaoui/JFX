package com.test.product.model;

public class Product {
    private Long id;
    private String name;
    private String reference;
    private double price;
    private Category category;

    public Product(String name, String reference, double price, Category category) {
        this.name = name;
        this.reference = reference;
        this.price = price;
        this.category = category;
    }

    public Product(Long id, String name, String reference, double price, Category category) {
        this.id = id;
        this.name = name;
        this.reference = reference;
        this.price = price;
        this.category = category;
    }

    public Product(Long id, String name, String reference, double price) {
        this.id = id;
        this.name = name;
        this.reference = reference;
        this.price = price;
    }

    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
