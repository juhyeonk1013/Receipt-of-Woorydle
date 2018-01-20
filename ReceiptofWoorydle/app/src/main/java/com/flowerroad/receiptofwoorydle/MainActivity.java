package com.flowerroad.receiptofwoorydle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by gywls on 2018-01-20.
 */

public class MainActivity extends Activity {
    public static String name;
    public static String image;
    public TextView userName;
    public ImageView userImage;
    public Button btn;
    Bitmap bitmap;
    private BackPressCloseHandler backPressCloseHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        Intent intent = getIntent();
        if (intent != null) {
            // LoginActivity로부터 넘어온 데이터를 꺼낸다
            name = intent.getStringExtra("userName");
            image = intent.getStringExtra("userImage");
        }
        ImageButton ibtn = (ImageButton) findViewById(R.id.logout);
        userImage = (ImageView) findViewById(R.id.user_image);
        userName = (TextView) findViewById(R.id.user_name);

        userName.setText(name+"님, 반갑습니다!");
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
                Intent intent = new Intent(MainActivity.this, TextDetection.class);
                startActivity(intent);
            }
        });

        backPressCloseHandler = new BackPressCloseHandler(this);
    }

    public void onClickLogout(View view) {
        UserManagement.requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {
                redirectLoginActivity();
            }
        });
    }
    protected void redirectLoginActivity() {
        final Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }
    @Override public void onBackPressed() {
        super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }
}