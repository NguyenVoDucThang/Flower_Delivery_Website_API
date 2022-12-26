package com.mycompany.myapp.domain;

import com.mycompany.myapp.domain.group.CartStatus;
import com.mycompany.myapp.domain.idgenerator.IDGenerator;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "cart")
public class Cart extends AbstractAuditingEntity<String> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_generator")
    @GenericGenerator(
        name = "cart_generator",
        strategy = "com.mycompany.myapp.domain.idgenerator.IDGenerator",
        parameters = {
            @Parameter(name = IDGenerator.INITIAL_PARAM, value = "10"),
            @Parameter(name = IDGenerator.INCREMENT_PARAM, value = "1"),
            @Parameter(name = IDGenerator.VALUE_PREFIX_PARAMETER, value = "HD"),
            @Parameter(name = IDGenerator.NUMBER_FORMAT_PARAMETER, value = "%06d"),
        }
    )
    //    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Column(name = "cart_status")
    @Enumerated(EnumType.STRING)
    private CartStatus status;

    @Column(name = "cart_total")
    private int total;

    @ManyToOne(cascade = CascadeType.PERSIST, targetEntity = Receiver.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_receiver_id", referencedColumnName = "receiver_id")
    private Receiver receiver;

    @ManyToOne(cascade = CascadeType.PERSIST, targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_user_id", referencedColumnName = "id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = Delivery.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_delivery_id", referencedColumnName = "delivery_id")
    private Delivery delivery;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
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
