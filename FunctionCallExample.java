package mysql.sec12;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.sql.CallableStatement;

public class FunctionCallExample {

	public static void main(String[] args) {
		Connection conn = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/thisisjava",
					"root",
					"1234");
			
			String sql = "SELECT user_login(?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1,  "java");
			pstmt.setString(2,  "12345");
			
			ResultSet rs = pstmt.executeQuery();
			int result;
			
			if(rs.next()) {
				result = rs.getInt(1);
			}else {
				throw new Exception("아이디 또는 비번이 맞지 않습니다.");
			}
			String message = switch(result) {
			case 0 -> "로그인 성공";
			case 1 -> "비밀번호가 틀림";
			default -> "아이디가 존재하지 않음";
			};
			
			System.out.println(message);
			pstmt.close();
			
			
			}catch(Exception e) {
				e.printStackTrace();
		}finally {
			if(conn != null) {
				try {
					conn.close();
				}catch(SQLException e) {}
			}
		}
	}

}
