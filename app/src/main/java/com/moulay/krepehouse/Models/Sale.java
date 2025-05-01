package com.moulay.krepehouse.Models;

public class Sale {
    private String saleNumber;
    private String dateTime;
    private int productsCount;
    private double amount;

    public Sale(String saleNumber, String dateTime, int productsCount, double amount) {
        this.saleNumber = saleNumber;
        this.dateTime = dateTime;
        this.productsCount = productsCount;
        this.amount = amount;
    }

    // Getters
    public String getSaleNumber() { return saleNumber; }
    public String getDateTime() { return dateTime; }
    public int getProductsCount() { return productsCount; }
    public double getAmount() { return amount; }
}
