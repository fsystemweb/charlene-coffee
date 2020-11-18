package com.cognizant.charlenecoffee.services;

import com.cognizant.charlenecoffee.models.Product;

import java.util.List;

public interface SaleService {
    public List<Product> readProducts(String[] products) throws Exception;
}
