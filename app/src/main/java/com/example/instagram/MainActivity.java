package com.example.instagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.instagram.databinding.ActivityMainBinding;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityMainBinding mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        final Button btnLogout = mainBinding.btnLogout;
        btnLogout.setOnClickListener(new LogoutButtonViewOnClickListener());
    }

    private void goToLoginActivity() {
        final Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public class LogoutButtonViewOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            ParseUser.logOut();
            goToLoginActivity();
        }
    }
}