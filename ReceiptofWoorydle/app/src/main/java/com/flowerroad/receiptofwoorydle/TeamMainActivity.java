package com.flowerroad.receiptofwoorydle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.netty.channel.MaxBytesRecvByteBufAllocator;

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

        TabHost tabHost = (TabHost) findViewById(R.id.tab);
        tabHost.setup();

        // 첫 번째 Tab. (탭 표시 텍스트:"HOME"), (페이지 뷰:"home")
        TabHost.TabSpec ts1 = tabHost.newTabSpec("Tab Spec 1") ;
        ts1.setContent(R.id.home);
        ts1.setIndicator("HOME");
        tabHost.addTab(ts1)  ;

        // 두 번째 Tab. (탭 표시 텍스트:"MEMBER"), (페이지 뷰:"member")
        TabHost.TabSpec ts2 = tabHost.newTabSpec("Tab Spec 2") ;
        ts2.setContent(R.id.member) ;
        ts2.setIndicator("MEMBER") ;
        tabHost.addTab(ts2) ;

        // 세 번째 Tab. (탭 표시 텍스트:"RECEITP"), (페이지 뷰:"receipt")
        TabHost.TabSpec ts3 = tabHost.newTabSpec("Tab Spec 3") ;
        ts3.setContent(R.id.receipt) ;
        ts3.setIndicator("RECEIPT") ;
        tabHost.addTab(ts3) ;

        ArrayList<User> userArrayList = new ArrayList<User>();
        userArrayList = mariaConnect.showTeamMember(team_id);

        ArrayAdapter<User> adapter;
        adapter = new ArrayAdapter<User>(this, android.R.layout.simple_expandable_list_item_1, userArrayList);

        ListView list = (ListView) findViewById(R.id.memberList);
        list.setAdapter(adapter);

        list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        list.setDivider(new ColorDrawable(Color.WHITE));
        list.setDividerHeight(2);
    }


}
