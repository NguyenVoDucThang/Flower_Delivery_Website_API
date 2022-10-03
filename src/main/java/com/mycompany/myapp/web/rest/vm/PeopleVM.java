package com.mycompany.myapp.web.rest.vm;

import com.mycompany.myapp.config.Constants;
import java.io.Serializable;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class PeopleVM implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int PASSWORD_MIN_LENGTH = 4;

    public static final int PASSWORD_MAX_LENGTH = 100;

    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    @Pattern(regexp = Constants.EMAIL_REGEX)
    private String email;

    public PeopleVM() {}

    public PeopleVM(String password, String email) {
        this.password = password;
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserVM{" + "password='" + password + '\'' + ", email='" + email + '\'' + '}';
    }
}
