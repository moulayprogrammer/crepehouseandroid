package com.moulay.krepehouse.Models;

public class Product {
    private int imageResId;
    private String nameArabic;
    private String nameFrench;
    private String price;

    public Product(int imageResId, String nameArabic, String nameFrench, String price) {
        this.imageResId = imageResId;
        this.nameArabic = nameArabic;
        this.nameFrench = nameFrench;
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

    public String getPrice() {
        return price;
    }
}

