package server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import com.mysql.cj.protocol.Resultset;

// 클라이언트의 회원가입을 관리하는 클래스
public class sign_up {

	connect_signup connect = new connect_signup();
	Statement stmt1 = null;
	Statement stmt2 = null;
	int r1;
	int r2;
	String message = null;

	public void insert_signup (String receiveString) {

		Connection conn = connect.makeconnect();
		Date now = new Date();

		ArrayList<user> list = new ArrayList<user>();

		try {
			stmt1 = conn.createStatement();
			stmt2 = conn.createStatement();

			String[] tokens = receiveString.split("/");

			// INSERT 문을 사용해 sign_up DB의 user_signup 테이블에 데이터 저장
			// 저장 데이터 -> 학번, 이름, 아이디, 비밀번호, 날짜
			r1 = stmt1.executeUpdate("insert into user_signup" + "(Classof, Name, ID, PW, Date) value ('" + tokens[0]
					+ "','" + tokens[1] + "','" + tokens[2] + "','" + tokens[3] + "','" + now + "')");

			if (r1 == 1) {   // 저장 성공
				System.out.println("\n>>User " + tokens[2] + " Sign up\n");
				
				// CREATE 문으로 sign_up DB에 유저의 아이디를 이름으로 하는 테이블 생성
				// 저장될 데이터 -> 건물(출입을 시도한), 출입 여부, 날짜
				String sql = "CREATE TABLE sign_up." + tokens[2] + " ("
						+ "Building VARCHAR(45),"
						+ "Status INTEGER,"
						+ "Date VARCHAR(45)"
						+ ")";
				
				r2 = stmt2.executeUpdate(sql);
				
				if (r2 == 0)   // 생성 성공
					System.out.println(">>Create user " + tokens[2] + " table\n");
				else   // 생성 실패
					System.out.println(">>fail");
			}
			else   // 저장 실패
				System.out.println(">>fail");
			
			stmt1.close();
			stmt2.close();

		} catch (SQLException ex) {
			System.out.println("SQLException:" + ex);
		} catch (Exception e) {
			System.out.println("Exception:" + e);
		}
	}
}
