package com.study;

import java.sql.*;

public class TransactionTest03 {
    public static void main(String[] args) {
        // DB 연결을 위한 인터페이스
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // DBMS접속, jdbc URL 은 DBMS에서 정한 방식으로 입력.
            // DBMS와 연결을 하고 Connection을 구현하고 있는 객체를 반환
            conn =
                    DriverManager.getConnection(
                            "jdbc:mysql://localhost/exampledb?useUnicode=true&serverTimezone=Asia/Seoul",
                            "gihun",
                            "gihun");

            conn.setAutoCommit(false);

            ps = conn.prepareStatement("update board set view_cnt = view_cnt + 1 where board_id = ?");
            ps.setInt(1, 1);
            int updateCount = ps.executeUpdate();
            if(updateCount == 0)
                throw new RuntimeException("board_id에 해당하는 자료가 없습니다.");

            ps.clearParameters();
            ps = conn.prepareStatement("select board_id, title, content, user_id, regdate, view_cnt from board where board_id = ?");
            ps.setInt(1, 1); // 1번째 물음표에 정수 값을 설정
            rs = ps.executeQuery();
            if(rs.next()) { // 데이터를 읽어왔을 때
                int boardId = rs.getInt("board_Id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                int userId = rs.getInt("user_id");
                Date regdate = rs.getDate("regdate");
                int viewCnt = rs.getInt("view_cnt");
                System.out.println(boardId + ", " + title + ", " + content + ", " + userId + ", " + regdate + ", " + viewCnt);
            } else {
                throw new RuntimeException("board_id에 해당하는 자료가 없습니다.");
            }
            conn.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                System.out.println("ROLEBACK 합니다!!");
                conn.rollback();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } finally {
            try {
                // rs 자원 해제
                if (rs != null)
                    rs.close();
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
