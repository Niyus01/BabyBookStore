package com.suyin.books_store;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.database.Cursor;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
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

        btnList = findViewById(R.id.btnList);
        btnSave = findViewById(R.id.btnSave);
        textBook = findViewById(R.id.textBook);
        booktoAdd = findViewById(R.id.editable_item);
        db= new DataBaseHelperBooks(this);

        Intent receivedIntent = getIntent();
        title = receivedIntent.getStringExtra("title");
        infoBook = receivedIntent.getStringExtra("info");
        price = receivedIntent.getDoubleExtra("price", price);

        booktoAdd.setText("Book name: "+title);
        textBook.setText("Information about this book: "+ infoBook);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData(title, price);
                btnSave.setEnabled(false);

            }

        });

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent listIntent = new Intent(SelectBookActivity.this, ListActivity.class);
              //  listIntent.putExtra("db",db);
                startActivity(listIntent);
            }
        });


        }
    public void addData(String title, double price){
      //  db.getWritableDatabase();
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
