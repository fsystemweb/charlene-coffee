package com.cognizant.charlenecoffee.services;

import com.cognizant.charlenecoffee.enums.CoffeeSize;
import com.cognizant.charlenecoffee.enums.ProductType;
import com.cognizant.charlenecoffee.models.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;


@SpringBootTest
public class ProductServiceTest {

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getProducts_objectMapperIsCalled() throws IOException {
        List<Product> mockResponse = createListProductOnlyForTestPurpose();
        List<Product> expected = createListProductOnlyForTestPurpose();

        given(objectMapper.readValue(new ClassPathResource("./products.json").getFile(),
                List.class)).willReturn(mockResponse);


        List<Product> result = productService.getProducts();

        assertAll("Expected should be the same than result",
                () -> assertEquals(expected.size(), result.size()),
                () -> assertEquals(expected.get(2).getName(), result.get(2).getName()),
                () -> assertEquals(expected.get(1).getType(), result.get(1).getType())
        );

    }

    private List<Product> createListProductOnlyForTestPurpose(){
        List<Product> products = new ArrayList<Product>();
        Product coffee = new Product("coffee", CoffeeSize.large, 2.30, ProductType.coffee, "EUR");
        products.add(coffee);

        Product orangeJuice = new Product("orange juice", null, 2.30, ProductType.juice, "EUR");
        products.add(orangeJuice);

        Product cupcake = new Product("cupcake", null, 3.30, ProductType.snack, "EUR");
        products.add(cupcake);

        return products;
    }
}
