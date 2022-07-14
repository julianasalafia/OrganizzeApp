package com.cursoandroid.organizze.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.cursoandroid.organizze.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

public class PrincipalActivity extends AppCompatActivity {
    private MaterialCalendarView calendarView;
    private TextView textGreeting;
    private TextView textBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        calendarView = findViewById(R.id.calendarView);
        textGreeting = findViewById(R.id.textGreeting);
        textBalance = findViewById(R.id.textBalance);
        configureCalendarView();

        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    public void addIncome(View view) {
        startActivity(new Intent(this, IncomesActivity.class));
    }

    public void addExpense(View view) {
        startActivity(new Intent(this, ExpensesActivity.class));
    }

    public void configureCalendarView() {
        CharSequence months[] =
                {"January","February","March", "April", "May", "June ", "July ", "August", "September", "October", "November", "December"};
                calendarView.setTitleMonths(months);
                calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
                    @Override
                    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {

                    }
                });
    }
}
