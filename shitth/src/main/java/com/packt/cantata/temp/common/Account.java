package com.packt.cantata.temp.common;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "Account")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type" , discriminatorType = DiscriminatorType.STRING)
public class  Account extends EntityBase {


    @Column(name = "username", nullable = false, length = 20, unique = true)
    private String username;

    @Column(name = "password", nullable = false, length = 20)
    private String password;

    public Account() {
    }

    public Account(String username, String password) {
        this.username = username;
        if (passwordValidation(password))
            this.password = password;
        else
            throw new RuntimeException("Invalid Password");
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (passwordValidation(password))
            this.password = password;
        else
            throw new RuntimeException("Invalid Password");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account that = (Account) o;
        return Objects.equals(id, that.id) && Objects.equals(username, that.username) && Objects.equals(password, that.password);
    }

    public boolean passwordValidation(String password){
        if (password == null) return true;
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,20}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
