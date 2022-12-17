package com.mycompany.myapp.domain;

import com.mycompany.myapp.domain.idgenerator.IDGenerator;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.Size;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "delivery")
public class Delivery implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "delivery_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "delivery_generator")
    @GenericGenerator(
        name = "delivery_generator",
        strategy = "com.mycompany.myapp.domain.idgenerator.IDGenerator",
        parameters = {
            @Parameter(name = IDGenerator.INITIAL_PARAM, value = "6"),
            @Parameter(name = IDGenerator.INCREMENT_PARAM, value = "1"),
            @Parameter(name = IDGenerator.VALUE_PREFIX_PARAMETER, value = "DE"),
            @Parameter(name = IDGenerator.NUMBER_FORMAT_PARAMETER, value = "%04d"),
        }
    )
    //    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Column(name = "delivery_date")
    private Instant delivery_date;

    @Size(max = 200)
    @Column(name = "delivery_message")
    private String message;

    @Column(name = "delivery_occasion")
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
