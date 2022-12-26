package com.mycompany.myapp.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mycompany.myapp.domain.Product;
import com.mycompany.myapp.domain.group.ProductType;
import java.io.Serializable;

public class ProductDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    private ProductType type;

    private int price;

    private String feature;

    private String detail;

    private String image_url;

    private int sold_num;

    private int quantity;

    public ProductDTO() {}

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.type = product.getType();
        this.price = product.getPrice();
        this.feature = product.getFeature();
        this.detail = product.getDetail();
        this.image_url = product.getImage_url();
        this.sold_num = product.getSold_num();
        this.quantity = product.getQuantity();
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

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public int getSold_num() {
        return sold_num;
    }

    public void setSold_num(int sold_num) {
        this.sold_num = sold_num;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return (
            "ProductDTO{" +
            "id='" +
            id +
            '\'' +
            ", name='" +
            name +
            '\'' +
            ", type=" +
            type +
            ", price=" +
            price +
            ", feature='" +
            feature +
            '\'' +
            ", detail='" +
            detail +
            '\'' +
            ", image_url='" +
            image_url +
            '\'' +
            ", sold_num=" +
            sold_num +
            ", quantity=" +
            quantity +
            '}'
        );
    }
}
