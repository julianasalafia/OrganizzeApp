package com.cursoandroid.organizze.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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
