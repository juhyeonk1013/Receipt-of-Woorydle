package com.flowerroad.receiptofwoorydle;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by juhyun on 2018-01-24.
 */

public class MariaConnect {
    private static Connection Conn = null;

    public void MariaConnect(){

    }
    public static void Connect(){ //처음 연결
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
    public static void signUpDB(int user_id, String user_name, String user_email){
        //첫 가입시 데이터베이스에 저장
        Connect();
        Statement stmt = null;
        int rs = -1;

        try {
            String mySQL = "insert into user(id, name, email) values("+user_id+",\'"+user_name+"\',\'"+user_email+"\');";
            stmt = Conn.createStatement();
            Log.d("MariaConnect"," falsefalsefalse");
            //삽입이나 수정시에는 executeUpdate 함수, rs는 int형을 사용하여야 하고
            //조회할때에는 executeQuery함수, rs는 ResultSet형을 사용하여야 한다.
            rs = stmt.executeUpdate(mySQL);
            Log.d("MariaConnect"," falsefalsefalse_true");
            if(rs < 0){
                //성공
                Log.i("MariaConnect"," Inserted signUp info into database");
            }


            stmt.close();
            Conn.close(); //사용한뒤 close
        } catch (Exception ex) {
            System.out.println("Exception" + ex);
        }
    }
}
