package com.example.shopping.Ui;
public class Model {
    private String title;
    private String price;
    private int image;

    public Model(String title, String price, int image) {
        this.title = title;
        this.price = price;
        this.image = image;
    }

    public Model() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getImg() {
        return image;
    }

    public void setImg(int image) {
        this.image = image;
    }
}
