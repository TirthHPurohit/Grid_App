package com.example.gridapp.model;

public class ImageModel {
    private UrlModel urls;

    public ImageModel(UrlModel urls){
        this.urls = urls;
    }

    public void setUrls(UrlModel urls) {
        this.urls = urls;
    }

    public UrlModel getUrls() {
        return urls;
    }
}
