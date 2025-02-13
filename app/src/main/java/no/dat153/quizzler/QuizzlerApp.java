package no.dat153.quizzler;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

public class QuizzlerApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

    }
}
