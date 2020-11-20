package controllers;


import models.Product;
import models.Receipt;
import services.DiscountService;
import services.ReceiptService;
import services.SaleService;

import java.util.HashMap;
import java.util.List;

public class MainController  {

    private SaleService saleService;

    private DiscountService discountService;

    private ReceiptService receiptService;

    public MainController(SaleService saleService, DiscountService discountService, ReceiptService receiptService){
        this.saleService = saleService;
        this.discountService = discountService;
        this.receiptService = receiptService;
    }

    public Object createFinalReceipt(String entryData){

        String[] parsedData = entryData.split(", ");
        try {
            List<Product> saleList = saleService.readProducts(parsedData);

            saleList = discountService.addDiscounts(saleList);

            Receipt receipt = receiptService.createReceipt(saleList);

            return receipt;
        } catch (Exception e) {
            HashMap<String, String> errorBody = new HashMap<>();
            errorBody.put("error", e.getMessage());
            return errorBody;
        }
    }
}
