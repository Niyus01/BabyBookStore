package com.suyin.books_store;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
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

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    private static final String TAG="ListActivity";
    private DataBaseHelperBooks db;
    private Button btnCancel, btnRemove, btnBuy, btnStore;
    private ListView mListView;
    private int selectID;
    private String selectName;
    private TextView totalPayment;
    private EditText bookSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        bookSelect = findViewById(R.id.editText1);
        btnCancel = findViewById(R.id.btnCancel);
        btnRemove = findViewById(R.id.btnRemove);
        btnBuy = findViewById(R.id.btnBuy);
        btnStore = findViewById(R.id.btnStore);
        mListView = findViewById(R.id.listDemo);
        totalPayment =  findViewById(R.id.textPrice);

        db = new DataBaseHelperBooks(this);
        enabledFalse();
        populateListView();

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteName(selectID,selectName);
                enabledFalse();
                populateListView();
            }
        });

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double total_payment=0;
                Toast.makeText(ListActivity.this,"Thanks you enjoy!!",Toast.LENGTH_SHORT).show();

                db.deleteTable();
                populateListView();
                btnBuy.setEnabled(false);
                enabledFalse();

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enabledFalse();
            }
        });

        btnStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ListActivity .this,Store.class);
                startActivity(intent);
            }
        });


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String name = adapterView.getItemAtPosition(position).toString();
                Log.d(TAG, "onItemClick: You Clicked on " + name);

                Cursor data = db.getItemID(name); //get the id associated with that name
                int itemID = -1;
                int countRow = data.getCount();

                while(data.moveToNext()){
                    itemID = data.getInt(0);
                }



                if(itemID > -1){
                    selectID=itemID;
                    selectName=name;
                    Log.d(TAG, "onItemClick: The ID is: " + itemID);
                    enabledTrue();
                    bookSelect.setText(name);
                }
                else{
                    toastMessage("No ID associated with that name");
                }

            }
        });

    }


    private void populateListView(){
        Log.d(TAG, "populateListView: Displaying data in the ListView.");


        Cursor data = db.getData();
        Cursor dataTotal = db.getPrice();
        String payment="";
        double a=0.0;
        if(dataTotal.getCount()>0) {
            dataTotal.moveToFirst();
           a = dataTotal.getDouble(dataTotal.getColumnIndex("total"));
        }
        ArrayList<String> listData = new ArrayList<>();

        while(data.moveToNext()){

            //get the value from database in column
            // then add it to the arraylist
            listData.add(data.getString(1));
        }

      //  listData.add(totalAmount+"$");
        //create the list adapter and set the adapter
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        mListView.setAdapter(adapter);
        totalPayment.setText("Current amount: $"+ String.format("%.2f",a));

    }

    public void enabledFalse(){
        bookSelect.setText("");
        bookSelect.setVisibility(View.INVISIBLE);
        btnRemove.setVisibility(View.INVISIBLE);
        btnCancel.setVisibility(View.INVISIBLE);
    }

    public void enabledTrue(){
        btnCancel.setVisibility(View.VISIBLE);
        btnRemove.setVisibility(View.VISIBLE);
        bookSelect.setVisibility(View.VISIBLE);
    }

    private void toastMessage(String message){
        Toast.makeText(this,message,  Toast.LENGTH_LONG).show();;
    }

}
