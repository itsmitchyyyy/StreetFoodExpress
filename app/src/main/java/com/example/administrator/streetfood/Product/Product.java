package com.example.administrator.streetfood.Product;

public class Product {
    private int id, supId, categoryId;
    private String prodName, prodDesc;
    private double prodQty, prodPrice, totalAmount;
    private String prodImage;

    public Product() {
    }

    public Product(int supId, int categoryId, String prodName, String prodDesc, double prodQty, double prodPrice, String prodImage) {
        this.supId = supId;
        this.categoryId = categoryId;
        this.prodName = prodName;
        this.prodDesc = prodDesc;
        this.prodQty = prodQty;
        this.prodPrice = prodPrice;
        this.prodImage = prodImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSupId() {
        return supId;
    }

    public void setSupId(int supId) {
        this.supId = supId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdDesc() {
        return prodDesc;
    }

    public void setProdDesc(String prodDesc) {
        this.prodDesc = prodDesc;
    }

    public double getProdQty() {
        return prodQty;
    }

    public void setProdQty(double prodQty) {
        this.prodQty = prodQty;
    }

    public double getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(double prodPrice) {
        this.prodPrice = prodPrice;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getProdImage() {
        return prodImage;
    }

    public void setProdImage(String prodImage) {
        this.prodImage = prodImage;
    }
}
