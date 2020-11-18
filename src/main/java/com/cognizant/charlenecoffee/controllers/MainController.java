package com.cognizant.charlenecoffee.controllers;

import com.cognizant.charlenecoffee.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class MainController {
    @Autowired
    private SaleService saleService;


}
