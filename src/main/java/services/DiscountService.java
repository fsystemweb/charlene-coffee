package services;

import models.Product;

import java.util.List;

public interface DiscountService {
    List<Product> addDiscounts(List<Product> saleList);
}
