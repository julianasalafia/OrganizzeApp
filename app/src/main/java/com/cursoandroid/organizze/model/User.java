package com.cursoandroid.organizze.model;

import com.cursoandroid.organizze.config.ConfigurationFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

public class User {
    private String idUser;
    private String name;
    private String email;
    private String password;
    private Double totalIncome = 0.00;
    private Double totalExpense = 0.00;

    public User() {

    }

    public void save() {
        DatabaseReference firebase = ConfigurationFirebase.getFirebaseDatabase();
        firebase.child("users").child(this.idUser).setValue(this);
    }

    public Double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(Double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public Double getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(Double totalExpense) {
        this.totalExpense = totalExpense;
    }

    @Exclude
    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
