package com.suyin.books_store;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SelectBookActivity extends AppCompatActivity {

    private Button btnSave, btnList;
    private  TextView textBook, booktoAdd ;
    private ListView mListView;
    private DataBaseHelperBooks db;
    private String title, infoBook ;
    private double price;
    private int selectedID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_book);

        Toolbar toolbar =(Toolbar)  findViewById(R.id.toolbar_main);
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        TextView sing_up = findViewById(R.id.toolbar_text);
        setSupportActionBar(toolbar);
        toolbar_title.setText("About this book");

        btnList = findViewById(R.id.btnList);
        btnSave = findViewById(R.id.btnSave);
        textBook = findViewById(R.id.textBook);
        booktoAdd = findViewById(R.id.editable_item);
        db= new DataBaseHelperBooks(this);

        Intent receivedIntent = getIntent();
        title = receivedIntent.getStringExtra("title");
        infoBook = receivedIntent.getStringExtra("info");
        price = receivedIntent.getDoubleExtra("price", price);
        booktoAdd.setText("Book: "+title);
        textBook.setText(infoBook);


        sing_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectBookActivity.this, MainActivity.class));
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData(title, price);
                btnSave.setBackground(getResources().getDrawable(R.drawable.btn_click));
                btnSave.setTextColor(getResources().getColor(R.color.colorPrimary));
                btnSave.setText("Added to the cart");
                btnSave.setEnabled(false);

            }

        });

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent listIntent = new Intent(SelectBookActivity.this, ListActivity.class);
                startActivity(listIntent);
            }
        });


    }

    public void addData(String title, double price){
        long res =  db.addData(title, price,1);

        if(res>0){
            Toast.makeText(this, "Data Successfully Inserted", Toast.LENGTH_SHORT).show();
            db.close();
        }

        else{
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }

    }
}
