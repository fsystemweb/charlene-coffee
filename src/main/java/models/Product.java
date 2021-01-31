package models;

import enums.CoffeeSize;
import enums.ProductType;

public class Product {
    private String name;
    private CoffeeSize size;
    private Double price;
    private ProductType type;
    private String currency;

    public Product() {
    }

    public Product(String name, CoffeeSize size, Double price, ProductType type, String currency) {
        this.name = name;
        this.size = size;
        this.price = price;
        this.type = type;
        this.currency = currency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CoffeeSize getSize() {
        return size;
    }

    public void setSize(CoffeeSize size) {
        this.size = size;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
