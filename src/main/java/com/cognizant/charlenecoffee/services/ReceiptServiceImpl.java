package com.cognizant.charlenecoffee.services;

import com.cognizant.charlenecoffee.enums.ProductType;
import com.cognizant.charlenecoffee.enums.TypeRowReceipt;
import com.cognizant.charlenecoffee.models.Product;
import com.cognizant.charlenecoffee.models.Receipt;
import com.cognizant.charlenecoffee.models.RowReceipt;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static com.cognizant.charlenecoffee.Utils.Constants.*;

@Service
public class ReceiptServiceImpl implements ReceiptService {

    public Receipt createReceipt(List<Product> saleList){
        Receipt newReceipt = new Receipt();
        newReceipt.setTitle(RECEIPT_TITLE);
        newReceipt.setFinalMessage(RECEIPT_FINAL_MESSAGE);

        addDatetime(newReceipt);
        addRowsAndTotal(newReceipt, saleList);

        return newReceipt;
    }

    private void addDatetime(Receipt receipt){
        SimpleDateFormat formatter= new SimpleDateFormat(FORMAT_DATE);
        Date date = new Date(System.currentTimeMillis());
        receipt.setDatetime(formatter.format(date));
    }

    private void addRowsAndTotal(Receipt receipt, List<Product> saleList){
        AtomicReference<Double> total = new AtomicReference<>(0.0);
        List<RowReceipt> rowReceiptList = new ArrayList<RowReceipt>();
        saleList.stream().forEach(item -> {
            RowReceipt rowReceipt;
            if(item.getType() == ProductType.coffee){
                rowReceipt = new RowReceipt(item.getSize()+" "+item.getName(), item.getPrice()
                        , TypeRowReceipt.DEBIT);

            }else if(item.getType() == ProductType.discount){
                rowReceipt = new RowReceipt(item.getName(), item.getPrice(), TypeRowReceipt.CREDIT);
                total.updateAndGet(v -> v - item.getPrice());
            }
            else{
                rowReceipt = new RowReceipt(item.getName(), item.getPrice(), TypeRowReceipt.DEBIT);
                total.updateAndGet(v -> v + item.getPrice());
            }

            rowReceiptList.add(rowReceipt);
        });
        DecimalFormat twoDForm = new DecimalFormat("#.00");
        receipt.setTotal(twoDForm.format(total.get()));
        receipt.setRows(rowReceiptList);
    }
}
