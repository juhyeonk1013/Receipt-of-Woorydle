package com.flowerroad.receiptofwoorydle;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Juhyeon on 2016-11-26.
 */

public class TeamMemberInviteListView extends LinearLayout {
    private TextView mName;

    public TeamMemberInviteListView(Context context, User user) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.activity_team_member_list,this,true); //채팅목록 XML 파일 지정

        mName = (TextView)findViewById(R.id.listname); //상대방 이름 텍스트뷰
    }
    public void setText(String data) { //텍스트 설정
        mName.setText(data);
    }
}
