package com.mycompany.myapp.domain;

import com.mycompany.myapp.domain.group.ProductType;
import com.mycompany.myapp.domain.idgenerator.IDGenerator;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prod_generator")
    @GenericGenerator(
        name = "prod_generator",
        strategy = "com.mycompany.myapp.domain.idgenerator.IDGenerator",
        parameters = {
            @Parameter(name = IDGenerator.INITIAL_PARAM, value = "37"),
            @Parameter(name = IDGenerator.INCREMENT_PARAM, value = "1"),
            @Parameter(name = IDGenerator.VALUE_PREFIX_PARAMETER, value = "SP"),
            @Parameter(name = IDGenerator.NUMBER_FORMAT_PARAMETER, value = "%03d"),
        }
    )
    //    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Column(name = "product_name")
    private String name;

    @Column(name = "product_type")
    @Enumerated(EnumType.STRING)
    private ProductType type;

    @Column(name = "product_price")
    private int price;

    @Column(name = "product_feature")
    private String feature;

    @Column(name = "product_detail")
    private String detail;

    @Column(name = "product_image_url")
    private String image_url;

    @Column(name = "product_sold_num")
    private int sold_num;

    @Column(name = "product_quantity")
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
