package com.study;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConnectionTest {
    public static void main(String[] args) {
        // DB 연결을 위한 인터페이스
        Connection conn = null;
        // PreparedStatement
        PreparedStatement ps = null;

        try {
            // DBMS접속, jdbc URL 은 DBMS에서 정한 방식으로 입력.
            // DBMS와 연결을 하고 Connection을 구현하고 있는 객체를 반환
            // jdbc url 주소와 dbms 정보 입력

            // 이해가 안되면 인터페이스 문법
            conn =
                    DriverManager.getConnection(
                            "jdbc:mysql://localhost/test?useUnicode=true&serverTimezone=Asia/Seoul",
                            "root",
                            "root");

            if (conn != null) {
                System.out.println("DBMS 연결 성공!");
                System.out.println(conn.getClass().getName()); // getClass().getName() 자바 리플렉션관련된 메소드
            }

            // SQL을 작성하고, SQL을 실행하는 코드를 작성할 수 있다.
            // conn야 지금 연결되 DBMS에 이 SQL을 준비해줘. 그런데 물음표 부분은 남겨놔. (실행 X)
            // 준비한 것을 참조하도록 PrepareStatement를 반환
            ps = conn.prepareStatement("insert into role(role_id, name) values (?,?)");

            // 물음표에 값을 채워줘, 바인딩. 바인딩까지 하면 SQL을 실행할 준비
            ps.setInt(1, 3); // 1번째 물음표에 정수 값을 설정
            ps.setString(2, "ROLE_TEST"); // 2번째 물음표에 문자열 값을 설정

            // SQL실행 executeUpdate(); - insert, update, delete 할 때 사용.
            int updateCount = ps.executeUpdate();
            System.out.println("수정된 건수 : " + updateCount);

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } finally {
            try {
                // ps 도 연결을 끊어줘야함
                if(ps != null)
                    ps.close();

                // 연결 끊기
                // null 이 아닐 때만 close
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.out.println("SQLException: " + e.getMessage());
            }
        }
    }
}
