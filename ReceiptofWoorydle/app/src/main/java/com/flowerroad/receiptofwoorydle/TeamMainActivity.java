package com.flowerroad.receiptofwoorydle;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

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
    String phone;
    String name;
    String image;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        //액션바 홈버튼 표시
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent != null) {
            // MainActivity로부터 넘어온 데이터를 꺼낸다
            name = intent.getStringExtra("userName");
            image = intent.getStringExtra("userImage");
            email = intent.getStringExtra("userEmail");
            team_id = intent.getStringExtra("teamid");
            user_id = intent.getIntExtra("userid",0);
            phone =intent.getStringExtra("userPhone");
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

        showTeamMemberList();
    }

    public void showTeamMemberList(){
        MariaConnect mariaConnect = new MariaConnect();
        ArrayList<User> userArrayList = new ArrayList<User>();
        userArrayList = mariaConnect.showTeamMember(team_id);

        ExpandableListView elv = (ExpandableListView) findViewById(R.id.elv);
        TeamMemberListAdapter adapter;

        adapter = new TeamMemberListAdapter(this, userArrayList);
        elv.setAdapter(adapter);

        elv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                /*
                if (all_book.get(groupPosition).getListID() == 0) {
                    //내가 판매등록한 책의 세부정보로 넘어가는 버튼
                    Intent intent = new Intent(getApplicationContext(), MypageRegisterActivity.class);

                    intent.putExtra("bookID", all_book.get(groupPosition).getBookId().get(childPosition));
                    intent.putExtra("bookTitle", all_book.get(groupPosition).getTitle().get(childPosition));
                    intent.putExtra("bookAuthor", all_book.get(groupPosition).getAuthor().get(childPosition));
                    intent.putExtra("bookCover", all_book.get(groupPosition).getImgUrl().get(childPosition));
                    intent.putExtra("bookISBN", all_book.get(groupPosition).getIsbn().get(childPosition));
                    intent.putExtra("bookPubdate", all_book.get(groupPosition).getPubdate().get(childPosition));
                    intent.putExtra("bookPublisher", all_book.get(groupPosition).getPublisher().get(childPosition));
                    intent.putExtra("cource", all_book.get(groupPosition).getCourse().get(childPosition));
                    intent.putExtra("professor", all_book.get(groupPosition).getProfessor().get(childPosition));
                    intent.putExtra("sellerPrice", all_book.get(groupPosition).getSellerPrice().get(childPosition));
                    intent.putExtra("comment", all_book.get(groupPosition).getComment().get(childPosition));
                    intent.putExtra("status", all_book.get(groupPosition).getStatus().get(childPosition));
                    intent.putExtra("bookPrice", all_book.get(groupPosition).getPrice().get(childPosition));
                    intent.putExtra("owner", all_book.get(groupPosition).getOwner().get(childPosition));

                    intent.putExtra("username", username);
                    intent.putExtra("userID", userID);
                    intent.putExtra("token", token);

                    startActivity(intent);


                } else if (all_book.get(groupPosition).getListID() == 1) {

                    //세부정보로 넘어가는 버튼
                    Intent intent = new Intent(getApplicationContext(), MypageRequestActivity.class);
                    intent.putExtra("requestId", all_book.get(groupPosition).getRequestId().get(childPosition));
                    intent.putExtra("bookTitle", all_book.get(groupPosition).getTitle().get(childPosition));
                    intent.putExtra("bookAuthor", all_book.get(groupPosition).getAuthor().get(childPosition));
                    intent.putExtra("bookCover", all_book.get(groupPosition).getImgUrl().get(childPosition));
                    intent.putExtra("bookISBN", all_book.get(groupPosition).getIsbn().get(childPosition));
                    intent.putExtra("bookPubdate", all_book.get(groupPosition).getPubdate().get(childPosition));
                    intent.putExtra("bookPublisher", all_book.get(groupPosition).getPublisher().get(childPosition));
                    intent.putExtra("cource", all_book.get(groupPosition).getCourse().get(childPosition));
                    intent.putExtra("professor", all_book.get(groupPosition).getProfessor().get(childPosition));
                    intent.putExtra("sellerPrice", all_book.get(groupPosition).getSellerPrice().get(childPosition));
                    intent.putExtra("comment", all_book.get(groupPosition).getComment().get(childPosition));
                    intent.putExtra("status", all_book.get(groupPosition).getStatus().get(childPosition));
                    intent.putExtra("bookPrice", all_book.get(groupPosition).getPrice().get(childPosition));
                    intent.putExtra("owner", all_book.get(groupPosition).getOwner().get(childPosition));

                    intent.putExtra("username", username);
                    intent.putExtra("userID", userID);
                    intent.putExtra("token", token);

                    startActivity(intent);

                }
                */

                return false;
            }
        });
    }
    protected void redirectInviteActivity() {
        final Intent intent = new Intent(this, TeamMemberInvite.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("userName",name);
        intent.putExtra("userImage",image);
        intent.putExtra("userEmail",email);
        intent.putExtra("userid",user_id);
        intent.putExtra("teamid",team_id);
        intent.putExtra("userPhone",phone);
        startActivity(intent);
        finish();
    }
    public void onClickinvite(View view) {
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(TeamMainActivity.this);
        alert_confirm.setMessage("팀원 초대하기").setCancelable(false).setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UserManagement.requestLogout(new LogoutResponseCallback() {
                            @Override
                            public void onCompleteLogout() {
                                redirectInviteActivity();
                            }
                        });
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        //or switch문을 이용하면 될듯 하다.
        if (id == android.R.id.home) {
            Toast.makeText(this, "홈아이콘 클릭", Toast.LENGTH_SHORT).show();
            final Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            intent.putExtra("userName",name);
            intent.putExtra("userImage",image);
            intent.putExtra("userEmail",email);
            intent.putExtra("userid",user_id);
            intent.putExtra("userPhone",phone);
            startActivity(intent);
            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
