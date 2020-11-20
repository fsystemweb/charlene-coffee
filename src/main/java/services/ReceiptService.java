package services;


import models.Product;
import models.Receipt;

import java.util.List;

public interface ReceiptService {
    public Receipt createReceipt(List<Product> saleList);
}
