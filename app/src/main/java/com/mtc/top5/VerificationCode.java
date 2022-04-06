package com.mtc.top5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

public class VerificationCode extends AppCompatActivity {
    private EditText etcode;
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_code);
        etcode = findViewById(R.id.usercode);
    }

    public void save(View view) {
        code = etcode.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://51.38.80.233/victory/email.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("success")) {
                    Toast.makeText(getApplicationContext(), "Email Verified", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(VerificationCode.this, LoginActivity.class);
                    startActivity(intent);
                    finish();



                } else {
                    System.out.println("failure");
                }
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
                data.put("email", SignUpActivity.email);
                data.put("verification_code", code);
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }
}