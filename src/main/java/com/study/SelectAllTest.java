package com.study;

import java.sql.*;

public class SelectAllTest {
    public static void main(String[] args) {
        // DB 연결을 위한 인터페이스
        Connection conn = null;
        PreparedStatement ps = null;
        // DBMS안에 있는 결과를 가져옴
        ResultSet rs = null;

        try {
            // DBMS접속, jdbc URL 은 DBMS에서 정한 방식으로 입력.
            // DBMS와 연결을 하고 Connection을 구현하고 있는 객체를 반환
            conn =
                    DriverManager.getConnection(
                            "jdbc:mysql://localhost/test?useUnicode=true&serverTimezone=Asia/Seoul",
                            "root",
                            "root");

            if (conn != null) {
                System.out.println("DBMS 연결 성공!");
                System.out.println(conn.getClass().getName()); // getClass().getName() 자바 리플렉션.
            }

            // SQL을 작성하고, SQL을 실행
            // conn야 지금 연결되 DBMS에 이 SQL을 준비해줘. 그런데 물음표 부분은 남겨놔.
            ps = conn.prepareStatement("select role_id, name from role");

            // select 문이 실행되면, 실행된 결과는 DBMS에 있다.
            // 실행된 결과를 ResultSet이 참조한다.
            rs = ps.executeQuery(); // select 문 실행

            // rs.next()는 한줄을 가져옴
            // next()는 데이터를 한 줄(record)을 읽어오면 true를 반환.
            // 더 이상 읽어올게 없을때까지 반복한다.
            while(rs.next()) {
                int roleId = rs.getInt("role_id");
                String name = rs.getString("name");
                System.out.println(roleId + ", " + name);
            }

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } finally {
            try {
                // rs 자원 해제
                if(rs != null)
                    rs.close();

                // ps 자원 해제
                if(ps != null)
                    ps.close();

                // 연결 끊기
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.out.println("SQLException: " + e.getMessage());
            }
        }
    }
}
