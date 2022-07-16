package com.cursoandroid.organizze.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.cursoandroid.organizze.R;
import com.cursoandroid.organizze.config.ConfigurationFirebase;
import com.cursoandroid.organizze.helper.Base64Custom;
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

public class PrincipalActivity extends AppCompatActivity {
    private MaterialCalendarView calendarView;
    private TextView textGreeting, textBalance;
    private Double totalExpense = 0.0;
    private Double totalIncome = 0.0;
    private Double userResume = 0.0;

    private FirebaseAuth auth = ConfigurationFirebase.getFirebaseAuth();
    private DatabaseReference firebaseRef = ConfigurationFirebase.getFirebaseDatabase();


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
        configureCalendarView();
        recoverResume();
    }

    public void recoverResume() {
        String emailUser = auth.getCurrentUser().getEmail();
        String idUser = Base64Custom.encode64Base(emailUser);
        final DatabaseReference userRef = firebaseRef.child("users").child(idUser);

        userRef.addValueEventListener(new ValueEventListener() {
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
        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {

            }
        });
    }
}
