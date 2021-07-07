package com.example.instagram;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.instagram.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";
    private EditText etUsername;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityLoginBinding loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(loginBinding.getRoot());

        etUsername = loginBinding.etUsername;
        etPassword = loginBinding.etPassword;
        final Button btnLogin = loginBinding.btnLogin;

        btnLogin.setOnClickListener(new LoginButtonViewOnClickListener());
    }

    private void loginUser(String username, String password) {
        Log.i(TAG, "Attempted to login user " + username);
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
}