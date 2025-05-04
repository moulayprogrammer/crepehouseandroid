package com.moulay.krepehouse.Models;

public class PurchasedFood {

    private int id;
    private int idFood;
    private String nameAr;
    private int qte;
    private float total;

    public PurchasedFood(int idFood, String nameAr, int qte, float total) {
        this.idFood = idFood;
        this.nameAr = nameAr;
        this.qte = qte;
        this.total = total;
    }

    public PurchasedFood() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdFood() {
        return idFood;
    }

    public void setIdFood(int idFood) {
        this.idFood = idFood;
    }

    public String getNameAr() {
        return nameAr;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}


