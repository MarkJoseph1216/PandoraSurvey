package com.example.privateex.pandorasurvey.Class;

public class AdsClass {

    private String ID;
    private String CategoryAds;

    public AdsClass(String id, String category) {
        this.ID = id;
        this.CategoryAds = category;
    }

    public String getId() {
        return ID;
    }

    public String getCategory() {
        return CategoryAds;
    }
}
