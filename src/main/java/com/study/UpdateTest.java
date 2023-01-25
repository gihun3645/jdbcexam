package com.study;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateTest {
    public static void main(String[] args) {
        // DB 준비
        Connection conn = null;
        PreparedStatement ps = null;

        try {

            conn =
                    DriverManager.getConnection(
                            "jdbc:mysql://localhost/test?useUnicode=true&serverTimezone=Asia/Seoul",
                            "root",
                            "root");

            if (conn != null) {
                System.out.println("DB 연결!");
                System.out.println(conn.getClass().getName());
            }

            // SQL을 사용할 수 있도록 준비
            ps = conn.prepareStatement("update user set name=?, email=? where user_id=?");

            ps.setInt(3, 2);
            ps.setString(1, "송기훈");
            ps.setString(2, "gihun3645@naver.com");

            int updateCount = ps.executeUpdate();
            System.out.println(updateCount+"건의 데이터가 수정되었습니다.");

            // SQL 실행코드 준비
        } catch (SQLException ex) {
            System.out.println("SQLException: "+ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } finally {
            try {
                // 연결 끊기
                if (ps != null)
                    ps.close();

                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.out.println("SQLException: "+e.getMessage());
            }
        }
    }
}
