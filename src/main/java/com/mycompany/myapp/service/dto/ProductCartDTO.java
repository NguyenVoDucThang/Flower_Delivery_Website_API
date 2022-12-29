package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.Cart_Product;
import java.io.Serializable;

public class ProductCartDTO implements Serializable {

    private static final long serialVersionUUID = 1L;

    private String id;

    private String name;

    private int quantity;

    private int price;

    private String image_url;

    private int total;

    public ProductCartDTO() {}

    public ProductCartDTO(Cart_Product cart_product) {
        this.id = cart_product.getProduct().getId();
        this.name = cart_product.getProduct().getName();
        this.quantity = cart_product.getQuantity();
        this.price = cart_product.getProduct().getPrice();
        this.image_url = cart_product.getProduct().getImage_url();
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    @Override
    public String toString() {
        return (
            "ProductCartDTO{" +
            "id='" +
            id +
            '\'' +
            ", name='" +
            name +
            '\'' +
            ", quantity=" +
            quantity +
            ", price=" +
            price +
            ", image_url=" +
            image_url +
            ", total=" +
            total +
            '}'
        );
    }
}
