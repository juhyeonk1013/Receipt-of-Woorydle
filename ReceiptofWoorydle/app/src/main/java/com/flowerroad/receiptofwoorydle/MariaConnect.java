package com.flowerroad.receiptofwoorydle;

import android.util.Log;

import java.sql.Array;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.*;

/**
 * Created by juhyun on 2018-01-24.
 */

public class MariaConnect {
    private static Connection Conn = null;

    public void MariaConnect(){

    }
    //초기 연결 메소드
    public static void Connect(){
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Conn = DriverManager.getConnection("jdbc:mariadb://49.236.146.44/row", "root", "rhcqkd14");
            if(Conn == null) {
                throw new Exception("데이터베이스 연결 실패\n");
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //로그인시 회원정보가 DB에 저장되어 있는지 확인하는 메소드
    public static boolean userExist(int sessionid){
        Connect();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            String mySQL = "select id, name, email from user where id=\'"+sessionid +"\';";
            stmt = Conn.createStatement();

            rs = stmt.executeQuery(mySQL);

            stmt.close();
            Conn.close(); //사용한뒤 close
            if(rs.next()) { //한 행씩 읽어들임.
                return true;

                //System.out.print(rs.getString("id")+" ");
                //System.out.print(rs.getString("name")+" ");
                //System.out.println(rs.getString("email"));
            }else{
                return false;
            }

        } catch (Exception ex) {
            System.out.println("Exception" + ex);
        }
        return false;
    }
    //첫 가입시 DB에 저장
    public static void signUpDB(int user_id, String user_name, String user_email){
        Connect();
        Statement stmt = null;
        int rs = -1;

        try {
            String mySQL = "insert into user(id, name, email) values("+user_id+",\'"+user_name+"\',\'"+user_email+"\');";
            stmt = Conn.createStatement();
            //삽입이나 수정시에는 executeUpdate 함수, rs는 int형을 사용하여야 하고
            //조회할때에는 executeQuery함수, rs는 ResultSet형을 사용하여야 한다.
            rs = stmt.executeUpdate(mySQL);
            if(rs < 0){
                //실패
            }
            stmt.close();
            Conn.close(); //사용한뒤 close
        } catch (Exception ex) {
            System.out.println("Exception" + ex);
        }
    }
    //팀을 만들면 DB에 등록하는 메소드
    public static void makeTeam(int user_id, String team_id, String team_name, String leader_name){ //만든 팀을 데이터베이스에 등록하는 함수
        Connect();
        Statement stmt = null;
        int rs = -1;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(cal.getTime());

        try {
            //team table에 저장
            String mySQL = "insert into team(teamid, teamname, leader, date) values(\'"+team_id+"\',\'"+team_name+"\',\'"+leader_name+"\',\'"+date+"\');";
            stmt = Conn.createStatement();
            //삽입이나 수정시에는 executeUpdate 함수, rs는 int형을 사용하여야 하고
            //조회할때에는 executeQuery함수, rs는 ResultSet형을 사용하여야 한다.
            rs = stmt.executeUpdate(mySQL);
            if(rs < 0){
                //실패
            }
            stmt.close();
            Conn.close(); //사용한뒤 close
            Connect();
            rs = -1;
            stmt = null;
            //teamlist에 저장하는 함수
            mySQL = "insert into teamlist(user_id, team_id) values("+user_id+",\'"+team_id+"\');";
            stmt = Conn.createStatement();
            rs = stmt.executeUpdate(mySQL);
            if(rs < 0){
                //실패
            }
            stmt.close();
            Conn.close();
        } catch (Exception ex) {
            System.out.println("Exception" + ex);
        }
    }
    //팀원들을 조회하는 메소드
    public static ArrayList<User> showTeamMember(String team_id){
        Connect();
        Statement stmt = null;
        ResultSet rs = null;
        ArrayList<User> teamuser = new ArrayList<>();
        try {
            String mySQL = "select user.id, user.name, user.email from user, teamlist where user.id = teamlist.user_id AND teamlist.team_id =\'"+team_id+"\';";
            stmt = Conn.createStatement();

            rs = stmt.executeQuery(mySQL);
            while(rs.next()) { //한 행씩 읽어들임.
                User user = new User();
                user.setUserID(rs.getInt("id"));
                user.setUserName(rs.getString("name"));
                user.setUserEmail(rs.getString("email"));
                teamuser.add(user);
            }

            stmt.close();
            Conn.close(); //사용한뒤 close
        } catch (Exception ex) {
            System.out.println("Exception" + ex);
        }

        return teamuser;
    }
    //각 유저의 팀 개수 반환
    public static int getTeamNum(int user_id){
        int team_num=0;

        Connect();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            String mySQL = "select COUNT(*) from teamlist where user_id="+user_id +";";
            stmt = Conn.createStatement();

            rs = stmt.executeQuery(mySQL);
            if(rs.next()) { //한 행씩 읽어들임.
                team_num = rs.getInt("COUNT(*)");
            }

            stmt.close();
            Conn.close(); //사용한뒤 close

        } catch (Exception ex) {
            System.out.println("Exception" + ex);
        }

        return team_num;
    }
    //팀의 이름 반환
    public static String getTeamName(String team_id){
        String team_name="";

        Connect();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            String mySQL = "select * from team where teamid = \'"+team_id+"\';";
            stmt = Conn.createStatement();

            rs = stmt.executeQuery(mySQL);
            if(rs.next()) { //한 행씩 읽어들임.
                team_name=rs.getString("teamname");
            }

            stmt.close();
            Conn.close(); //사용한뒤 close

        } catch (Exception ex) {
            System.out.println("Exception" + ex);
        }

        return team_name;
    }
    //각 유저가 가입된 팀 리스트
    public static ArrayList<Team> showTeam(int user_id){
        Connect();
        Statement stmt = null;
        ResultSet rs = null;
        ArrayList<Team> team_list = new ArrayList<Team>();
        try {
            String mySQL = "select * from teamlist where user_id = "+user_id +";";
            stmt = Conn.createStatement();

            rs = stmt.executeQuery(mySQL);
            while(rs.next()) { //한 행씩 읽어들임.
                Team team = new Team();
                team.setTeamid(rs.getString("team_id"));
                team_list.add(team);
            }

            stmt.close();
            Conn.close(); //사용한뒤 close
        } catch (Exception ex) {
            System.out.println("Exception" + ex);
        }

        return team_list;
    }

    //팀원을 초대하기 위한 검색
    public ArrayList<User> selectTeamMember(String username){
        Connect();
        Statement stmt = null;
        ResultSet rs = null;
        ArrayList<Team> team_list = new ArrayList<Team>();
        ArrayList<User> selectuser = new ArrayList<>();
        try {
            String mySQL = "select id, name, email from user where name like \'%"+username +"%\';";
            stmt = Conn.createStatement();

            rs = stmt.executeQuery(mySQL);

            if(rs.next()) { //한 행씩 읽어들임.
                User user = new User();
                user.setUserID(rs.getInt("id"));
                user.setUserName(rs.getString("name"));
                user.setUserEmail(rs.getString("email"));
                selectuser.add(user);
            }

            stmt.close();
            Conn.close(); //사용한뒤 close
        } catch (Exception ex) {
            System.out.println("Exception" + ex);
        }

        return selectuser;
    }

    //팀원 초대
    public void addTeamMember(int user_id, String team_id){
        Connect();
        Statement stmt = null;
        int rs = -1;
        ArrayList<Team> team_list = new ArrayList<Team>();
        try {
            String mySQL = "insert into teamlist(user_id, team_id) values("+user_id+",\'"+team_id+"\');";
            stmt = Conn.createStatement();

            rs = stmt.executeUpdate(mySQL);
            if(rs < 0){
                //실패
            }
            stmt.close();
            Conn.close(); //사용한뒤 close
        } catch (Exception ex) {
            System.out.println("Exception" + ex);
        }

    }
}
