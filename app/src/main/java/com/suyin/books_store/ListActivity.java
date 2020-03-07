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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;


import org.json.JSONException;

import java.math.BigDecimal;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG="ListActivity";
    private DataBaseHelperBooks db;
    private Button  btnBuy, btnStore;
    private ListView mListView;
    private int selectID;
    private String selectName, amount;
    private TextView totalPayment, bookCount;
    private EditText bookSelect;
    private Spinner spinner;
    private String[] booksbytitle = { "0","1","2","3" };

    public static final String PAYPAL_CLIENT_ID = "AYS1Ahsj-cjU2JKTqfLHFCQ-OyUkf7aBge7La3gkcRK_qCZt_M_8tbb41yb7IQg0QGwNQaEUfKDAkhUe";
    private static final int PAYPAL_REQUEST_CODE = 7171;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(PAYPAL_CLIENT_ID);

    @Override
    protected  void onDestroy()
    {     stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        bookSelect = findViewById(R.id.editText1);
        spinner = findViewById(R.id.spinner);
        btnBuy = findViewById(R.id.btnBuy);
        btnStore = findViewById(R.id.btnStore);
        mListView = findViewById(R.id.listDemo);
        totalPayment =  findViewById(R.id.textPrice);
        bookCount = findViewById(R.id.textbookcount);


        Intent intent = new Intent (this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.numbers,android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        db= new DataBaseHelperBooks(this);
        enabledFalse();
        populateListView();





        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double total_payment=0;
                Toast.makeText(ListActivity.this,"Thanks you enjoy!!",Toast.LENGTH_SHORT).show();

                db.deleteTable();

               // btnBuy.setEnabled(false);
               // enabledFalse();
            //    processPayment();
                populateListView();


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


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        parent.getContext();

       /* int book=0;
        switch(item){
            case "0":
                book=0;
            case "1":
                book=1;
            case "2":
                book=2;
            case "3":
                book=3;*/

        if(position>0){
            Toast.makeText(this, "book: "+position,Toast.LENGTH_LONG).show();
        db.updateCount(position, selectName);
        enabledFalse();}


        }



    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    private void populateListView(){
        Log.d(TAG, "populateListView: Displaying data in the ListView.");


        Cursor data = db.getData();
        Cursor dataTotal = db.getPrice();

        double payment=0.0;
        if(dataTotal.getCount()>0) {
            dataTotal.moveToFirst();
           payment = dataTotal.getDouble(dataTotal.getColumnIndex("total"));
           amount = String.valueOf(payment);
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
        totalPayment.setText("Current amount: $"+ String.format("%.2f",payment));
        db.close();

    }

    private void processPayment(){
        //amount = totalPayment.getText().toString();
        PayPalPayment payPalPayment = new PayPalPayment (new BigDecimal(String.valueOf(amount)), "USD",
                "Pay your balance", PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent (this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYPAL_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null) {
                    try {
                        db.deleteTable();
                        populateListView();
                        //Intent intent = new Intent(this, PaymentDetailsActivity.class);
                        String paymentDetails = confirmation.toJSONObject().toString(4);
                       // Toast.makeText(this, "Payment Detail: " +  paymentDetails+ " Payment Amount: "+String.format("%.2f",amount),Toast.LENGTH_LONG).show();
                       // intent.putExtra("Payment Detail", paymentDetails);
                        //intent.putExtra("Payment Amount", amount);
                        //startActivity(intent);
                       // db.deleteTable();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }

        else if(resultCode==RESULT_CANCELED){
            Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();}

        else if(resultCode== PaymentActivity.RESULT_EXTRAS_INVALID){
            Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show();

        }
    }

    public void enabledFalse(){
        bookSelect.setText("");
        bookSelect.setVisibility(View.INVISIBLE);
        spinner.setVisibility(View.INVISIBLE);
        bookCount.setVisibility(View.INVISIBLE);



    }

    public void enabledTrue(){
        spinner.setVisibility(View.VISIBLE);
        bookSelect.setVisibility(View.VISIBLE);
        bookCount.setVisibility(View.VISIBLE);
    }

    private void toastMessage(String message){
        Toast.makeText(this,message,  Toast.LENGTH_LONG).show();;
    }

}
