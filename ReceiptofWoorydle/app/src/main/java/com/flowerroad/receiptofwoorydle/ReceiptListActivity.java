package com.flowerroad.receiptofwoorydle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

public class ReceiptListActivity extends Activity {
    //@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_list);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        Button btn = (Button) findViewById(R.id.receipt_detect);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReceiptListActivity.this, TextDetection.class);
                startActivity(intent);
            }
        });
        TextView teamName = (TextView) findViewById(R.id.team_name);
        teamName.setText("Flower Road");
        viewReceipList();
    }

    public void viewReceipList(){
        TableLayout receiptList = (TableLayout) findViewById(R.id.receipt_list);
        TableRow tr = new TableRow(this);
        tr.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT
        ));

        for(int i = 0; i < 5;i ++) {
            TextView b = new TextView(this);
            b.setText("2018.01.17");
            /*android:layout_margin="1dp"
                android:gravity="center"
                android:background="#00000000"
                android:text=" "
                android:textColor="#000000"
                android:textSize="10sp"*/
            b.setTextSize(10);
            b.setTextColor("#000000");
            b.setLayoutParams(new LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT
            ));
            tr.addView(b);
        }
        receiptList.addView(tr, new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT
        ));
    }
}