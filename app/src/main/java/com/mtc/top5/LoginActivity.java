package com.mtc.top5;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private EditText loginemailID, loginpasswordID;
    private String email, password;
    private String URL = "http://51.38.80.233/victory/login.php";
    private Button loginB;


    private TextView forgotPassB, signupB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = password = "";
        loginemailID = findViewById(R.id.loginemailID);
        loginpasswordID = findViewById(R.id.loginpasswordID);
        loginB = findViewById(R.id.login_button);
        forgotPassB = findViewById(R.id.forgot_password);
        signupB = findViewById(R.id.signup_button);

        signupB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        forgotPassB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(LoginActivity.this, ForgotPass.class);
                startActivity(intent);

            }
        });













    }
    public void getscore()
    {   System.out.println(String.format("http://51.38.80.233/victory/getscore.php/?id=%s",String.valueOf(MyThread.myProfile.getUid())));

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest( String.format("http://51.38.80.233/victory/getscore.php/?id=%s",String.valueOf(MyThread.myProfile.getUid())), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println("array got");
                for (int i = 0; i < response.length(); i++) {
                    try {


                        JSONObject categories = response.getJSONObject(i);


                        String score = categories.getString("total_score").toString();

                        MyThread.myPerformance.setScore(Integer.valueOf(score));



                    } catch (JSONException e) {
                        System.out.println("nah didnt work");


                        e.printStackTrace();
                    }


                }

                //latch.countDown();


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);

            }
        });

        requestQueue.add(jsonArrayRequest);
    }
    public void getnameuid(MyCompleteListener completeListener)
    {   System.out.println(String.format("http://51.38.80.233/victory/getUID.php/?email=%s",email));

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest( String.format("http://51.38.80.233/victory/getUID.php?email=%s",email), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println("array got");
                for (int i = 0; i < response.length(); i++) {
                    try {


                        JSONObject categories = response.getJSONObject(i);


                        String name = categories.getString("username").toString();
                        String uid = categories.getString("id").toString();

                        MyThread.myProfile.setName(name);
                        MyThread.myProfile.setUid(Integer.valueOf(uid));
                        MyThread.myPerformance.setName(name);
                        completeListener.onSuccess();



                    } catch (JSONException e) {
                        System.out.println("nah didnt work");


                        e.printStackTrace();
                    }


                }

                //latch.countDown();


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);

            }
        });

        requestQueue.add(jsonArrayRequest);

    }

    public void login(View view) {
        email = loginemailID.getText().toString().trim();
        password = loginpasswordID.getText().toString().trim();

        if (!email.equals("") && !password.equals("")) {
            getnameuid(new MyCompleteListener() {
                @Override
                public void onSuccess() {
                    getscore();
                }

                @Override
                public void onFailure() {

                }
            });
            //getscore();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    if (response.contains("success")) {
                        Toast.makeText(LoginActivity.this, "valid Login Id/Password", Toast.LENGTH_SHORT).show();
                        //String name =  GetName(email);//Extra call for getting actual username(not necessary/requires a further fetch).
                        //here get name and uid from database from field matching email

                       // MyThread.myProfile = new ProfileModel(email, name, uid);

                        //
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(LoginActivity.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    data.put("email", email);
                    data.put("password", password);

                    return data;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }else{
            Toast.makeText(this, "Fields can not be empty!", Toast.LENGTH_SHORT).show();
        }
    }

    public void register(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }
    public String  GetName(String email){
        return email;

    }
}