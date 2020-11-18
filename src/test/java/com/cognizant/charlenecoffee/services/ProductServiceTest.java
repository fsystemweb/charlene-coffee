package com.cognizant.charlenecoffee.services;

import com.cognizant.charlenecoffee.models.Product;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ProductServiceTest {

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private ProductServiceImpl productService;


    @Test
    void getProducts_objectMapperIsCalled() {
        List<Product> expected = new ArrayList<Product>();
        List<Product> result = Mockito.when(objectMapper.readValue(anyString(), any(Product[].class)).thenReturn(expected));

        assertEquals(expected, result);

    }
}
