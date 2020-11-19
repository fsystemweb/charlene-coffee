package com.cognizant.charlenecoffee.services;

import com.cognizant.charlenecoffee.models.Product;
import com.cognizant.charlenecoffee.models.Receipt;

import java.util.List;

public interface ReceiptService {
    public Receipt createReceipt(List<Product> saleList);
}
