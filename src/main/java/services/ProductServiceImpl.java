package services;


import com.fasterxml.jackson.databind.ObjectMapper;
import models.Product;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductServiceImpl implements ProductService {

    private ObjectMapper objectMapper;

    public ProductServiceImpl(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    };

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
