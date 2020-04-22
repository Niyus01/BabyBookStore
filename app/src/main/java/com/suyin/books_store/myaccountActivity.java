package com.suyin.books_store;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

public class myaccountActivity extends AppCompatActivity {

    Button openStore, viewData;
    TextView sing_up,toolbar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myaccount);
        openStore =  findViewById(R.id.buttonStore);
        viewData = findViewById(R.id.buttonView);

        Toolbar toolbar =findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        sing_up = findViewById(R.id.toolbar_text);
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Baby Book Store");


        sing_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(myaccountActivity.this, MainActivity.class));
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
