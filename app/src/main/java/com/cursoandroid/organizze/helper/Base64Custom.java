package com.cursoandroid.organizze.helper;

import android.util.Base64;

public class Base64Custom {

    public static String encode64Base(String text) {
        return Base64.encodeToString(text.getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)", "");
    }

    public static String decode64Base(String encodedText) {
        return new String (Base64.decode(encodedText, Base64.DEFAULT));
    }
}
