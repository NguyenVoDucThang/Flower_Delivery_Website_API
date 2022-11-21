package com.mycompany.myapp.domain;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("cart")
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Column("status")
    private CartStatus status;

    @Column("total")
    private int total;

    @ManyToOne
    @JoinColumn(name = "receiver_id", referencedColumnName = "receiver.id")
    private Receiver receiver;

    @OneToOne
    @JoinColumn(name = "delivery_id", referencedColumnName = "delivery.id")
    private Delivery delivery;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user.id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "cart")
    private Set<Cart_Product> cartProductSet;
}
