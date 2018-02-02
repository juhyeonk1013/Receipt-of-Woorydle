package com.flowerroad.receiptofwoorydle;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by Juhyeon on 2018-02-01.
 */

/** 검색목록을 작성하기 위한 어댑터 클래스 */
public class TeamMemberInviteListAdapter extends BaseAdapter{
    private Context mContext;
    private ArrayList<User> inviteLists; //검색 목록 저장할 어레이 리스트

    //생성자에 변수 설정
    public TeamMemberInviteListAdapter(Context context, ArrayList<User> inviteLists) {
        this.mContext = context;
        this.inviteLists = inviteLists;
    }

    //검색된 사람의 개수
    @Override
    public int getCount() { return inviteLists.size(); }

    //특정 목록
    @Override
    public Object getItem(int position) { return inviteLists.get(position);}

    @Override
    public long getItemId(int position) { return 0; }

    //초대할 사람을 보여주는 View 함수
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TeamMemberInviteListView inviteListView;
        if(convertView == null){ //convertView 가  NULL 이라면 CharListView 로 설정
            inviteListView = new TeamMemberInviteListView(mContext, inviteLists.get(position));
        } else {
            inviteListView = (TeamMemberInviteListView) convertView;
        }

        inviteListView.setText(inviteLists.get(position));

        return inviteListView; //리스트뷰 반환
    }

}