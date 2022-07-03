package com.cursoandroid.organizze.activity;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.cursoandroid.organizze.R;
import com.cursoandroid.organizze.helper.DateCustom;
import com.google.android.material.textfield.TextInputEditText;

public class ExpensesActivity extends AppCompatActivity {
    private TextInputEditText fieldData, fieldCategory, fieldDescription;
    private EditText fieldValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);

        fieldData = findViewById(R.id.editData);
        fieldCategory = findViewById(R.id.editCategory);
        fieldDescription = findViewById(R.id.editDescription);
        fieldValue = findViewById(R.id.editValue);

        fieldData.setText(DateCustom.currentData());
    }
}
