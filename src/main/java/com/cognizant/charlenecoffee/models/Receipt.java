package com.cognizant.charlenecoffee.models;

import java.util.Date;

public class Receipt {
    private String title;
    private Date datetime;
    private RowReceipt[] rows;
    private Double total;
    private String finalMessage;
}
