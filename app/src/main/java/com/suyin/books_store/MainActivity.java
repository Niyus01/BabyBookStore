package com.suyin.books_store;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText mTextUsername;
    EditText mTextPassword;
    Button mButtonLogin;
    TextView mTextViewRegister;
    DataBaseHelperLogin db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        });
    }
}
