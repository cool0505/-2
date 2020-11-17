package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//sign_up DB와 연결하기 위한 클래스
public class connect_signup {
	public Connection makeconnect() {

		Connection conn = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sign_up?serverTimezone=UTC", "root",
					"Rlanwjd67!");
			if (conn != null)
				System.out.println("> Sign up DB connect!");
		} catch (SQLException ex) {
			System.out.println("SQLException:" + ex);
		} catch (Exception e) {
			System.out.println("Exception:" + e);
		}

		return conn;

	}
}
