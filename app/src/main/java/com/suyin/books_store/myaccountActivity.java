package com.suyin.books_store;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class myaccountActivity extends AppCompatActivity {

    private   Button openStore, viewData;
    private   TextView sing_up,toolbar_title;
    private  FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myaccount);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar =findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        sing_up = findViewById(R.id.toolbar_text);
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Baby Book Store");

        openStore =  findViewById(R.id.buttonStore);
        viewData = findViewById(R.id.buttonView);

        sing_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(myaccountActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

        openStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(myaccountActivity.this, Store.class);
                startActivity(intent);
            }
        });

        viewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent (myaccountActivity.this, ListActivity.class);
                startActivity(intent1);
            }
        });
    }
}
