package com.study;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionTest01 {
    public static void main(String[] args) {
        // DB 연결을 위한 인터페이스
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            // DBMS접속, jdbc URL 은 DBMS에서 정한 방식으로 입력.
            // DBMS와 연결을 하고 Connection을 구현하고 있는 객체를 반환
            conn =
                    DriverManager.getConnection(
                            "jdbc:mysql://localhost/exampledb?useUnicode=true&serverTimezone=Asia/Seoul",
                            "gihun",
                            "gihun");

            // 자동 커밋을 false로 설정
            conn.setAutoCommit(false);

            // begin을 할 필요가 없음

            // SQL을 작성하고, SQL을 실행
            // conn야 지금 연결되 DBMS에 이 SQL을 준비해줘. 그런데 물음표 부분은 남겨놔.
            // 준비한 것을 참조하도록 PreparedStatement를 반환
            ps = conn.prepareStatement("delete  from role where role_id = ?");

            // 물음표에 값을 채워줘, 바인딩. 바인딩 까지 하면 SQL을 실행할 준비
            ps.setInt(1, 3); // 1번째 물음표에 정수 값을 설정

            // SQL실행 executeUpdate(); - insert, update, delete 할 때 사용.
            int updateCount = ps.executeUpdate();
            System.out.println("수정된 건수 : " + updateCount);

            System.out.printf("10초간쉰다.");
            Thread.sleep(10000); // 10초간 정지
            System.out.println("10초간 다 쉬었다");
            conn.commit();
        } catch (Exception ex) {
            try {
                System.out.println("ROLEBACK 합니다!!");
                conn.rollback();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } finally {
            try {
                // ps 자원 해제
                if (ps != null)
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
