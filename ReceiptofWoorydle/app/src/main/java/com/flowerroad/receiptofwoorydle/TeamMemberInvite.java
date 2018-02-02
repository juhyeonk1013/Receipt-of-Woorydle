package com.flowerroad.receiptofwoorydle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Juhyeon on 2018-02-01.
 */

public class TeamMemberInvite extends AppCompatActivity {
    private String team_id;
    ListView listView;
    EditText editText;
    Button button;
    MariaConnect mariaConnect;
    TeamMemberInviteListAdapter teamMemberInviteListAdapter;
    private ArrayList<User> inviteList = new  ArrayList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_member_invite);

        Intent intent = getIntent();
        if (intent != null) {
            // 이전 Activity로부터 넘어온 데이터를 꺼낸다
            team_id = intent.getStringExtra("team_id");
        }

        mariaConnect = new MariaConnect();

        editText = (EditText) findViewById(R.id.searchName);
        listView = (ListView) findViewById(R.id.invite_list);

        listView.setEmptyView(findViewById(R.id.empty));
        teamMemberInviteListAdapter= new TeamMemberInviteListAdapter(this, inviteList);
        listView.setAdapter(teamMemberInviteListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) { //목록 하나를 클릭하면
                mariaConnect.addTeamMember(inviteList.get(position).getUserID(), team_id);
                Log.d("어머나"," "+inviteList.get(position).getUserID()+" "+team_id);
                Toast.makeText(getApplicationContext(), inviteList.get(position).getUserName()+"님을 초대합니다.",Toast.LENGTH_LONG).show();
            }
        });

    }
    public void inviteBtn(View view){
        String string = editText.getText().toString();
        inviteList.clear();

        if(!validate())     // 검색어를 입력하지 않고 검색 버튼을 누른 경우
            Toast.makeText(getBaseContext(), "Enter some data!", Toast.LENGTH_LONG).show();
        else {
            inviteList = mariaConnect.selectTeamMember(editText.getText().toString());
            teamMemberInviteListAdapter= new TeamMemberInviteListAdapter(this, inviteList);
            listView.setAdapter(teamMemberInviteListAdapter);
        }

    }
    private boolean validate(){
        if(editText.getText().toString().trim().equals(""))
            return false;
        else
            return true;
    }
}
