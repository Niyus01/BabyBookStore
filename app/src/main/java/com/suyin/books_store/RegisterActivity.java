package com.suyin.books_store;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

  EditText text_username;
  EditText text_password;
  EditText text_confpassword;
  Button button_Register;
  TextView textview_Login, toolbar_title;
  DataBaseHelperLogin db;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register);
    this.setTitle("Register");

    db = new DataBaseHelperLogin(this);
    text_username = findViewById(R.id.edittext_username);
    text_password = findViewById(R.id.edittext_password);
    text_confpassword = findViewById(R.id.edittext_conf_password);
    button_Register= findViewById(R.id.button_register);
    textview_Login =  findViewById(R.id.textview_login);

    Toolbar toolbar =findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayShowTitleEnabled(false);
    toolbar_title = findViewById(R.id.toolbar_title);
    toolbar_title.setText("Baby Book Store");

    textview_Login.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent loginIntent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(loginIntent);
      }
    });

    button_Register.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String user = text_username.getText().toString().trim();
        String pwd = text_password.getText().toString().trim();
        String conf_pwd = text_confpassword.getText().toString().trim();

        if(user.isEmpty()){
          text_username.setError("Please enter email id");
        }

        else if (pwd.isEmpty()){
          text_password.setError("Please enter your password");
        }

        else if (user.isEmpty()&&pwd.isEmpty()){
          Toast.makeText(RegisterActivity.this,"Field are Empty", Toast.LENGTH_SHORT).show();
        }

        else if (!(user.isEmpty()&&pwd.isEmpty())) {

          if (pwd.equals(conf_pwd)) {
            long val = db.addData(user, pwd);

            if (val > 0){
              Toast.makeText(RegisterActivity.this, "You have registered", Toast.LENGTH_SHORT).show();

            button_Register.setBackground(getResources().getDrawable(R.drawable.btn_click));
            button_Register.setTextColor(getResources().getColor(R.color.colorPrimary));
            button_Register.setText("Registered");
            button_Register.setEnabled(false);}

            else
              Toast.makeText(RegisterActivity.this, pwd + "Registration error", Toast.LENGTH_SHORT).show();

          } else {
            Toast.makeText(RegisterActivity.this, "Password is not matching", Toast.LENGTH_SHORT).show();
          }
        }
      }
    });
  }
}
