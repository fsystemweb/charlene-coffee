package com.cognizant.charlenecoffee.controllers;

import com.cognizant.charlenecoffee.models.Product;
import com.cognizant.charlenecoffee.models.Receipt;
import com.cognizant.charlenecoffee.services.DiscountService;
import com.cognizant.charlenecoffee.services.ReceiptService;
import com.cognizant.charlenecoffee.services.SaleService;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.HashMap;
import java.util.List;

@RestController
public class MainController {
    @Autowired
    private SaleService saleService;

    @Autowired
    private DiscountService discountService;

    @Autowired
    private ReceiptService receiptService;

    @PostMapping(value="/api")
    public ResponseEntity createFinalReceipt(@RequestBody HashMap<String, String> entryData){

        String[] parsedData = entryData.get("data").split(", ");
        try {
            List<Product> saleList = saleService.readProducts(parsedData);

            saleList = discountService.addDiscounts(saleList);

            Receipt receipt = receiptService.createReceipt(saleList);

            return ResponseEntity.ok().body(receipt);
        } catch (Exception e) {
            HashMap<String, String> errorBody = new HashMap<>();
            errorBody.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody);
        }
    }
}
