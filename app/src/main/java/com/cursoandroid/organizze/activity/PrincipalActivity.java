package com.cursoandroid.organizze.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cursoandroid.organizze.R;
import com.cursoandroid.organizze.adapter.AdapterTransaction;
import com.cursoandroid.organizze.config.ConfigurationFirebase;
import com.cursoandroid.organizze.helper.Base64Custom;
import com.cursoandroid.organizze.model.Transaction;
import com.cursoandroid.organizze.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PrincipalActivity extends AppCompatActivity {
    private MaterialCalendarView calendarView;
    private TextView textGreeting, textBalance;
    private Double totalExpense = 0.0;
    private Double totalIncome = 0.0;
    private Double userResume = 0.0;

    private FirebaseAuth auth = ConfigurationFirebase.getFirebaseAuth();
    private DatabaseReference firebaseRef = ConfigurationFirebase.getFirebaseDatabase();
    private DatabaseReference userRef;
    private ValueEventListener valueEventListenerUser;
    private ValueEventListener valueEventListenerTransactions;

    private RecyclerView recyclerView;
    private AdapterTransaction adapterTransaction;
    private List<Transaction> transactions = new ArrayList<>();
    private DatabaseReference transactionRef;
    private String monthYearSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Organizze");
        setSupportActionBar(toolbar);

        calendarView = findViewById(R.id.calendarView);
        textGreeting = findViewById(R.id.textGreeting);
        textBalance = findViewById(R.id.textBalance);
        recyclerView = findViewById(R.id.recyclerTransactions);
        configureCalendarView();

        adapterTransaction = new AdapterTransaction(transactions, this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterTransaction);
    }

    public void recoverTransactions() {
        String emailUser = auth.getCurrentUser().getEmail();
        String idUser = Base64Custom.encode64Base(emailUser);

        transactionRef = firebaseRef.child("transactions")
        .child(idUser)
        .child(monthYearSelected);

        valueEventListenerTransactions = transactionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                transactions.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()) {
                  Transaction transaction = data.getValue(Transaction.class);
                  transactions.add(transaction);
                }
                adapterTransaction.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void recoverResume() {
        String emailUser = auth.getCurrentUser().getEmail();
        String idUser = Base64Custom.encode64Base(emailUser);
        userRef = firebaseRef.child("users").child(idUser);

        valueEventListenerUser = userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                totalExpense = user.getTotalExpense();
                totalIncome = user.getTotalIncome();
                userResume = totalIncome - totalExpense;

                DecimalFormat decimalFormat = new DecimalFormat("0.##");
                String formattedResult = decimalFormat.format(userResume);

                textGreeting.setText("Hey, " + user.getName());
                textBalance.setText("US$ " + formattedResult);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.closeMenu:
                auth.signOut();
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addIncome(View view) {
        startActivity(new Intent(this, IncomesActivity.class));
    }

    public void addExpense(View view) {
        startActivity(new Intent(this, ExpensesActivity.class));
    }

    public void configureCalendarView() {
        CharSequence months[] =
                {"January", "February", "March", "April", "May", "June ", "July ", "August", "September", "October", "November", "December"};
        calendarView.setTitleMonths(months);

        CalendarDay currentDate =  calendarView.getCurrentDate();
        String monthSelected = String.format("%02d", (currentDate.getMonth() + 1));
        monthYearSelected = String.valueOf( monthSelected + "" + currentDate.getYear());

        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                String monthSelected = String.format("%02d", (date.getMonth() + 1));
                monthYearSelected = String.valueOf(monthSelected + "" + date.getYear());

                transactionRef.removeEventListener(valueEventListenerTransactions);
                recoverTransactions();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        recoverResume();
        recoverTransactions();
    }

    @Override
    protected void onStop() {
        super.onStop();
        userRef.removeEventListener(valueEventListenerUser);
        transactionRef.removeEventListener(valueEventListenerTransactions);
    }
}
