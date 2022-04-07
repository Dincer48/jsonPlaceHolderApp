package com.example.univera.ui;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.example.univera.MainActivity;
import com.example.univera.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[@#$%^&+=])" +
                    "(?=\\S+$)" +
                    ".{4,}" +
                    "$");
    SharedPreferences preferences;

            @BindView(R.id.username)       TextInputLayout username;
            @BindView(R.id.password)        TextInputLayout password;
            @BindView(R.id.login)            TextView login;
            @BindView(R.id.registerText)        TextView registerText;

    @Override
    protected void onCreate(Bundle savedInstanceState){



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        preferences = getSharedPreferences("Userinfo", 0);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameValue = username.getEditText().getText().toString();
                String passwordValue = password.getEditText().getText().toString();

                String registeredUserName = preferences.getString("username","");
                String registeredPassword = preferences.getString("password","");

                if (validateEmail() | validatePassword()) {
                    if (usernameValue.equals(registeredUserName)  &&  passwordValue.equals(registeredPassword)){
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }

            }
        });

        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    private boolean validateEmail() {

        String emailInput = username.getEditText().getText().toString().trim();

        if (emailInput.isEmpty()) {
            username.setError("Field can not be empty");
            return false;
        }

         else {
            username.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = password.getEditText().getText().toString().trim();
        if (passwordInput.isEmpty()) {
            password.setError("Field can not be empty");
            return false;
        }

        else if (passwordInput.length() < 5) {
            password.setError("Password must be at least be 8 characters");
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }
}
