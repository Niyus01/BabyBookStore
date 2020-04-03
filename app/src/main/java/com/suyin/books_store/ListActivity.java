package com.suyin.books_store;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
    private int selectID, selectCount;
    private String selectName, amount;
    private TextView totalPayment_textv, bookCount, toolbar_title, sing_up;
    private EditText bookSelect;
    private Spinner spinner;
    private double totalPayment;
    private ArrayList<ListUser> listData = new ArrayList<>();


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


        Toolbar toolbar =findViewById(R.id.toolbar_main);
        toolbar_title = findViewById(R.id.toolbar_title);
        sing_up = findViewById(R.id.toolbar_text);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar_title.setText("Cart");


        bookSelect = findViewById(R.id.editText1);
        spinner = findViewById(R.id.spinner);
        btnBuy = findViewById(R.id.btnBuy);
        btnStore = findViewById(R.id.btnStore);
        mListView = findViewById(R.id.listDemo);
        totalPayment_textv =  findViewById(R.id.textPrice);
        bookCount = findViewById(R.id.textbookcount);

        db= new DataBaseHelperBooks(this);


        Intent intent = new Intent (this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);

        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(this,R.array.numbers,android.R.layout.simple_spinner_item);
        adapterSpinner.setDropDownViewResource(R.layout.linearlayout_spinner_item);
        spinner.setAdapter(adapterSpinner);
        spinner.setOnItemSelectedListener(this);


        populateListView();


        sing_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListActivity.this, MainActivity.class));
            }
        });


        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(totalPayment==0){
                    Toast.makeText(ListActivity.this,"Add new books to the car!!",Toast.LENGTH_SHORT).show();
                }
                else {
                    enabledFalse();
                    processPayment();
                }
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
               // String name = adapterView.getItemAtPosition(position).toString();
                String name = listData.get(position).getTitle();
                int count = listData.get(position).getCount();
                view.setSelected(true);

                Log.d(TAG, "onItemClick: You Clicked on " + name);

                Cursor data = db.getItemID(name); //get the id associated with that name
                int itemID = -1;
                int countRow = data.getCount();

                while(data.moveToNext()){
                    itemID = data.getInt(0);
                }

                if(itemID > -1){
                    selectID=itemID;
                    selectCount = count;
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

       if(position ==1){
           db.deleteName(selectID,selectName);
           listData.clear();
           enabledFalse();
           populateListView();}

        else if(position>1){
            int count =position-1;

            db.updateCount(count, selectName);
            listData.clear();
            enabledFalse();
            populateListView();
        }

    }


    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }


    private void populateListView(){
        Log.d(TAG, "populateListView: Displaying data in the ListView.");

        Cursor data = db.getData();
        Cursor dataTotal = db.getPayment();

        if(dataTotal.getCount()>0) {
            dataTotal.moveToFirst();
            totalPayment = dataTotal.getDouble(dataTotal.getColumnIndex("total"));

        }

        totalPayment_textv.setText("Current amount: $ "+ String.format("%.2f",totalPayment));

        while(data.moveToNext()){
            //get the value from database in column then add it to the arraylist

            ListUser list =  new ListUser(data.getString(1),data.getInt(3), data.getDouble(4) );
            listData.add(list);
        }

        //create the list adapter and set the adapter
        ListUserAdapter adapter = new ListUserAdapter(this,  listData);
        mListView.setAdapter(adapter);
        db.close();

    }


    private void processPayment(){
        PayPalPayment payPalPayment = new PayPalPayment (new BigDecimal(totalPayment), "USD",
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
                        startActivity(new Intent(ListActivity.this, myaccountActivity.class));
                        String paymentDetails = confirmation.toJSONObject().toString(4);

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
        spinner.setSelection(0);
        spinner.setVisibility(View.VISIBLE);
        bookSelect.setVisibility(View.VISIBLE);
        bookCount.setVisibility(View.VISIBLE);
    }

    private void toastMessage(String message){
        Toast.makeText(this,message,  Toast.LENGTH_LONG).show();;
    }

}
