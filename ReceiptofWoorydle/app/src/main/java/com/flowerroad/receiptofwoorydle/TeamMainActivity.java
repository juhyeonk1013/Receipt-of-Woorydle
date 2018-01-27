package com.flowerroad.receiptofwoorydle;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by juhyun on 2018-01-26.
 */

public class TeamMainActivity extends AppCompatActivity {
    private String team_id;
    private int user_id;
    private TextView intro;
    private String team_name="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        //btn=(Button)findViewById(R.id.add_team);

        Intent intent = getIntent();
        if (intent != null) {
            // MainActivity로부터 넘어온 데이터를 꺼낸다
            //name = intent.getStringExtra("userName");
            team_id = intent.getStringExtra("teamid");
            user_id = intent.getIntExtra("userid",0);
        }

        MariaConnect mariaConnect = new MariaConnect();
        team_name = mariaConnect.getTeamName(team_id);

        intro = (TextView) findViewById(R.id.introduce);
        intro.setText(team_name+"팀의 메인화면입니다.");

        Button receiptBtn = (Button)findViewById(R.id.receipt_management);
        receiptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeamMainActivity.this, ReceiptListActivity.class);
                intent.putExtra("teamid", team_id);
                intent.putExtra("teamname", team_name);
                startActivity(intent);
            }
        });

        Button memberBtn = (Button)findViewById(R.id.member_management);
        /*receiptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeamMainActivity.this, MemberListActivity.class);
                startActivity(intent);
            }
        });*/
    }
}

