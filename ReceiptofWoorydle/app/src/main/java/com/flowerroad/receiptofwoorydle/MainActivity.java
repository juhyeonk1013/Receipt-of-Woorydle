package com.flowerroad.receiptofwoorydle;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.Toolbar;

import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by gywls on 2018-01-20.
 */


public class MainActivity extends AppCompatActivity implements AddTeamDialog.AddTeamDialogListener {
    private TextView textViewTeamname;

    public String teamName = "Flower Load";
    public String name;
    public String image;
    public String email;
    public TextView userName;
    public ImageView userImage;
    public TextView userEmail;
    public Button btn;  //영수증 분석하기 버튼
    public Button btn2; //팀 추가 버튼
    Bitmap bitmap;

    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //btn=(Button)findViewById(R.id.add_team);

        Intent intent = getIntent();
        if (intent != null) {
            // LoginActivity로부터 넘어온 데이터를 꺼낸다
            name = intent.getStringExtra("userName");
            image = intent.getStringExtra("userImage");
            email = intent.getStringExtra("userEmail");
        }

        userImage = (ImageView) findViewById(R.id.user_image);
        userName = (TextView) findViewById(R.id.user_name);
        userEmail = (TextView) findViewById(R.id.user_email);

        userName.setText(name+"님, 반갑습니다!");
        userEmail.setText(email);

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

        btn = (Button) findViewById(R.id.receipt_detect);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ReceiptListActivity.class);
                startActivity(intent); 
            }
        });

        viewTeamList();
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
        int teamNum = 10;
        TableLayout teamList = (TableLayout) findViewById(R.id.team_list);
        TableRow tr;
        tr = new TableRow(this);
        tr.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.FILL_PARENT
        ));

        for(int i = 0; i < teamNum;i ++) {
            Button ibtn = new Button(this);
            String str=String.format("drawable/teamcard"+(i%4+1));
            int imageResource = getResources().getIdentifier(str, null, getPackageName());

            BitmapDrawable drawable = (BitmapDrawable)getApplicationContext().getResources().getDrawable(imageResource);
            Bitmap bitmap = drawable.getBitmap();
            bitmap = Bitmap.createScaledBitmap(bitmap, 140*5, 149*5, true);
            BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);

            ibtn.setBackground(bitmapDrawable);
            ibtn.setText(teamName);
            ibtn.setTextColor(Color.BLACK);
            ibtn.setTextSize(10);

            tr.addView(ibtn);
            if(i%2 ==1){
                teamList.addView(tr, new TableRow.LayoutParams(
                        TableRow.LayoutParams.FILL_PARENT,
                        TableRow.LayoutParams.FILL_PARENT
                ));

                tr = new TableRow(this);
                tr.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.FILL_PARENT,
                        TableRow.LayoutParams.FILL_PARENT
                ));
            }
            ibtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, ReceiptListActivity.class);
                    startActivity(intent);
                }
            });
        }

        String str2=String.format("drawable/dot");
        int imageResource2 = getResources().getIdentifier(str2, null, getPackageName());

        BitmapDrawable drawable2 = (BitmapDrawable)getApplicationContext().getResources().getDrawable(imageResource2);
        Bitmap bitmap2 = drawable2.getBitmap();
        bitmap2 = Bitmap.createScaledBitmap(bitmap2, 140*5, 140*5, true);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap2);

        btn2 = new Button(this);
        btn2.setBackground(bitmapDrawable);
        //btn2.setShadowLayer(bitmapDrawable);
        btn2.setText("+");
        btn2.setTextColor(Color.parseColor("#BCBCBC"));
        btn2.setTextSize(30);

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