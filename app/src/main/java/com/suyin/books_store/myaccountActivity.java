package com.suyin.books_store;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class myaccountActivity extends AppCompatActivity {

    Button openStore, viewData;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myaccount);

        openStore =  findViewById(R.id.buttonStore);
        viewData = findViewById(R.id.buttonView);
        title = findViewById(R.id.title);
        title.setText("Baby Book Store");

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
