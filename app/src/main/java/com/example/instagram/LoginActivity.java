package com.example.instagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.instagram.databinding.ActivityLoginBinding;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";
    private EditText etUsername;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ParseUser.getCurrentUser() != null) {
            goToMainActivity();
        }

        final ActivityLoginBinding loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(loginBinding.getRoot());

        etUsername = loginBinding.etUsername;
        etPassword = loginBinding.etPassword;
        final Button btnLogin = loginBinding.btnLogin;

        btnLogin.setOnClickListener(new LoginButtonViewOnClickListener());
    }

    private void loginUser(String username, String password) {
        Log.i(TAG, "Attempted to login user " + username);
        ParseUser.logInInBackground(username, password, new InstagramLogInCallback());
    }

    private void goToMainActivity() {
        final Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private class LoginButtonViewOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Log.i(TAG, "onClick login button");
            final String username = etUsername.getText().toString();
            final String password = etPassword.getText().toString();
            loginUser(username, password);
        }
    }

    private class InstagramLogInCallback implements LogInCallback {

        @Override
        public void done(ParseUser user, ParseException e) {
            if (e != null) {
                Log.e(TAG, "Issue with login", e);
                Toast.makeText(LoginActivity.this, "Issue with login", Toast.LENGTH_SHORT).show();
                return;
            }
            goToMainActivity();
            Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
        }
    }
}