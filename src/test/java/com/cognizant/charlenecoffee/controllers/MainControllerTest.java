package com.cognizant.charlenecoffee.controllers;

import com.cognizant.charlenecoffee.enums.CoffeeSize;
import com.cognizant.charlenecoffee.enums.ProductType;
import com.cognizant.charlenecoffee.enums.TypeRowReceipt;
import com.cognizant.charlenecoffee.models.Product;
import com.cognizant.charlenecoffee.models.Receipt;
import com.cognizant.charlenecoffee.models.RowReceipt;
import com.cognizant.charlenecoffee.services.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.cognizant.charlenecoffee.Utils.Constants.*;
import static com.cognizant.charlenecoffee.Utils.Constants.FORMAT_DATE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MainControllerTest {

    @Mock
    private SaleService saleService;

    @Mock
    private DiscountService discountService;

    @Mock
    private ReceiptService receiptService;

    @InjectMocks
    private MainController mainController;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createFinalReceipt_whenFail() throws Exception {
        String[] testData = {""};
        when(saleService.readProducts(testData)).thenThrow(new Exception(ERROR_INVALID_PRODUCT + "coca-cola"));

        HashMap<String, String> testEntryData = new HashMap<>();
        testEntryData.put("data","");
        ResponseEntity result = mainController.createFinalReceipt(testEntryData);

        HashMap<String, String> expectedBody = new HashMap<>();
        expectedBody.put("error",ERROR_INVALID_PRODUCT + "coca-cola");

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        Assertions.assertEquals(expectedBody, result.getBody());
    }

    @Test
    public void createFinalReceipt_whenWorkFine() throws Exception {
        String[] testData = {""};
        when(saleService.readProducts(testData)).thenReturn(createListProductOnlyForTestPurpose());

        when(receiptService.createReceipt(new ArrayList<Product>())).thenReturn(createReceiptExpected());

        HashMap<String, String> testEntryData = new HashMap<>();
        testEntryData.put("data","");
        ResponseEntity responseEntity = mainController.createFinalReceipt(testEntryData);
        Receipt result = (Receipt) responseEntity.getBody();

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(createReceiptExpected().getDatetime(), result.getDatetime());
        Assertions.assertEquals(createReceiptExpected().getTotal(), result.getTotal());
        Assertions.assertEquals(createReceiptExpected().getRows().get(1).getDescription(), result.getRows().get(1).getDescription());
    }

    private List<Product> createListProductOnlyForTestPurpose(){
        List<Product> products = new ArrayList<Product>();
        Product coffee = new Product("coffee", CoffeeSize.large, 2.3, ProductType.coffee, "EUR");
        products.add(coffee);

        Product orangeJuice = new Product("orange juice", null, 2.3, ProductType.juice, "EUR");
        products.add(orangeJuice);

        Product cupcake = new Product("cupcake", null, 3.3, ProductType.snack, "EUR");
        products.add(cupcake);

        return products;
    }

    private Receipt createReceiptExpected(){
        Receipt expected = new Receipt();
        expected.setTitle(RECEIPT_TITLE);
        expected.setFinalMessage(RECEIPT_FINAL_MESSAGE);
        expected.setTotal("7.90");

        SimpleDateFormat formatter= new SimpleDateFormat(FORMAT_DATE);
        Date date = new Date(System.currentTimeMillis());
        expected.setDatetime(formatter.format(date));

        List<RowReceipt> rowReceiptList = new ArrayList<RowReceipt>();

        RowReceipt coffee = new RowReceipt("large coffee", 2.3, TypeRowReceipt.DEBIT);
        rowReceiptList.add(coffee);
        RowReceipt orangeJuice = new RowReceipt("orange juice",  2.3, TypeRowReceipt.DEBIT);
        rowReceiptList.add(orangeJuice);
        RowReceipt cupcake = new RowReceipt("cupcake", 3.3, TypeRowReceipt.DEBIT);
        rowReceiptList.add(cupcake);



        expected.setRows(rowReceiptList);

        return expected;
    }
}
