package com.example.administrator.streetfood.Category;

public class Category {
    private int id;
    private String catName, catDesc;

    public Category() {
    }

    public Category(String catName, String catDesc) {
        this.catName = catName;
        this.catDesc = catDesc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatDesc() {
        return catDesc;
    }

    public void setCatDesc(String catDesc) {
        this.catDesc = catDesc;
    }
}
