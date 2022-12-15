package com.mycompany.myapp.domain;

import com.mycompany.myapp.domain.group.CartStatus;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Entity
@Table("cart")
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @org.springframework.data.annotation.Id
    @Column("cart_id")
    @GeneratedValue(generator = "cart-generator")
    @GenericGenerator(
        name = "cart-generator",
        parameters = { @org.hibernate.annotations.Parameter(name = "prefix", value = "HD") },
        strategy = "com.mycompany.myapp.domain.idgenerator.IDGenerator"
    )
    private String id;

    @Column("cart_status")
    private CartStatus status;

    @Column("cart_total")
    private int total;

    @ManyToOne(cascade = CascadeType.PERSIST, targetEntity = Receiver.class)
    @JoinColumn(name = "cart_receiver_id", referencedColumnName = "receiver_id", table = "receiver")
    @Column("cart_receiver_id")
    private Receiver receiver;

    @ManyToOne(cascade = CascadeType.PERSIST, targetEntity = User.class)
    @JoinColumn(name = "cart_user_id", referencedColumnName = "id", table = "jhi_user")
    @Column("cart_user_id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = Delivery.class)
    @JoinColumn(name = "cart_delivery_id", referencedColumnName = "delivery_id", table = "delivery")
    @Column("cart_delivery_id")
    private Delivery delivery;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @Transient
    private Set<Cart_Product> cartProductSet = new LinkedHashSet<>();

    public Cart() {}

    public Cart(CartStatus status, int total, Receiver receiver, Delivery delivery, User user) {
        this.status = status;
        this.total = total;
        this.receiver = receiver;
        this.delivery = delivery;
        this.user = user;
    }

    public Cart(String id, CartStatus status, int total, Receiver receiver, Delivery delivery, User user) {
        this.id = id;
        this.status = status;
        this.total = total;
        this.receiver = receiver;
        this.delivery = delivery;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Cart_Product> getCartProductSet() {
        return cartProductSet;
    }

    public void setCartProductSet(Set<Cart_Product> cartProductSet) {
        this.cartProductSet = cartProductSet;
    }
}
