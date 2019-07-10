package com.example.privateex.pandorasurvey.Class;

public class CategoriesClass {

    private String ID;
    private String Category;

    public CategoriesClass(String id, String category) {
        this.ID = id;
        this.Category = category;
    }

    public String getId() {
        return ID;
    }

    public String getCategory() {
        return Category;
    }
}
