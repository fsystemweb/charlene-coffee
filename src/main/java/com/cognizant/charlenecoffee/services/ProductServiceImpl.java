package com.cognizant.charlenecoffee.services;

import com.cognizant.charlenecoffee.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    @Autowired
    private ObjectMapper objectMapper;

    public List<Product> getProducts(){
        return this.objectMapper.readValue(new ClassPathResource("./defaults/myjson.json").getFile(), Product[].class);;
    }
}
