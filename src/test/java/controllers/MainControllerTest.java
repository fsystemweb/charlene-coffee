package controllers;


import enums.CoffeeSize;
import enums.ProductType;
import enums.TypeRowReceipt;
import models.Product;
import models.Receipt;
import models.RowReceipt;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import services.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static utils.Constants.*;

@RunWith(SpringJUnit4ClassRunner.class)
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

        Object result = mainController.createFinalReceipt("");

        HashMap<String, String> expectedBody = new HashMap<>();
        expectedBody.put("error",ERROR_INVALID_PRODUCT + "coca-cola");

        assertEquals(expectedBody, result);
    }

    @Test
    public void createFinalReceipt_whenWorkFine() throws Exception {
        String[] testData = {""};
        when(saleService.readProducts(testData)).thenReturn(createListProductOnlyForTestPurpose());

        when(receiptService.createReceipt(new ArrayList<Product>())).thenReturn(createReceiptExpected());

        Object response = mainController.createFinalReceipt("");
        Receipt result = (Receipt) response;

        assertEquals(createReceiptExpected().getDatetime(), result.getDatetime());
        assertEquals(createReceiptExpected().getTotal(), result.getTotal());
        assertEquals(createReceiptExpected().getRows().get(1).getDescription(), result.getRows().get(1).getDescription());
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
