package com.example.trackit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class LandingPage extends AppCompatActivity {

    ImageButton logIn;
    ImageButton signUp;
    EditText txtLogEmail, txtLogPassword;
    String txtLogEmailTxt, txtLogPasswordTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);

        logIn = findViewById(R.id.btnLogin);
        signUp = findViewById(R.id.btnSignUp);
        txtLogEmail = findViewById(R.id.txtLogEmail);
        txtLogPassword = findViewById(R.id.txtLogPassword);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtLogEmailTxt = txtLogEmail.getText().toString();
                txtLogPasswordTxt = txtLogPassword.getText().toString();

                // Check if one of the fields are empty if not the sign up is successful
                if (txtLogEmailTxt.matches("") || txtLogPasswordTxt.matches("")){
                    Toast.makeText(LandingPage.this, "One of the fields are empty. Please try again.", Toast.LENGTH_SHORT).show();
                }else{
                    UserDataBase userDataBase = new UserDataBase(LandingPage.this);
                    // Check if the account is already signed up then go to the dashboard
                    if (!userDataBase.userSearch(txtLogEmailTxt, txtLogPasswordTxt)){
                        Toast.makeText(LandingPage.this, "Account not found. Please sign-up.", Toast.LENGTH_SHORT).show();
                    }else{
                        Intent logs = new Intent(v.getContext(), Dashboard.class);
                        startActivity(logs);
                    }

                }

            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signs = new Intent(v.getContext(), SignUpPage.class);
                startActivity(signs);
            }
        });
    }
}