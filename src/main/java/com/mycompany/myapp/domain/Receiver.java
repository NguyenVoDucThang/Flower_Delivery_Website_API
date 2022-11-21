package com.mycompany.myapp.domain;

import com.mycompany.myapp.config.Constants;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("receiver")
public class Receiver implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Column("name")
    private String name;

    @Column("address")
    private String address;

    @Column("email")
    @Pattern(regexp = Constants.EMAIL_REGEX)
    @Size(min = 5, max = 254)
    private String email;

    @Column("phone_number")
    @Pattern(regexp = Constants.PHONE_NUMBER_REGEX)
    @Size(min = 10, max = 11)
    private String phone_number;

    @OneToMany(mappedBy = "receiver")
    private Set<Cart> carts;

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
