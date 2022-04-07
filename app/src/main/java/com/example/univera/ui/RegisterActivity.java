package com.example.univera.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.univera.R;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.username)         TextInputLayout username;
    @BindView(R.id.userEmail)        TextInputLayout useremail;
    @BindView(R.id.password)         TextInputLayout password;
    @BindView(R.id.age)              TextInputLayout age;
    @BindView(R.id.mobile_phone)     TextInputLayout mobile_phone;
    @BindView(R.id.register)         TextView register;
    @BindView(R.id.signin_text)      TextView signinText;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        SharedPreferences sharedPreferences;


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);

        sharedPreferences = getSharedPreferences("Userinfo",0);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameValue = username.getEditText().getText().toString();
                String useremailValue = useremail.getEditText().getText().toString();
                String passwordValue = password.getEditText().getText().toString();
                String ageValue = age.getEditText().getText().toString();
                String mobilePhoneValue = mobile_phone.getEditText().getText().toString();

                if (usernameValue.length() > 1) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", usernameValue);
                    editor.putString("useremail", useremailValue);
                    editor.putString("password", passwordValue);
                    editor.putString("age", ageValue);
                    editor.putString("mobile_phone", mobilePhoneValue);
                    editor.apply();
if (validateUsername() | validateUsermail() |validateAge() |validatePhone() |validatePassword() ){
    Toast.makeText(RegisterActivity.this, " Kullanıcı Oluşturuldu.", Toast.LENGTH_SHORT).show();
    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
}

                }else {
                    Toast.makeText(RegisterActivity.this, " Boş alanları doldurunuz.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signinText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);

            }
        });

    }
    private boolean validateUsername() {

        String usernameValue = username.getEditText().getText().toString();

        if (usernameValue.isEmpty() ) {
            username.setError("Field can not be empty");
            return false;
        }
        else {
            username.setError(null);
            return true;
        }

    }
    private boolean validateUsermail() {

        String useremailValue = useremail.getEditText().getText().toString();


        if (useremailValue.isEmpty() ) {
            useremail.setError("Field can not be empty");
            return false;
        }
        else {
            useremail.setError(null);
            return true;
        }

    }
    private boolean validateAge() {

        String ageValue = age.getEditText().getText().toString();



        if (ageValue.isEmpty() ) {
            age.setError("Field can not be empty");
            return false;
        }
        else {
            age.setError(null);
            return true;
        }

    } private boolean validatePhone() {

        String mobilePhoneValue = mobile_phone.getEditText().getText().toString();



        if (mobilePhoneValue.isEmpty() ) {
            mobile_phone.setError("Field can not be empty");
            return false;
        }
        else {
            mobile_phone.setError(null);
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
            password.setError("Password must be at least be 5 characters");
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }

}
