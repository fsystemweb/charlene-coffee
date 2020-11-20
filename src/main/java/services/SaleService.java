package services;

import models.Product;

import java.util.List;

public interface SaleService {
    public List<Product> readProducts(String[] products) throws Exception;
}
