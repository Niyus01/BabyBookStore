package com.suyin.books_store;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class MainActivity extends AppCompatActivity {

  EditText mTextUsername;
  EditText mTextPassword;
  Button mButtonLogin;
  TextView mTextViewRegister, sing_up, toolbar_title;
  DataBaseHelperLogin db;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    this.setTitle("Login");



    // Find the toolbar view inside the activity layout
    Toolbar toolbar =findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayShowTitleEnabled(false);
    toolbar_title = findViewById(R.id.toolbar_title);
    toolbar_title.setText("Baby Book Store");

    db = new DataBaseHelperLogin(this);

    mTextUsername= findViewById(R.id.edittext_username);
    mTextPassword = findViewById(R.id.edittext_password);
    mButtonLogin = findViewById(R.id.button_login);
    mTextViewRegister = findViewById(R.id.textview_register);



    mTextViewRegister.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        Intent registerIntent =  new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(registerIntent);


      }
    });

    mButtonLogin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String user = mTextUsername.getText().toString().trim();
        String pwd = mTextPassword.getText().toString().trim();

        if(user.isEmpty()){
          mTextUsername.setError("Please enter email id");
        }

        else if (pwd.isEmpty()){
          mTextPassword.setError("Please enter your password");
        }

        else if (user.isEmpty()&&pwd.isEmpty()){
          Toast.makeText(MainActivity.this,"Field are Empty", Toast.LENGTH_SHORT).show();
        }

        else if (!(user.isEmpty()&&pwd.isEmpty())){
          Boolean res = db.checkUser(user,pwd);

          if(res== true){
            Toast.makeText(MainActivity.this,"Successfully logged",Toast.LENGTH_SHORT).show();
            Intent storeIntent = new Intent(MainActivity.this, myaccountActivity.class);
            startActivity(storeIntent);
          }
          else{
            Toast.makeText(MainActivity.this,"Login Error ",Toast.LENGTH_SHORT).show();
          }

        }
      }
    });
  }
}
