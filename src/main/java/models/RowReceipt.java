package models;

import enums.TypeRowReceipt;

public class RowReceipt {
    private String description;
    private Double price;
    private TypeRowReceipt type;

    public RowReceipt(String description, Double price, TypeRowReceipt type) {
        this.description = description;
        this.price = price;
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public TypeRowReceipt getType() {
        return type;
    }

    public void setType(TypeRowReceipt type) {
        this.type = type;
    }
}
