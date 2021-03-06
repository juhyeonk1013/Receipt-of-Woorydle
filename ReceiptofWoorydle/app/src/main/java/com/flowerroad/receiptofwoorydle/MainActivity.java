package com.flowerroad.receiptofwoorydle;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

import org.apache.commons.lang3.RandomStringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * Created by gywls on 2018-01-20.
 */


public class MainActivity extends AppCompatActivity implements AddTeamDialog.AddTeamDialogListener {
    public String teamName = "Flower Load";
    public String name;
    public String image;
    public String email;
    public String phone;
    public int id;
    public TextView userPhone;
    public TextView userName;
    public ImageView userImage;
    public TextView userEmail;
    public Button btn2; //팀 추가 버튼
    Bitmap bitmap;
    SwipeRefreshLayout swipeRefresh;

    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        if (intent != null) {
            // LoginActivity로부터 넘어온 데이터를 꺼낸다
            name = intent.getStringExtra("userName");
            image = intent.getStringExtra("userImage");
            email = intent.getStringExtra("userEmail");
            id = intent.getIntExtra("userid",0);
            phone = intent.getStringExtra("userPhone");
        }

        userImage = (ImageView) findViewById(R.id.user_image);
        userName = (TextView) findViewById(R.id.user_name);
        userEmail = (TextView) findViewById(R.id.user_email);
        userPhone = (TextView) findViewById(R.id.user_phone);

        userName.setText(name+"님, 반갑습니다!");
        userEmail.setText(email);
        userPhone.setText(phone.substring(0,3)+"-"+phone.substring(3,7)+"-"+phone.substring(7));

        Thread mThread=new Thread(){
            @Override
            public void run(){
                try{
                    URL url = new URL(image);

                    //웹에서 이미지를 가져온 뒤 이미지 뷰에 지정할 Bitmap 생성
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setDoInput(true);
                    conn.connect();

                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                } catch(IOException ex){
                }
            }
        };

        mThread.start();    //웹에서 이미지 가져오는 작업 시행
        try{
            //메인 스레드는 작업 스레드가 이미지 작업을 가져올 때까지
            //대기해야 하므로 작업 스레드의 join() 메소드를 호출해서
            //메인 스레드가 작업 스레드가 종료될 때 까지 기다리도록 한다.
            mThread.join();
            //작업 스레드에서 이미지 불러오는 작업을 완료 했기 때문에
            //메인스레드에서 이미지 뷰에 이미지 지정
            userImage.setImageBitmap(bitmap);
        }catch(InterruptedException e){

        }

        viewTeamList();
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewTeamList();
                swipeRefresh.setRefreshing(false);
            }
        });
        backPressCloseHandler = new BackPressCloseHandler(this);
    }

    public void openDialog() {
        AddTeamDialog addTeamDialog=new AddTeamDialog();
        addTeamDialog.show(getSupportFragmentManager(), "add team dialog");
    }

    @Override
    public void applyTexts(String teamname) {
        teamName = teamname;
        //textViewTeamname.setText(teamname);

        String team_id="";
        team_id = RandomStringUtils.randomAlphabetic(5)+RandomStringUtils.randomNumeric(5).toString();
        MariaConnect mariaConnect = new MariaConnect();
        mariaConnect.makeTeam(id, team_id, teamname);

        final Intent intent = new Intent(this, TeamMainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("userName",name);
        intent.putExtra("userImage",image);
        intent.putExtra("userEmail",email);
        intent.putExtra("teamid",team_id);
        intent.putExtra("userid",id);
        intent.putExtra("userPhone",phone);
        startActivity(intent);
        finish();
    }

    public void onClickLogout(View view) {
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(MainActivity.this);
        alert_confirm.setMessage("로그아웃 하시겠습니까?").setCancelable(false).setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UserManagement.requestLogout(new LogoutResponseCallback() {
                            @Override
                            public void onCompleteLogout() {
                                redirectLoginActivity();
                            }
                        });
                    }
                }).setNegativeButton("취소",
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
    protected void redirectLoginActivity() {
        final Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }

    public void viewTeamList(){
        MariaConnect mariaConnect = new MariaConnect();
        int teamNum = mariaConnect.getTeamNum(id); //마리아 디비에서 팀개수
        ArrayList<Team> team = new ArrayList<Team>();
        team = mariaConnect.showTeam(id); //내가 가입된 팀
        int[] colorSrc = {Color.parseColor("#FAED7D"),Color.parseColor("#B2EBF4"),Color.parseColor("#FFB2D9"),Color.parseColor("#CEF279"), Color.parseColor("#FFA7A7")};

        TableLayout teamList = (TableLayout) findViewById(R.id.team_list);
        teamList.removeAllViews();
        TableRow tr;
        tr = new TableRow(this);
        tr.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT
        ));

        for(int i = 0; i < teamNum;i++) {
            Button ibtn = new Button(this);

            String team_id = team.get(i).getTeamid();
            String leader_name = mariaConnect.getLeaderName(team.get(i).getTeamid());
            String team_name = mariaConnect.getTeamName(team.get(i).getTeamid());
            team.get(i).setTeamName(team_name);

            ibtn.setBackgroundTintList(ColorStateList.valueOf(colorSrc[i%5]));
            ibtn.setText(team_name+"\n\n@"+leader_name+"님의 팀\n");
            ibtn.setTextColor(Color.BLACK);
            ibtn.setTextSize(20);
            ibtn.setWidth(0);

            tr.addView(ibtn);


            if(i%2 ==1){
                teamList.addView(tr, new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT
                ));

                tr = new TableRow(this);
                tr.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT
                ));

            }

            final String finalTeam_id = team_id;
            ibtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, TeamMainActivity.class);
                    intent.putExtra("teamid", finalTeam_id);
                    intent.putExtra("userid",id);
                    intent.putExtra("userName",name);
                    intent.putExtra("userImage",image);
                    intent.putExtra("userEmail",email);
                    intent.putExtra("userPhone",phone);
                    startActivity(intent);
                    finish();
                }
            });
        }

        btn2 = new Button(this);
        btn2.setText("+");
        btn2.setTextColor(Color.parseColor("#D5D5D5"));
        btn2.setTextSize(30);
        btn2.setTextColor(Color.parseColor("#8C8C8C"));
        btn2.setWidth(0);

        tr.addView(btn2);
        teamList.addView(tr);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
    }

}