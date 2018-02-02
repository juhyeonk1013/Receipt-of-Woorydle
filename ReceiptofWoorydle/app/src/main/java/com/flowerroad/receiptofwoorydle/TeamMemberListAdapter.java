package com.flowerroad.receiptofwoorydle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by juhyun on 2018-02-01.
 */

public class TeamMemberListAdapter extends BaseExpandableListAdapter {

    private ArrayList<User> UserList;
    private LayoutInflater inflater;
    Bitmap bitmap;

    //class Constructor
    public TeamMemberListAdapter(Context mContext, ArrayList<User> UserList) {
        this.UserList = UserList;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    /** Getter and Setter 및 기본 메소드들  */
    @Override
    public int getGroupCount() {
        return UserList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return UserList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return UserList;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    //확장리스트의 부모를 보여주는 메소드
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_team_member_list, null);
        }
        //확장리스트의 명명을 한다.
        String listname = UserList.get(groupPosition).getUserName();
        final String listimage = UserList.get(groupPosition).getUserimage();

        ImageView imageView = (ImageView) convertView.findViewById(R.id.listimage);
        TextView textView = (TextView) convertView.findViewById(R.id.listname);
        textView.setText(" "+listname);
        textView.setTextSize(20);

        Thread mThread=new Thread(){
            @Override
            public void run(){

                try{
                    URL url = new URL(listimage);

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
            imageView.setImageBitmap(bitmap);
        }catch(InterruptedException e){

        }

        return convertView;
    }

    //자식을 보여주는 메소드
    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_team_member_list_child, null); //listItem.xml 파일에서 발췌
        }

        Button charBtn = (Button) convertView.findViewById(R.id.chatBtn);
        Button callBtn = (Button) convertView.findViewById(R.id.callBtn);
        Button emailBtn = (Button) convertView.findViewById(R.id.emailBtn);

        charBtn.setText(UserList.get(groupPosition).getUserName()+"님과 채팅");
        callBtn.setText(UserList.get(groupPosition).getUserName()+"님과 전화");
        emailBtn.setText(UserList.get(groupPosition).getUserName()+"님에게 메일");

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
