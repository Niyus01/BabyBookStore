package com.suyin.books_store;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentDetailsActivity extends AppCompatActivity {

    TextView mId, mamount, mstatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        mId = findViewById(R.id.textId);
        mamount = findViewById(R.id.textAmount);
        mstatus = findViewById(R.id.textStatus);

        Intent intent = getIntent();

        try{
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("PaymentDetails"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showDetails(JSONObject response, String paymentAmount){
        try {
            mId.setText(response.getString("id"));
            mstatus.setText(response.getString("state"));
            mamount.setText(response.getString("$ "+ paymentAmount));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
