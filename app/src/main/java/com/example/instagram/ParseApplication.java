package com.example.instagram;

import com.example.instagram.models.Post;
import com.parse.Parse;
import com.parse.ParseObject;

import android.app.Application;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Post.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(BuildConfig.CONSUMER_KEY)
                .clientKey(BuildConfig.CONSUMER_SECRET)
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
