package com.binary2quantum.android.intpro.module;

import android.support.annotation.NonNull;

/**
 * Created by HP on 1/22/2019.
 */

public class cartItem {

    private String product, brand, quantity, amount, id;
    private String thickness, size;


    public cartItem(String id, String product, String brand, String quantity, String amount, String thickness, String size) {
        this.product = product;
        this.brand = brand;
        this.quantity = quantity;
        this.amount = amount;
        this.size = size;
        this.thickness = thickness;
        this.id = id;
    }

    @NonNull
    @Override
    public String toString() {
        return "cartItem{" +
                "product='" + product + '\'' +
                ", brand='" + brand + '\'' +
                ", quantity='" + quantity + '\'' +
                ", amount='" + amount + '\'' +
                ", id='" + id + '\'' +
                ", thickness='" + thickness + '\'' +
                ", size='" + size + '\'' +
                '}';
    }

    public String getThickness() {

        return thickness;
    }

    public void setThickness(String thickness) {
        this.thickness = thickness;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProduct() {
        return product;
    }

    public String getType() {
        return brand;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getAmount() {
        return amount;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public void setType(String brand) {
        this.brand = brand;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
