package com.flowerroad.receiptofwoorydle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by jaeyeon on 2018-01-25.
 */

public class ReceiptTextInsertActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {

        //@Override
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_text_insert);

        Button btn = (Button) findViewById(R.id.finish);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent TextDetectionIntent = new Intent(ReceiptTextInsertActivity.this, ReceiptListActivity.class);
                startActivity(TextDetectionIntent);
            }
        });
    }

}
