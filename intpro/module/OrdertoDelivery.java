package com.binary2quantumtechbase.andapp.intpro.module;

public class OrdertoDelivery {
    private String custumer_name, custumer_mobile, custumer_email, custumer_address,
            product, brand, category1, category2, quantity, Single_price, amount, id, reference_code, delivery_status;


    public OrdertoDelivery() {

    }

    public String getCategory1() {
        return category1;
    }

    public void setCategory1(String category1) {
        this.category1 = category1;
    }

    public String getCategory2() {
        return category2;
    }

    public void setCategory2(String category2) {
        this.category2 = category2;
    }

    public String getDelivery_status() {
        return delivery_status;
    }

    public void setDelivery_status(String delivery_status) {
        this.delivery_status = delivery_status;
    }

    public String getReference_code() {
        return reference_code;
    }

    public void setReference_code(String reference_code) {
        this.reference_code = reference_code;
    }

    public String getCustumer_name() {
        return custumer_name;
    }

    public void setCustumer_name(String custumer_name) {
        this.custumer_name = custumer_name;
    }

    public String getCustumer_mobile() {
        return custumer_mobile;
    }

    public void setCustumer_mobile(String custumer_mobile) {
        this.custumer_mobile = custumer_mobile;
    }

    public String getCustumer_email() {
        return custumer_email;
    }

    public void setCustumer_email(String custumer_email) {
        this.custumer_email = custumer_email;
    }

    public String getCustumer_address() {
        return custumer_address;
    }

    public void setCustumer_address(String custumer_address) {
        this.custumer_address = custumer_address;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

//    public String getThickness() {
//        return thickness;
//    }
//
//    public void setThickness(String thickness) {
//        this.thickness = thickness;
//    }
//
//    public String getSize() {
//        return size;
//    }
//
//    public void setSize(String size) {
//        this.size = size;
//    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getSingle_price() {
        return Single_price;
    }

    public void setSingle_price(String single_price) {
        Single_price = single_price;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "OrdertoDelivery{" +
                "custumer_name='" + custumer_name + '\'' +
                ", custumer_mobile='" + custumer_mobile + '\'' +
                ", custumer_email='" + custumer_email + '\'' +
                ", custumer_address='" + custumer_address + '\'' +
                ", product='" + product + '\'' +
                ", brand='" + brand + '\'' +
                ", category1='" + category1 + '\'' +
                ", category2='" + category2 + '\'' +
                ", quantity='" + quantity + '\'' +
                ", Single_price='" + Single_price + '\'' +
                ", amount='" + amount + '\'' +
                ", id='" + id + '\'' +
                ", reference_code='" + reference_code + '\'' +
                ", delivery_status='" + delivery_status + '\'' +
                '}';
    }
}
