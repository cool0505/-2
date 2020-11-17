package server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import com.mysql.cj.protocol.Resultset;

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

			r1 = stmt1.executeUpdate("insert into user_signup" + "(Classof, Name, ID, PW, Date) value ('" + tokens[0]
					+ "','" + tokens[1] + "','" + tokens[2] + "','" + tokens[3] + "','" + now + "')");

			if (r1 == 1)
				System.out.println("\n>>User " + tokens[2] + " Sign up\n");
			else
				System.out.println(">>fail");
			
			String sql = "CREATE TABLE sign_up." + tokens[2] + " ("
														+ "Building VARCHAR(45),"
														+ "Status INTEGER,"
														+ "Date VARCHAR(45)"
														+ ")";
			
			r2 = stmt2.executeUpdate(sql);
			
			if (r2 == 0)
				System.out.println(">>Create user " + tokens[2] + " table\n");
			else
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
