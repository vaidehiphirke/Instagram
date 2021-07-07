package com.example.instagram;

import com.parse.Parse;

import android.app.Application;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(BuildConfig.CONSUMER_KEY)
                .clientKey(BuildConfig.CONSUMER_SECRET)
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
