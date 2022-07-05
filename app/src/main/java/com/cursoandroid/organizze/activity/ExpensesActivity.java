package com.cursoandroid.organizze.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cursoandroid.organizze.R;
import com.cursoandroid.organizze.helper.DateCustom;
import com.cursoandroid.organizze.model.Transaction;
import com.google.android.material.textfield.TextInputEditText;

public class ExpensesActivity extends AppCompatActivity {
    private TextInputEditText fieldDate, fieldCategory, fieldDescription;
    private EditText fieldAmount;
    private Transaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);

        fieldDate = findViewById(R.id.editData);
        fieldCategory = findViewById(R.id.editCategory);
        fieldDescription = findViewById(R.id.editDescription);
        fieldAmount = findViewById(R.id.editAmount);

        fieldDate.setText(DateCustom.currentData());
    }

    public void saveExpense(View view) {
        if (validateExpenseField()) {
            transaction = new Transaction();
            String date = fieldDate.getText().toString();
            transaction.setAmount(Double.parseDouble(fieldAmount.getText().toString()));
            transaction.setCategory(fieldCategory.getText().toString());
            transaction.setDescription(fieldDescription.getText().toString());
            transaction.setDate(date);
            transaction.setType("e");

            transaction.save(date);
        }
    }

    public Boolean validateExpenseField() {
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
                        Toast.makeText(ExpensesActivity.this,
                                "Give us a description",
                                Toast.LENGTH_SHORT).show();
                        return false;
                    }
                } else {
                    Toast.makeText(ExpensesActivity.this,
                            "What category is it from?",
                            Toast.LENGTH_SHORT).show();
                    return false;
                }
            } else {
                Toast.makeText(ExpensesActivity.this,
                        "When did you make the transaction?",
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(ExpensesActivity.this,
                    "What's the amount?",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
