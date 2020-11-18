package server;

import java.util.Date;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

// 클라이언트의 로그인을 관리하는 클래스
public class login {

	connect_login connect = new connect_login();
	Statement stmt = null;
	int r;
	String message = null;

	public void insert_login(String receiveString) {

		System.out.println(receiveString + "login mes");
		Date now = new Date();
		Connection conn = connect.makeconnect();

		try {

			Statement stmt = conn.createStatement();
			
			String[] tokens = receiveString.split("/");

			// INSERT 문을 사용해 login DB의 user_login 테이블에 데이터 저장
			// 저장 데이터 -> 아이디, 비밀번호, 날짜
			r = stmt.executeUpdate("insert into user_login" + "(ID, PW, Date) value ('" + tokens[0] + "','"
					+ tokens[1] + "','" + now + "')");

			if (r == 1)   // 저장 성공
				System.out.println(">>User login\n");
			else   // 저장 실패
				System.out.println(">>DB connect fail");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
