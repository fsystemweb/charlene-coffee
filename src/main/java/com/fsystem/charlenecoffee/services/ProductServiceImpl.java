package com.fsystem.charlenecoffee.services;

import com.fsystem.charlenecoffee.models.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ObjectMapper objectMapper;

    public List<Product> getProducts(){
        List<Product> productsList = new ArrayList<Product>();

        try {
            Product[] products = this.objectMapper.readValue(new ClassPathResource("./products.json").getFile(),
                    Product[].class);

            productsList = new ArrayList<Product>(Arrays.asList(products));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return productsList;
    }
}
