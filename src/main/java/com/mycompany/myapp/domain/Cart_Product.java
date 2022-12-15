package com.mycompany.myapp.domain;

import com.mycompany.myapp.domain.composite_key.CartProductKey;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Entity
@Table("cart_product")
public class Cart_Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private CartProductKey id;

    @ManyToOne
    @MapsId("cartId")
    @JoinColumn(name = "cp_cart_id", referencedColumnName = "cart_id", table = "cart", nullable = false)
    private Cart cart;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "cp_product_id", referencedColumnName = "product_id", table = "product", nullable = false)
    private Product product;

    @Column("cp_quantity")
    private int quantity;

    @Column("total")
    private int total = quantity * product.getPrice();

    public Cart_Product() {}

    public Cart_Product(Cart cart, Product product, int quantity) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
    }

    public Cart_Product(CartProductKey id, Cart cart, Product product, int quantity) {
        this.id = id;
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cart_Product)) return false;
        Cart_Product that = (Cart_Product) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
