package com.mtc.top5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private EditText email, pass;
    private Button loginB;
    private TextView forgotPassB, signupB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        loginB = findViewById(R.id.login_button);
        forgotPassB = findViewById(R.id.signup_button);
        signupB = findViewById(R.id.signup_button);

        loginB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateData()) {
                    login();
                }
            }
        });

        signupB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean validateData() {
        boolean status = false;
        if (email.getText().toString().isEmpty()) {
            email.setError("Please Enter a valid E-mail adress");
            return false;
        }
        if (pass.getText().toString().isEmpty()) {
            pass.setError("Please enter a password");
            return false;
        }
        return status;
    }

    private void login() {

    }

}