package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mycompany.myapp.config.Constants;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("jhi_people")
public class People extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Pattern(regexp = Constants.EMAIL_REGEX)
    @Size(min = 5, max = 254)
    private String email;

    @JsonIgnore
    @NotNull
    @Size(min = 60, max = 60)
    @Column("password_hash")
    private String password;

    @NotNull
    private boolean activated = false;

    @Size(min = 6, max = 6)
    @Column("activation_key")
    @JsonIgnore
    private String activationKey;

    @JsonIgnore
    @Transient
    private Set<Authority> authorities = new HashSet<>();

    public People() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String toString() {
        return (
            "People{" +
            "id=" +
            id +
            ", email='" +
            email +
            '\'' +
            ", password='" +
            password +
            '\'' +
            ", activated=" +
            activated +
            ", activationKey='" +
            activationKey +
            '\'' +
            ", authorities=" +
            authorities +
            '}'
        );
    }
}
