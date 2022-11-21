package com.mycompany.myapp.domain;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("cart_product")
public class Cart_Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private CartProductKey id;

    @ManyToOne
    @MapsId("cartId")
    @JoinColumn(name = "cart_id", referencedColumnName = "cart.id", nullable = false)
    private Cart cart;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id", referencedColumnName = "product.id", nullable = false)
    private Product product;

    @Column("quantity")
    private int quantity;

    @Column("total")
    private int total;

    public Cart_Product() {}

    public Cart_Product(Cart cart, Product product, int quantity, int total) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
        this.total = total;
    }

    public Cart_Product(CartProductKey id, Cart cart, Product product, int quantity, int total) {
        this.id = id;
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
        this.total = total;
    }

    public CartProductKey getId() {
        return id;
    }

    public void setId(CartProductKey id) {
        this.id = id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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
}
