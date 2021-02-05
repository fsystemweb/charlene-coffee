package com.fsystem.charlenecoffee.services;

import com.fsystem.charlenecoffee.models.Product;
import com.fsystem.charlenecoffee.models.Receipt;

import java.util.List;

public interface ReceiptService {
    public Receipt createReceipt(List<Product> saleList);
}
