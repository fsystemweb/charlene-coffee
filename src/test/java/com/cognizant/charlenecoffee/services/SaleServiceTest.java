package com.cognizant.charlenecoffee.services;

import com.cognizant.charlenecoffee.enums.CoffeeSize;
import com.cognizant.charlenecoffee.enums.ProductType;
import com.cognizant.charlenecoffee.models.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
public class SaleServiceTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private SaleServiceImpl saleService;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void readProducts_testWithRightData() throws Exception {
        String[] availableProduct = {"large coffee", "orange juice", "small coffee with extra milk" };

        Mockito.when(productService.getProducts()).thenReturn(createListProductOnlyForTestPurpose());


        Product largeCoffee = new Product("coffee", CoffeeSize.large, 3.30, ProductType.coffee, "EUR");
        Product orangeJuice = new Product("orange juice", null, 2.30, ProductType.juice, "EUR");
        Product smallCoffee = new Product("coffee", CoffeeSize.small, 2.30, ProductType.coffee, "EUR");
        Product extraMilk = new Product("extra milk", null, 1.30, ProductType.extra, "EUR");

        List<Product> expected = new ArrayList<Product>();
        expected.add(largeCoffee);
        expected.add(orangeJuice);
        expected.add(smallCoffee);
        expected.add(extraMilk);


        List<Product> result = saleService.readProducts(availableProduct);

        assertAll("Expected should be the same than result",
                () -> Assertions.assertEquals(expected.get(0).getName(), result.get(0).getName()),
                () -> Assertions.assertEquals(expected.get(0).getSize(), result.get(0).getSize()),
                () -> Assertions.assertEquals(expected.get(1).getName(), result.get(1).getName()),
                () -> Assertions.assertEquals(expected.get(1).getType(), result.get(1).getType()),
                () -> Assertions.assertEquals(expected.get(2).getName(), result.get(2).getName()),
                () -> Assertions.assertEquals(expected.get(3).getName(), result.get(3).getName())
        );

    }

    @Test(expected = Exception.class)
    public void readProducts_testWithWrongData() throws Exception {
        String[] availableProduct = { "tiny coffee with extra whisky" };

        Mockito.when(productService.getProducts()).thenReturn(createListProductOnlyForTestPurpose());

        List<Product> result = saleService.readProducts(availableProduct);
    }

    private List<Product> createListProductOnlyForTestPurpose(){
        List<Product> products = new ArrayList<Product>();
        Product largeCoffee = new Product("coffee", CoffeeSize.large, 3.30, ProductType.coffee, "EUR");
        products.add(largeCoffee);

        Product smallCoffee = new Product("coffee", CoffeeSize.small, 2.30, ProductType.coffee, "EUR");
        products.add(smallCoffee);

        Product orangeJuice = new Product("orange juice", null, 2.30, ProductType.juice, "EUR");
        products.add(orangeJuice);

        Product cupcake = new Product("cupcake", null, 3.30, ProductType.snack, "EUR");
        products.add(cupcake);

        Product extraMilk = new Product("extra milk", null, 1.30, ProductType.extra, "EUR");
        products.add(extraMilk);

        return products;
    }

}
