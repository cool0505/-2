package server;

import java.util.Date;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

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

			r = stmt.executeUpdate("insert into user_login" + "(ID, PW, Date) value ('" + tokens[0] + "','"
					+ tokens[1] + "','" + now + "')");

			if (r == 1)
				System.out.println("User login\n");
			else
				System.out.println("DB connect fail");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
