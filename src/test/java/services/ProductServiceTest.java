package services;


import com.fasterxml.jackson.databind.ObjectMapper;
import enums.CoffeeSize;
import enums.ProductType;
import models.Product;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
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
        Product[] mockResponse = createArrayProductOnlyForTestPurpose();
        List<Product> expected = createListProductOnlyForTestPurpose();

        given(objectMapper.readValue(new ClassPathResource("./products.json").getFile(),
                Product[].class)).willReturn(mockResponse);


        List<Product> result = productService.getProducts();


        assertEquals(expected.size(), result.size());
        assertEquals(expected.get(2).getName(), result.get(2).getName());
        assertEquals(expected.get(1).getType(), result.get(1).getType());
    }

    private Product[] createArrayProductOnlyForTestPurpose(){

        Product coffee = new Product("coffee", CoffeeSize.large, 2.30, ProductType.coffee, "EUR");
        Product orangeJuice = new Product("orange juice", null, 2.30, ProductType.juice, "EUR");
        Product cupcake = new Product("cupcake", null, 3.30, ProductType.snack, "EUR");

        Product[] products = {coffee, orangeJuice, cupcake};

        return products;
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
