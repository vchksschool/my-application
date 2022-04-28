package com.mtc.top5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MyProfileActivity extends AppCompatActivity {

    private EditText name, password,email, confirmp;
    private LinearLayout editB;
    private Button cancelB, saveB;
    private TextView profileText;
    private LinearLayout btnlayout;
    private Dialog progressDialog;
    private TextView dialogText;
    private String namestr, passStr, cpassStr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("My Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog = new Dialog(MyProfileActivity.this);
        progressDialog.setContentView(R.layout.dialog_layout);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogText = progressDialog.findViewById(R.id.dialog_text);
        dialogText.setText("Saving data...");

        btnlayout = findViewById(R.id.buttonlayout);

        name = findViewById(R.id.mp_name);
        email = findViewById(R.id.mp_email);
        password = findViewById(R.id.mp_pass);
        confirmp = findViewById(R.id.mp_confpass);
        cancelB = findViewById(R.id.mpcancelb);
        saveB = findViewById(R.id.saveb);
        profileText = findViewById(R.id.profiletext);
        editB = findViewById(R.id.editB);

        disableEditing();
        editB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableEdits();
            }
        });

        cancelB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableEditing();
            }
        });

        saveB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate())
                {
                    savedata();
                }
            }
        });

    }
    private Boolean validate()
    {
        namestr = name.getText().toString();
        passStr = password.getText().toString();
        cpassStr = confirmp.getText().toString();
        if(namestr.isEmpty())
        {
            name.setError("Name can't be empty !");
            return false;

        }
        if(passStr.isEmpty())
        {
            password.setError("Password can't be empty!");
            return false;

        }
        if(cpassStr.isEmpty())
        {
            confirmp.setError("Confirm password can't be empty!");
            return false;

        }
        if (!cpassStr.equals(passStr))
        {
            confirmp.setError("Please ensure both passwords match!");
            return false;
        }
        return true;


    }
    private void  savedata()

    {
        progressDialog.show();
        DBqueries.saveNewProfileData(namestr, passStr, this, new MyCompleteListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(MyProfileActivity.this, "New User details saved!",Toast.LENGTH_SHORT).show();
                disableEditing();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure() {
                Toast.makeText(MyProfileActivity.this, "Something went wrong, please try again later",Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });


    }

    private void enableEdits()
    {
        name.setEnabled(true);
        password.setEnabled(true);
        confirmp.setEnabled(true);
        btnlayout.setVisibility(View.VISIBLE);
    }
    private void disableEditing()
    {
        name.setEnabled(false);
        email.setEnabled(false);
        password.setEnabled(false);
        confirmp.setEnabled(false);
        btnlayout.setVisibility(View.GONE);//makes buttons invisible(until user clicks edit button)
        name.setText(DBqueries.myProfile.getName());
        email.setText(DBqueries.myProfile.getEmail());
        profileText.setText(DBqueries.myProfile.getName().toUpperCase().substring(0,1));

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == android.R.id.home);{
            MyProfileActivity.this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

}