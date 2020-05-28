package com.suyin.books_store;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

  EditText mTextUsername;
  EditText mTextPassword;
  Button mButtonLogin;
  TextView mTextViewRegister, sing_up, toolbar_title;
  DataBaseHelperLogin db;
  FirebaseAuth mFirebaseAuth;
  private FirebaseAuth.AuthStateListener mAuthStateListener;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);


    Toolbar toolbar =findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    toolbar_title = findViewById(R.id.toolbar_title);
    toolbar_title.setText("Baby Book Store");

    mFirebaseAuth = FirebaseAuth.getInstance();
    db = new DataBaseHelperLogin(this);
    mTextUsername = findViewById(R.id.edittext_username);
    mTextPassword = findViewById(R.id.edittext_password);
    mButtonLogin = findViewById(R.id.button_login);
    mTextViewRegister = findViewById(R.id.textview_register);

    mAuthStateListener = new FirebaseAuth.AuthStateListener() {


      @Override
      public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser != null) {
          Toast.makeText(MainActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
        }

      }
    };

    mTextViewRegister.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(registerIntent);
      }
    });


    mButtonLogin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String email = mTextUsername.getText().toString().trim();
        String pwd = mTextPassword.getText().toString().trim();

        if (email.isEmpty()) {
          mTextUsername.setError("Please enter email id");
          mTextUsername.requestFocus();
        } else if (pwd.isEmpty()) {
          mTextPassword.setError("Please enter your password");
          mTextPassword.requestFocus();
        }
        else if(pwd.length()<6){
          mTextPassword.setError("Password must be >=6 Characters");
          mTextPassword.requestFocus();

        }
        else if (email.isEmpty() && pwd.isEmpty()) {
          Toast.makeText(MainActivity.this, "Field are Empty", Toast.LENGTH_SHORT).show();

        } else if (!(email.isEmpty() && pwd.isEmpty())) {
          mFirebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
              if (!task.isSuccessful()) {
                Toast.makeText(MainActivity.this, "SignIn Unsuccessful, Please Try Again", Toast.LENGTH_SHORT).show();
              } else {
                Toast.makeText(MainActivity.this, "Successfully logged", Toast.LENGTH_SHORT).show();
                Intent storeIntent = new Intent(MainActivity.this, myaccountActivity.class);
                startActivity(storeIntent);
              }
            }
          });
        } else {
          Toast.makeText(MainActivity.this, "Error Ocurred!", Toast.LENGTH_SHORT).show();
        }
      }
    });

  }
  @Override
    protected void onStart(){
      super.onStart();
      mFirebaseAuth.addAuthStateListener(mAuthStateListener);

    }
  }


