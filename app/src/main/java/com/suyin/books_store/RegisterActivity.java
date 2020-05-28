package com.suyin.books_store;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

  EditText text_useremail;
  EditText text_password;
  Button button_Register;
  TextView textview_Login, toolbar_title;
  DataBaseHelperLogin db;
  FirebaseAuth firebase;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register);

    db = new DataBaseHelperLogin(this);
    text_useremail = findViewById(R.id.edittext_useremail);
    text_password = findViewById(R.id.edittext_password);
    button_Register= findViewById(R.id.button_register);
    textview_Login =  findViewById(R.id.textview_login);
    firebase = FirebaseAuth.getInstance();


    Toolbar toolbar =findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
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
        String email = text_useremail.getText().toString().trim();
        String pwd = text_password.getText().toString().trim();

        if(email.isEmpty()){
          text_useremail.setError("Please enter email id");
          text_useremail.requestFocus();
        }
        else if (pwd.isEmpty()){
          text_password.setError("Please enter your password");
          text_password.requestFocus();
        }
        else if(pwd.length()<6){
          text_password.setError("Password must be >=6 Characters");
          text_password.requestFocus();

        }
        else if (email.isEmpty()&&pwd.isEmpty()){
          text_password.setError("Field is empty");
          text_password.requestFocus();
          text_useremail.setError("Filed is empty");
          text_useremail.requestFocus();
        }
        else if (!(email.isEmpty()&&pwd.isEmpty())){
          firebase.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
              if(task.isSuccessful()){
                Toast.makeText(RegisterActivity.this,"SignUp Successful, Please Login in", Toast.LENGTH_SHORT).show();
                text_useremail.setText("");
                text_password.setText("");

              }
              else
                Toast.makeText(RegisterActivity.this,"SignUp Usuccessful, ", Toast.LENGTH_SHORT).show();

            }
          });
        }
        else {
          Toast.makeText(RegisterActivity.this,"Error Ocurred!", Toast.LENGTH_SHORT).show();

        }

      }
    });
  }
}
