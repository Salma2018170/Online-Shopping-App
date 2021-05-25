package com.example.shopping.storage;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private String name;
    private String cost;
    private int quantities;
    private int signUpId;

    public Product(){}

    public Product(int id, String name, String cost, int quantities) { this.id = id; this.name = name; this.cost = cost; this.quantities = quantities;}

    public Product(String name, String cost, int quantities) { this.name = name; this.cost = cost; this.quantities = quantities;}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) { this.cost = cost; }

    public int getQuantities() {
        return quantities;
    }

    public void setQuantities(int quantities) {
        this.quantities = quantities;
    }
}
