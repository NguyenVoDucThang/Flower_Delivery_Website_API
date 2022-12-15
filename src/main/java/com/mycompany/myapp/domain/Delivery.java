package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.Size;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Entity
@Table("delivery")
public class Delivery implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @org.springframework.data.annotation.Id
    @Column("delivery_id")
    @GeneratedValue(generator = "delivery-generator")
    @GenericGenerator(
        name = "delivery-generator",
        parameters = { @Parameter(name = "prefix", value = "DE") },
        strategy = "com.mycompany.myapp.domain.idgenerator.IDGenerator"
    )
    private String id;

    @Column("delivery_date")
    private Instant delivery_date;

    @Size(max = 200)
    @Column("delivery_message")
    private String message;

    @Column("delivery_occasion")
    private String occasion;

    public Delivery(String id, Instant delivery_date, String message, String occasion) {
        this.id = id;
        this.delivery_date = delivery_date;
        this.message = message;
        this.occasion = occasion;
    }

    public Delivery(Instant delivery_date, String message, String occasion) {
        this.delivery_date = delivery_date;
        this.message = message;
        this.occasion = occasion;
    }

    public Delivery() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(Instant delivery_date) {
        this.delivery_date = delivery_date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOccasion() {
        return occasion;
    }

    public void setOccasion(String occasion) {
        this.occasion = occasion;
    }

    @Override
    public String toString() {
        return (
            "Delivery{" +
            "id='" +
            id +
            '\'' +
            ", delivery_date=" +
            delivery_date +
            ", message='" +
            message +
            '\'' +
            ", occasion='" +
            occasion +
            '\'' +
            '}'
        );
    }
}
