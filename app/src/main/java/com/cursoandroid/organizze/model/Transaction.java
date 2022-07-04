package com.cursoandroid.organizze.model;

import com.cursoandroid.organizze.config.ConfigurationFirebase;
import com.cursoandroid.organizze.helper.Base64Custom;
import com.cursoandroid.organizze.helper.DateCustom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class Transaction {
    private String date;
    private String category;
    private String description;
    private String type;
    private double amount;

    public Transaction() {

    }

    public void save(String chosenDate) {
        FirebaseAuth auth = ConfigurationFirebase.getFirebaseAuth();
        String idUser = Base64Custom.encode64Base(auth.getCurrentUser().getEmail());
        String monthYear = DateCustom.monthYearDayChosenDate(chosenDate);

        DatabaseReference firebase = ConfigurationFirebase.getFirebaseDatabase();
        firebase.child("transactions")
                .child(idUser)
                .child(monthYear)
                .push()
                .setValue(this);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
