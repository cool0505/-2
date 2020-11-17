package DB;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CONNECT {
	Connection conn = null;
	
	public Connection getDB() {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sign_up?serverTimezone=UTC", "root",
<<<<<<< HEAD:Client/src/DB/CONNECT.java
					"0000");
			
			System.out.println("Success!");
=======
					"Rlanwjd67!");
			if (conn != null)
				System.out.println("> Sign up DB connect!");
>>>>>>> origin/HooJeong:Server/src/server/connect_signup.java
		} catch (SQLException ex) {
			System.out.println("SQLException:" + ex);
		} catch (Exception e) {
			System.out.println("Exception:" + e);
		}
		return conn;
	}
}

