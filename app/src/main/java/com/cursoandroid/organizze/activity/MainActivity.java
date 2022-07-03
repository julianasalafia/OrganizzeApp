package com.cursoandroid.organizze.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cursoandroid.organizze.R;
import com.cursoandroid.organizze.config.ConfigurationFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;

public class MainActivity extends IntroActivity {
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setButtonBackVisible(false);
        setButtonNextVisible(false);

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_1)
                .build());

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_2)
                .build());

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_3)
                .build());

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_4)
                .build());

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_signup)
                .canGoForward(false)
                .build());
    }

    @Override
    protected void onStart() {
        super.onStart();
        verifyUserLogin();
    }

    public void signUp(View view) {
        startActivity(new Intent(this, SignUpActivity.class));
    }

    public void logIn(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void verifyUserLogin() {
        auth = ConfigurationFirebase.getFirebaseAuth();
        //auth.signOut();
        if (auth.getCurrentUser() != null) {
            openMainScreen();
        }
    }

    public void openMainScreen() {
        startActivity(new Intent(this, PrincipalActivity.class));
    }
}
