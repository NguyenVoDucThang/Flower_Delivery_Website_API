package com.mycompany.myapp.domain;

import com.mycompany.myapp.config.Constants;
import com.mycompany.myapp.domain.idgenerator.IDGenerator;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "receiver")
public class Receiver implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "receiver_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "receiver_generator")
    @GenericGenerator(
        name = "receiver_generator",
        strategy = "com.mycompany.myapp.domain.idgenerator.IDGenerator",
        parameters = {
            @Parameter(name = IDGenerator.INITIAL_PARAM, value = "9"),
            @Parameter(name = IDGenerator.INCREMENT_PARAM, value = "1"),
            @Parameter(name = IDGenerator.VALUE_PREFIX_PARAMETER, value = "RE"),
            @Parameter(name = IDGenerator.NUMBER_FORMAT_PARAMETER, value = "%04d"),
        }
    )
    //    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Column(name = "receiver_name")
    private String name;

    @Column(name = "receiver_address")
    private String address;

    @Column(name = "receiver_email")
    @Pattern(regexp = Constants.EMAIL_REGEX)
    @Size(min = 5, max = 254)
    private String email;

    @Column(name = "receiver_phone_number")
    @Pattern(regexp = Constants.PHONE_NUMBER_REGEX)
    @Size(min = 10, max = 11)
    private String phone_number;

    public Receiver() {}

    public Receiver(String name, String address, String email, String phone_number) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone_number = phone_number;
    }

    public Receiver(String id, String name, String address, String email, String phone_number) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone_number = phone_number;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    @Override
    public String toString() {
        return (
            "Receiver{" +
            "id='" +
            id +
            '\'' +
            ", name='" +
            name +
            '\'' +
            ", address='" +
            address +
            '\'' +
            ", email='" +
            email +
            '\'' +
            ", phone_number='" +
            phone_number +
            '\'' +
            '}'
        );
    }
}
