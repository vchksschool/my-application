package com.mtc.top5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    private EditText etName, etEmail, etPassword, etReenterPassword;
    private TextView tvStatus;
    private Button btnRegister;
    private ImageView backB;

    private String URL = "http://51.38.80.233/victory/register.php";
    public static String name, email, password, reenterPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        etName = findViewById(R.id.usernameID);
        etEmail = findViewById(R.id.emailID);
        etPassword = findViewById(R.id.loginpasswordID);
        etReenterPassword = findViewById(R.id.confirm_passID);
        tvStatus = findViewById(R.id.tvStatus);
        btnRegister = findViewById(R.id.signup_button);
        name = email = password = reenterPassword = "";
        backB = findViewById(R.id.sibackB);

        backB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpActivity.this.finish();
            }
        });
    }

    public void save(View view) {
        name = etName.getText().toString().trim();
        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        reenterPassword = etReenterPassword.getText().toString().trim();

        if(!name.equals("") && !email.equals("") && !password.equals(""))
        {
            if(!password.equals(reenterPassword))
            {

                Toast.makeText(this, "Password Mismatch", Toast.LENGTH_SHORT).show();
            }
            else
            {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    if (response.contains("success")) {

                        tvStatus.setText("Verify your email");

                        btnRegister.setClickable(false);
                        Intent intent = new Intent(SignUpActivity.this, VerificationCode.class);
                        startActivity(intent);
                        finish();
                        
                    } else{
                        tvStatus.setText(response);                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    data.put("username", name);
                    data.put("email", email);
                    data.put("password", password);
                    return data;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

            requestQueue.add(stringRequest);
        }


        }
        else
        {
            Toast.makeText(getApplicationContext(), "Please make sure No fields are empty", Toast.LENGTH_SHORT).show();
        }
    }
    public void login(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}