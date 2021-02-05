package com.fsystem.charlenecoffee.services;

import com.fsystem.charlenecoffee.models.Product;

import java.util.List;

public interface DiscountService {
    List<Product> addDiscounts(List<Product> saleList);
}
