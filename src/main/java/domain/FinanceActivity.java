package domain;

import java.util.Date;

public class FinanceActivity {
    private String kind; //income or expense
    private double amount;
    private String Description;
    private Date date;
    private ManagementUser reporter;

    public FinanceActivity(String kind, double amount, String description, Date date, ManagementUser reporter) {
        this.kind = kind;
        this.amount = amount;
        this.Description = description;
        this.date = date;
        this.reporter = reporter;
    }
}
