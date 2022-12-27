package com.mycompany.myapp.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mycompany.myapp.domain.Cart;
import com.mycompany.myapp.domain.Delivery;
import com.mycompany.myapp.domain.Receiver;
import com.mycompany.myapp.domain.group.CartStatus;
import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

public class CartDTO implements Serializable {

    private static final long serialVersionUUID = 1L;

    private String id;

    private CartStatus status;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int total;

    private Receiver receiver;

    private Delivery delivery;

    private UserDTO user;

    private Set<ProductCartDTO> products;

    public CartDTO() {}

    public CartDTO(Cart cart) {
        this.id = cart.getId();
        this.status = cart.getStatus();
        this.total = cart.getTotal();
        if (cart.getUser() != null) this.user = new UserDTO(cart.getUser());
        this.receiver = cart.getReceiver();
        this.delivery = cart.getDelivery();
        if (cart.getCartProductSet() != null && !cart.getCartProductSet().isEmpty()) this.products =
            cart.getCartProductSet().stream().map(ProductCartDTO::new).collect(Collectors.toSet());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CartStatus getStatus() {
        return status;
    }

    public void setStatus(CartStatus status) {
        this.status = status;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Set<ProductCartDTO> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductCartDTO> products) {
        this.products = products;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return (
            "CartDTO{" +
            "id='" +
            id +
            '\'' +
            ", status=" +
            status +
            ", total=" +
            total +
            ", receiver=" +
            receiver +
            ", delivery=" +
            delivery +
            ", user=" +
            user +
            ", products=" +
            products +
            '}'
        );
    }
}
