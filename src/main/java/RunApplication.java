import com.fasterxml.jackson.databind.ObjectMapper;
import controllers.*;
import models.Receipt;
import services.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class RunApplication {

    public static void main(String[] args) throws IOException {
        ProductService productService = new ProductServiceImpl(new ObjectMapper());
        SaleService saleService = new SaleServiceImpl(productService);

        DiscountService discountService = new DiscountServiceImpl();
        ReceiptService receiptService = new ReceiptServiceImpl();

        MainController mainController = new MainController(saleService, discountService, receiptService);

        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        String entryData = reader.readLine();

        Object result = mainController.createFinalReceipt(entryData);

        parserAndShowResult(result);
    }

    private static void parserAndShowResult(Object result){
        if(result.getClass() == Receipt.class){
            Receipt receipt = (Receipt) result;
            showResult(receipt);
        }else{
            System.out.println(result.toString());
        }
    }

    private static void showResult(Receipt receipt){
        System.out.println(receipt.getTitle());
        System.out.println("Date: "+receipt.getDatetime());

        System.out.println("Products: ");
        System.out.println("------------");
        receipt.getRows().stream().forEach(row -> {
            System.out.println(row.getDescription()+" "+row.getPrice()+" "+row.getType());
        });
        System.out.println(" ");
        System.out.println("-------------------------------");
        System.out.println("Total: "+receipt.getTotal());
        System.out.println(receipt.getFinalMessage());
    }
}
