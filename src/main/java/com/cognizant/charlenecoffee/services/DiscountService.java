package com.cognizant.charlenecoffee.services;

import com.cognizant.charlenecoffee.models.Product;

import java.util.List;

public interface DiscountService {
    List<Product> addDiscounts(List<Product> saleList);
}
