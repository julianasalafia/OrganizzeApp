package com.cursoandroid.organizze.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cursoandroid.organizze.R;
import com.cursoandroid.organizze.config.ConfigurationFirebase;
import com.cursoandroid.organizze.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginActivity extends AppCompatActivity {
    private EditText fieldEmail, fieldPassword;
    private Button buttonLogin;
    private User user;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fieldEmail = findViewById(R.id.editEmail);
        fieldPassword = findViewById(R.id.editPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textEmail = fieldEmail.getText().toString();
                String textPassword = fieldPassword.getText().toString();

                if (!textEmail.isEmpty()) {
                    if (!textPassword.isEmpty()) {
                        user = new User();
                        user.setEmail(textEmail);
                        user.setPassword(textPassword);
                        validateLogin();
                    } else {
                        Toast.makeText(LoginActivity.this,
                                "What's your password?",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this,
                            "What's your email?",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void validateLogin() {
        auth = ConfigurationFirebase.getFirebaseAuth();
        auth.signInWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    openMainScreen();
                } else {
                    String exception = "";

                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        exception = "Oh, it seems you're not registered!";

                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        exception = "Email and password don't match the registered user, try again.";
                    } catch (Exception e) {
                        exception = "Ops, something went wrong! " + e.getMessage();
                        e.printStackTrace();
                    }

                    Toast.makeText(LoginActivity.this,
                            exception,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void openMainScreen() {
        startActivity(new Intent(this, PrincipalActivity.class));
        finish();
    }
}
