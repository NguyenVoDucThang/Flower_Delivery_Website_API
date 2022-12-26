package com.mycompany.myapp.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mycompany.myapp.domain.Cart_Product;
import java.io.Serializable;

public class ProductCartDTO implements Serializable {

    private static final long serialVersionUUID = 1L;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String id;

    private String name;

    private int quantity;

    private int total;

    public ProductCartDTO() {}

    public ProductCartDTO(Cart_Product cart_product) {
        this.id = cart_product.getProduct().getId();
        this.name = cart_product.getProduct().getName();
        this.quantity = cart_product.getQuantity();
        this.total = this.quantity * cart_product.getProduct().getPrice();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "ProductCartDTO{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", quantity=" + quantity + ", total=" + total + '}';
    }
}
