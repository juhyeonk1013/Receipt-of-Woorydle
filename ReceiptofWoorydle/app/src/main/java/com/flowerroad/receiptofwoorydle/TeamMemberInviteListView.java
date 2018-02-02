package com.flowerroad.receiptofwoorydle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Juhyeon on 2018-02-01.
 */

public class TeamMemberInviteListView extends LinearLayout {
    private TextView mName;
    ImageView mimage;
    Bitmap bitmap;

    public TeamMemberInviteListView(Context context, User user) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.activity_team_member_list,this,true); //채팅목록 XML 파일 지정

        mName = (TextView)findViewById(R.id.listname); //상대방 이름 텍스트뷰
        mimage = (ImageView) findViewById(R.id.listimage);


    }
    public void setText(final User user) { //텍스트 설정
        mName.setText(user.getUserName());
        mimage.setImageBitmap(user.getBitmap());
        
    }
}
