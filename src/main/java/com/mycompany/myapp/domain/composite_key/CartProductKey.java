package com.mycompany.myapp.domain.composite_key;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CartProductKey implements Serializable {

    @Column(name = "cp_cart_id")
    private String cartId;

    @Column(name = "cp_product_id")
    private String productId;

    public CartProductKey() {}

    public CartProductKey(String cartId, String productId) {
        this.cartId = cartId;
        this.productId = productId;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartProductKey)) return false;
        CartProductKey that = (CartProductKey) o;
        return Objects.equals(cartId, that.cartId) && Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartId, productId);
    }
}
