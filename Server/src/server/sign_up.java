package server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class sign_up {

	connect_signup connect = new connect_signup();
	Statement stmt = null;
	int r;
	String message = null;

	public void insert_signup (String receiveString) {

		Connection conn = connect.makeconnect();
		Date now = new Date();

		ArrayList<user> list = new ArrayList<user>();

		try {
			stmt = conn.createStatement();

			Statement stmt1 = conn.createStatement();

			String[] tokens = receiveString.split("/");

			r = stmt.executeUpdate("insert into user_signup" + "(Classof, Name, ID, PW, Date) value ('" + tokens[0]
					+ "','" + tokens[1] + "','" + tokens[2] + "','" + tokens[3] + "','" + now + "')");

			if (r == 1)
				System.out.println("User " + tokens[1] + " Sign up\n");
			else
				System.out.println("fail");

			stmt.close();

		} catch (SQLException ex) {
			System.out.println("SQLException:" + ex);
		} catch (Exception e) {
			System.out.println("Exception:" + e);
		}
	}
}
