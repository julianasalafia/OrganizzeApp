package com.cursoandroid.organizze.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cursoandroid.organizze.R;
import com.cursoandroid.organizze.config.ConfigurationFirebase;
import com.cursoandroid.organizze.helper.Base64Custom;
import com.cursoandroid.organizze.helper.DateCustom;
import com.cursoandroid.organizze.model.Transaction;
import com.cursoandroid.organizze.model.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class IncomesActivity extends AppCompatActivity {
    private TextInputEditText fieldDate, fieldCategory, fieldDescription;
    private EditText fieldAmount;
    private Transaction transaction;
    private DatabaseReference firebaseRef = ConfigurationFirebase.getFirebaseDatabase();
    private FirebaseAuth auth = ConfigurationFirebase.getFirebaseAuth();
    private Double totalIncome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incomes);

        fieldDate = findViewById(R.id.editDate);
        fieldCategory = findViewById(R.id.editCategory);
        fieldDescription = findViewById(R.id.editDescription);
        fieldAmount = findViewById(R.id.editAmount);

        fieldDate.setText(DateCustom.currentData());
        recoverTotalIncome();
    }

    public void saveIncome(View view) {
        if (validadeIncomeField()) {
            transaction = new Transaction();
            String date = fieldDate.getText().toString();
            Double recoveredAmount = Double.parseDouble(fieldAmount.getText().toString());

            transaction.setAmount(recoveredAmount);
            transaction.setCategory(fieldCategory.getText().toString());
            transaction.setDescription(fieldDescription.getText().toString());
            transaction.setDate(date);
            transaction.setType("i");

            Double updatedIncome = totalIncome + recoveredAmount;
            updateIncomes(updatedIncome);

            transaction.save(date);
            finish();
        }
    }

    public Boolean validadeIncomeField() {
        String textAmount = fieldAmount.getText().toString();
        String textDate = fieldDate.getText().toString();
        String textCategory = fieldCategory.getText().toString();
        String textDescription = fieldDescription.getText().toString();

        if (!textAmount.isEmpty()) {
            if (!textDate.isEmpty()) {
                if (!textCategory.isEmpty()) {
                    if (!textDescription.isEmpty()) {
                        return true;
                    } else {
                        Toast.makeText(IncomesActivity.this,
                                "Give us a description",
                                Toast.LENGTH_SHORT).show();
                        return false;
                    }
                } else {
                    Toast.makeText(IncomesActivity.this,
                            "What category is it from?",
                            Toast.LENGTH_SHORT).show();
                    return false;
                }
            } else {
                Toast.makeText(IncomesActivity.this,
                        "When did you make the transaction?",
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(IncomesActivity.this,
                    "What's the amount?",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void recoverTotalIncome() {
        String emailUser = auth.getCurrentUser().getEmail();
        String idUser = Base64Custom.encode64Base(emailUser);
        DatabaseReference userRef = firebaseRef.child("users").child(idUser);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                totalIncome = user.getTotalIncome();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void updateIncomes(Double income) {
        String emailUser = auth.getCurrentUser().getEmail();
        String idUser = Base64Custom.encode64Base(emailUser);
        DatabaseReference userRef = firebaseRef.child("users").child(idUser);

        userRef.child("totalIncome").setValue(income);
    }
}
