package com.example.sqlitefromassetexample.model;

public class Product {
    private int id;
    private String name;
    private String price;
    private String descritpion;

    public Product(int id, String name, String price, String descritpion) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.descritpion = descritpion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescritpion() {
        return descritpion;
    }

    public void setDescritpion(String descritpion) {
        this.descritpion = descritpion;
    }
}
