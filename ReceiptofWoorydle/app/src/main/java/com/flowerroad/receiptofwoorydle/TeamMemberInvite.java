package com.flowerroad.receiptofwoorydle;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
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

import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

import java.util.ArrayList;

/**
 * Created by Juhyeon on 2018-02-01.
 */

public class TeamMemberInvite extends AppCompatActivity {
    private String team_id;
    ListView listView;
    EditText editText;
    MariaConnect mariaConnect;
    TeamMemberInviteListAdapter teamMemberInviteListAdapter;
    private ArrayList<User> inviteList = new  ArrayList<User>();
    String name;
    String email;
    String image;
    int user_id;
    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_member_invite);

        //액션바 홈버튼 표시
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent != null) {
            // 이전 Activity로부터 넘어온 데이터를 꺼낸다
            name = intent.getStringExtra("userName");
            image = intent.getStringExtra("userImage");
            email = intent.getStringExtra("userEmail");
            team_id = intent.getStringExtra("teamid");
            phone = intent.getStringExtra("userPhone");
            user_id = intent.getIntExtra("userid",0);
        }

        mariaConnect = new MariaConnect();

        editText = (EditText) findViewById(R.id.searchName);
        listView = (ListView) findViewById(R.id.invite_list);

        listView.setEmptyView(findViewById(R.id.empty));
        teamMemberInviteListAdapter= new TeamMemberInviteListAdapter(this, inviteList);
        listView.setAdapter(teamMemberInviteListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) { //목록 하나를 클릭하면

                AlertDialog.Builder alert_confirm = new AlertDialog.Builder(TeamMemberInvite.this);
                alert_confirm.setMessage(inviteList.get(position).getUserName()+"님을 초대하시겠습니까?").setCancelable(false).setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(mariaConnect.verifyTeamMember(inviteList.get(position).getUserID(),team_id) == true){
                                    //팀원이 이미 가입되어있음.
                                    Toast.makeText(getApplicationContext(), inviteList.get(position).getUserName()+"님은 이미 가입되어 있습니다.",Toast.LENGTH_LONG).show();

                                }else{
                                    //팀원 초대
                                    mariaConnect.addTeamMember(inviteList.get(position).getUserID(), team_id);
                                    Toast.makeText(getApplicationContext(), inviteList.get(position).getUserName()+"님을 초대합니다.",Toast.LENGTH_LONG).show();
                                }
                            }
                        }).setNegativeButton("CANCEL",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 'No'
                                return;
                            }
                        });
                AlertDialog alert = alert_confirm.create();
                alert.show();
            }
        });

    }

    public void inviteBtn(View view){
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        //or switch문을 이용하면 될듯 하다.
        if (id == android.R.id.home) {
            Toast.makeText(this, "팀홈버튼 클릭", Toast.LENGTH_SHORT).show();
            final Intent intent = new Intent(this, TeamMainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            intent.putExtra("userName",name);
            intent.putExtra("userImage",image);
            intent.putExtra("userEmail",email);
            intent.putExtra("userid",user_id);
            intent.putExtra("teamid",team_id);
            intent.putExtra("userPhone",phone);
            startActivity(intent);
            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
