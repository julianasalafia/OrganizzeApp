package com.cursoandroid.organizze.activity;

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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class SignUpActivity extends AppCompatActivity {
    private EditText fieldName, fieldEmail, fieldPassword;
    private Button buttonSignUp;
    private FirebaseAuth auth;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fieldName = findViewById(R.id.editName);
        fieldEmail = findViewById(R.id.editEmail);
        fieldPassword = findViewById(R.id.editPassword);
        buttonSignUp = findViewById(R.id.buttonSignup);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textName = fieldName.getText().toString();
                String textEmail = fieldEmail.getText().toString();
                String textPassword = fieldPassword.getText().toString();

                if (!textName.isEmpty()) {
                    if (!textEmail.isEmpty()) {
                        if (!textPassword.isEmpty()) {
                            user = new User();
                            user.setName(textName);
                            user.setEmail(textEmail);
                            user.setPassword(textPassword);
                            signUpUser();
                        } else {
                            Toast.makeText(SignUpActivity.this,
                                    "Choose your password carefully",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SignUpActivity.this,
                                "Give us your email",
                                Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(SignUpActivity.this,
                            "What's your name?",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void signUpUser() {
        auth = ConfigurationFirebase.getFirebaseAuth();
        auth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    finish();
                } else {
                    String exception = "";

                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        exception = "You need a strong password!";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        exception = "You need a valid email!";
                    } catch (FirebaseAuthUserCollisionException e) {
                        exception = "This account was already registered";
                    } catch (Exception e) {
                        exception = "Ops, something went wrong! " + e.getMessage();
                        e.printStackTrace();
                    }

                    Toast.makeText(SignUpActivity.this,
                            exception,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
