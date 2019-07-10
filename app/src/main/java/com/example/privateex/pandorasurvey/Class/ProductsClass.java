package com.example.privateex.pandorasurvey.Class;

public class ProductsClass {

    private String ID;
    private String CategoryProduct;

    public ProductsClass(String id, String category) {
        this.ID = id;
        this.CategoryProduct = category;
    }

    public String getId() {
        return ID;
    }

    public String getCategory() {
        return CategoryProduct;
    }
}
