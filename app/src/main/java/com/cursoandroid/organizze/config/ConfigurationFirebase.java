package com.cursoandroid.organizze.config;

import com.google.firebase.auth.FirebaseAuth;

public class ConfigurationFirebase {
    private static FirebaseAuth auth;

    public static FirebaseAuth getFirebaseAuth() {
        if (auth == null) {
            auth = FirebaseAuth.getInstance();
        }
        return auth;
    }
}
