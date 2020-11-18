package com.cognizant.charlenecoffee.services;

import com.cognizant.charlenecoffee.models.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    @Autowired
    private ObjectMapper objectMapper;

    public List<Product> getProducts(){
        List<Product> productsList = new ArrayList<Product>();

        try {
            productsList = this.objectMapper.readValue(new ClassPathResource("./products.json").getFile(),
                    List.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return productsList;
    }
}
