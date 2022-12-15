package com.mycompany.myapp.domain;

import com.mycompany.myapp.domain.group.ProductType;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Entity
@Table("product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @org.springframework.data.annotation.Id
    @Column("product_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prod-generator")
    @GenericGenerator(
        name = "prod-generator",
        strategy = "com.mycompany.myapp.domain.idgenerator.IDGenerator",
        parameters = { @Parameter(name = "prefix", value = "SP") }
    )
    private String id;

    @Column("product_name")
    private String name;

    @Column("product_type")
    private ProductType type;

    @Column("product_price")
    private int price;

    @Column("product_feature")
    private String feature;

    @Column("product_detail")
    private String detail;

    @Column("product_image_url")
    private String image_url;

    @Column("product_sold_num")
    private int sold_num;

    @Column("product_quantity")
    private int quantity;

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
