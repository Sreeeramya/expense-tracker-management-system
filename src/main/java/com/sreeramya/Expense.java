package com.sreeramya;

public class Expense {

    private double amount;
    private String category;
    private String description;
    private String expenseDate;
    private int userId;

    public Expense(double amount,
                   String category,
                   String description,
                   String expenseDate,
                   int userId) {

        this.amount = amount;
        this.category = category;
        this.description = description;
        this.expenseDate = expenseDate;
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }
    public String getDescription() {
        return description;
    }
    public String getExpenseDate() {
        return expenseDate;
    }
    public int getUserId() {
        return userId;
    }
}