package com.flowerroad.receiptofwoorydle;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ReceiptListActivity extends AppCompatActivity {
    ArrayList<String> middleRow = new ArrayList<String>();
    ArrayList<String> lastRow = new ArrayList<String>();
    int total = 25000;
    //@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_list);

        Button btn = (Button) findViewById(R.id.receipt_detect);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder selectInsertBuilder = new AlertDialog.Builder(ReceiptListActivity.this);

                final String str[] = {"영수증 인식","직접 입력"};
                selectInsertBuilder.setTitle("등록 방법을 선택하세요")
                        .setNegativeButton("Cancel", null)
                        .setItems(str, // 리스트 목록에 사용할 배열
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch(which) {
                                            case 0:
                                                Intent intent = new Intent(ReceiptListActivity.this, TextDetection.class);
                                                startActivity(intent);
                                                break;
                                            case 1:
                                                insertReceiptContent();
                                                break;
                                        }
                                    }
                                }); // 클릭 리스너
                AlertDialog dialog = selectInsertBuilder.create();    // 알림창 객체 생성
                dialog.show();    // 알림창 띄우기
            }
        });
        TextView teamName = (TextView) findViewById(R.id.team_name);
        teamName.setText("Flower Road");
        viewReceiptList();
    }

    public void viewReceiptList(){
        TableLayout receiptList = (TableLayout) findViewById(R.id.receipt_list);
        setRows();
        TableRow tr = new TableRow(this);
        tr.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT
        ));
        for(int i = 0; i < 5;i ++) {
            TextView b = new TextView(this);
            b.setText(middleRow.get(i));
            b.setTextSize(10);
            b.setTextColor(Color.parseColor("#000000"));
            b.setBackgroundColor(Color.parseColor("#F6F6F6"));

            b.setGravity(Gravity.CENTER);
            b.setLayoutParams(new LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT
            ));
            tr.addView(b);
            ViewGroup.MarginLayoutParams margin =
                    new ViewGroup.MarginLayoutParams(b.getLayoutParams());
            margin.setMargins(1, 1, 1, 1);
            //b.setLayoutParams(new ConstraintLayout.LayoutParams(margin));
        }
        receiptList.addView(tr, new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT
        ));
        TableRow tr2 = new TableRow(this);
        tr2.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT
        ));
        for(int i = 0; i < 5;i ++) {
            TextView b = new TextView(this);
            b.setText(lastRow.get(i));
            b.setTextSize(10);
            b.setTextColor(Color.parseColor("#000000"));
            b.setBackgroundColor(Color.parseColor("#F6F6F6"));

            b.setGravity(Gravity.CENTER);
            b.setLayoutParams(new LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT
            ));
            tr2.addView(b);
            ViewGroup.MarginLayoutParams margin =
                    new ViewGroup.MarginLayoutParams(b.getLayoutParams());
            margin.setMargins(1, 1, 1, 1);
            //b.setLayoutParams(new ConstraintLayout.LayoutParams(margin));
        }
        receiptList.addView(tr2, new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT
        ));
    }
    void setRows(){
        middleRow.add("2018/01/23");
        middleRow.add("동대닭한마리");
        middleRow.add("닭한마리");
        middleRow.add("25,000");
        middleRow.add(" ");

        lastRow.add("합계");
        lastRow.add(" ");
        lastRow.add(" ");
        lastRow.add(total+"원");
        lastRow.add(" ");
    }

    private void insertReceiptContent(){

    }
}