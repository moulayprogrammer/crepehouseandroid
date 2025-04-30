package com.moulay.krepehouse.Models;

public class PurchasedProduct {
    private int imageResId;
    private String nameArabic;
    private String nameFrench;
    private String quantity;
    private String price;

    public PurchasedProduct(int imageResId, String nameArabic, String nameFrench, String quantity, String price) {
        this.imageResId = imageResId;
        this.nameArabic = nameArabic;
        this.nameFrench = nameFrench;
        this.quantity = quantity;
        this.price = price;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getNameArabic() {
        return nameArabic;
    }

    public String getNameFrench() {
        return nameFrench;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getPrice() {
        return price;
    }
}


