package com.mycompany.myapp.domain;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Column("name")
    private String name;

    @Column("type")
    private ProductType type;

    @Column("price")
    private int price;

    @Column("feature")
    private String feature;

    @Column("detail")
    private String detail;

    @Column("image_url")
    private String image_url;

    @Column("sold_num")
    private int sold_num;

    @Column("quantity")
    private int quantity;

    @OneToMany(mappedBy = "product")
    private Set<Cart_Product> cartProductSet;

    public Product() {}

    public Product(String name, ProductType type, int price, String feature, String detail, String image_url, int sold_num, int quantity) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.feature = feature;
        this.detail = detail;
        this.image_url = image_url;
        this.sold_num = sold_num;
        this.quantity = quantity;
    }

    public Product(
        String id,
        String name,
        ProductType type,
        int price,
        String feature,
        String detail,
        String image_url,
        int sold_num,
        int quantity
    ) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.feature = feature;
        this.detail = detail;
        this.image_url = image_url;
        this.sold_num = sold_num;
        this.quantity = quantity;
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
            "Product{" +
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
