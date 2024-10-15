package com.packt.cantata.temp;

import com.packt.cantata.temp.common.Account;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class CustomerEntity extends Account {

    @Column(name = "email", length = 20)
    private String email;

    @Column(name = "phoneNum", length = 20)
    private String phoneNum;

    @Column(name = "passport_id", length = 20, unique = true)
    private String passport_id;


    @OneToMany(mappedBy ="customer", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ReservationEntity> reservations = new ArrayList<>();


    public CustomerEntity (){
        super();
    }

    public CustomerEntity (String email, String phoneNum, String passport_id, String username, String password) {
        super(username, password);
        this.setEmail(email);
        this.phoneNum = phoneNum;
        this.passport_id = passport_id;
        this.reservations = new ArrayList<>();
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPassport_id() {
        return passport_id;
    }

    public void setPassport_id(String passport_id) {
        this.passport_id = passport_id;
    }

    public List<ReservationEntity> getReservations() {
        return reservations;
    }

    public void setReservations(List<ReservationEntity> reservations) {
        this.reservations = reservations;
    }
}







