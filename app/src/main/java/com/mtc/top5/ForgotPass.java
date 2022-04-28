package com.mtc.top5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ForgotPass extends AppCompatActivity {
    private EditText etEmailF;
    private String emailF;
    private ImageView backB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        etEmailF = findViewById(R.id.forgotemail);
        backB = findViewById(R.id.fbackB);

        backB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgotPass.this.finish();
            }
        });
    }

    public void save(View view) {
        emailF = etEmailF.getText().toString().trim();
        if (!emailF.equals("")) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://51.38.80.233/victory/forgotpassword.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.contains("success")) {
                    Toast.makeText(ForgotPass.this, "Email Sent", Toast.LENGTH_SHORT).show();
                    //String name =  GetName(email);//Extra call for getting actual username(not necessary/requires a further fetch).
                    //here get name and uid from database from field matching email

                    // MyThread.myProfile = new ProfileModel(email, name, uid);

                    //
                    Intent intent = new Intent(ForgotPass.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {

                    Toast.makeText(ForgotPass.this, response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ForgotPass.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                System.out.println(emailF);
                data.put("email", emailF);


                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }
        else
        {
            Toast.makeText(ForgotPass.this, "Type in a email adress", Toast.LENGTH_SHORT).show();
        }
    }


}