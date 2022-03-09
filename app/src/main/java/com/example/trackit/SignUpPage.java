package com.example.trackit;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SignUpPage extends AppCompatActivity {

    ImageButton signUp, btnBack;
    EditText txtEmail, txtPassword, txtConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_page);

        signUp = findViewById(R.id.btnSign);
        btnBack = findViewById(R.id.btnBack);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtConfirm = findViewById(R.id.txtConfirm);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserModel userModel;
                String txtEmailText = txtEmail.getText().toString();
                String txtPasswordText = txtPassword.getText().toString();
                String txtConfirmText = txtConfirm.getText().toString();

                // Check if one of the fields are empty if not the sign up is successful
                if (txtEmailText.matches("") || txtPasswordText.matches("") || txtConfirmText.matches("")){
                    Toast.makeText(SignUpPage.this, "One of the fields are empty. Please try again.", Toast.LENGTH_SHORT).show();
                }else{
                    userModel = new UserModel(-1, txtEmail.getText().toString(), txtPassword.getText().toString());
                    UserDataBase userDataBase = new UserDataBase(SignUpPage.this);
                    // Check if the account is already signed up
                    if (!userDataBase.userSearch(txtEmailText, txtPasswordText)){
                        userDataBase.addUser(userModel);
                        Toast.makeText(SignUpPage.this, "You sign-up successfully. You can now log-in.", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(SignUpPage.this, "Error. This account is already exists.", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(v.getContext(), LandingPage.class);
                startActivity(back);
                finish();
            }
        });

    }


}
