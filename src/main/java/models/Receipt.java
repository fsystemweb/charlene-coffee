package models;

import java.util.List;

public class Receipt {
    private String title;
    private String datetime;
    private List<RowReceipt> rows;
    private String total;
    private String finalMessage;

    public Receipt() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public List<RowReceipt>  getRows() {
        return rows;
    }

    public void setRows(List<RowReceipt> rows) {
        this.rows = rows;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getFinalMessage() {
        return finalMessage;
    }

    public void setFinalMessage(String finalMessage) {
        this.finalMessage = finalMessage;
    }
}
