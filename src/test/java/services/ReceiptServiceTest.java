package services;


import enums.CoffeeSize;
import enums.ProductType;
import enums.TypeRowReceipt;
import models.Product;
import models.Receipt;
import models.RowReceipt;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static utils.Constants.*;

public class ReceiptServiceTest {
    @InjectMocks
    private ReceiptServiceImpl receiptService;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createReceipt_returnNewReceipt(){
        Receipt result = receiptService.createReceipt(createListProductOnlyForTestPurpose());

        Receipt expected = createExpectedReceipt();

        assertEquals(expected.getDatetime(), result.getDatetime());
        assertEquals(expected.getFinalMessage(), result.getFinalMessage());
        assertEquals(expected.getTitle(), result.getTitle());
        assertEquals(expected.getTotal(), result.getTotal());
        assertEquals(expected.getRows().get(2).getDescription(), result.getRows().get(2).getDescription());
        assertEquals(expected.getRows().get(4).getPrice(), result.getRows().get(4).getPrice());
        assertEquals(expected.getRows().get(1).getDescription(), result.getRows().get(1).getDescription());

    }

    private List<Product> createListProductOnlyForTestPurpose(){
        List<Product> products = new ArrayList<Product>();
        Product largeCoffee = new Product("coffee", CoffeeSize.large, 3.30, ProductType.coffee, "EUR");
        products.add(largeCoffee);

        Product smallCoffee = new Product("coffee", CoffeeSize.small, 2.30, ProductType.coffee, "EUR");
        products.add(smallCoffee);

        Product extraMilk = new Product("extra milk", null, 1.30, ProductType.extra, "EUR");
        products.add(extraMilk);

        Product orangeJuice = new Product("orange juice", null, 2.30, ProductType.juice, "EUR");
        products.add(orangeJuice);

        Product cupcake = new Product("cupcake", null, 3.30, ProductType.snack, "EUR");
        products.add(cupcake);

        return products;
    }

    private Receipt createExpectedReceipt(){
        Receipt expected = new Receipt();
        expected.setTitle(RECEIPT_TITLE);
        expected.setFinalMessage(RECEIPT_FINAL_MESSAGE);
        expected.setTotal("12.50");

        SimpleDateFormat formatter= new SimpleDateFormat(FORMAT_DATE);
        Date date = new Date(System.currentTimeMillis());
        expected.setDatetime(formatter.format(date));

        List<RowReceipt> rowReceiptList = new ArrayList<RowReceipt>();

        RowReceipt rowReceiptLargeCoffee = new RowReceipt("large coffee",3.30, TypeRowReceipt.DEBIT);
        rowReceiptList.add(rowReceiptLargeCoffee);
        RowReceipt rowReceiptSmallCoffee = new RowReceipt("small coffee",2.30, TypeRowReceipt.DEBIT);
        rowReceiptList.add(rowReceiptSmallCoffee);
        RowReceipt rowReceiptExtraMilk = new RowReceipt("extra milk",1.30, TypeRowReceipt.DEBIT);
        rowReceiptList.add(rowReceiptExtraMilk);
        RowReceipt rowReceiptOrangeJuice = new RowReceipt("orange juice",2.30, TypeRowReceipt.DEBIT);
        rowReceiptList.add(rowReceiptOrangeJuice);
        RowReceipt rowReceiptCupCake = new RowReceipt("cupcake",3.30, TypeRowReceipt.DEBIT);
        rowReceiptList.add(rowReceiptCupCake);

        expected.setRows(rowReceiptList);

        return expected;
    }
}
