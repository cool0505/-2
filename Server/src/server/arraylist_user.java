package server;

import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class arraylist_user {

	connect_signup connect = new connect_signup();
	Statement stmt = null;
	ResultSet r;
	String message = "1";
	int state = 0;
	
	public void list(String receiveString) {

		sign_up sign = new sign_up();
		login login = new login();
	
		send_server send = new send_server();
		
		Connection conn = connect.makeconnect();
		ArrayList<user> list = new ArrayList<user>();

		try {

			stmt = conn.createStatement();
			r = stmt.executeQuery("SELECT Classof, Name, ID, PW FROM user_signup");

			while (r.next()) {
				user user = new user();
				user.setClassof(r.getString("Classof"));
				user.setID(r.getString("Name"));
				user.setID(r.getString("ID"));
				user.setPW(r.getString("PW"));
				list.add(user);
			}
			
			System.out.println("> Create Arraylist\n");
			
			StringTokenizer token = new StringTokenizer(receiveString, "/");
			String[] tokens = receiveString.split("/");

			if (tokens.length == 1)
				System.out.println(tokens[0]);
			
			if (tokens.length == 2) {
				for (int i = 0; i < list.size(); i++) {
					if (tokens[0].equals(list.get(i).getID()) == true && tokens[1].equals(list.get(i).getPW()) == true) {
						login.insert_login(receiveString);
						send.set_message(message);
						this.state = 1;
						break;
					}
				}
				
				if(this.state != 1) {
					message = "0";
					send.set_message(message);
					this.state = 0;
				}
				this.state = 0;
			}
			
			if (tokens.length == 4) {
				for (int i = 0; i < list.size(); i++) {
					if (tokens[0].equals(list.get(i).getClassof()) == true || tokens[2].equals(list.get(i).getID()) == true) {
						message = "0";
						send.set_message(message);
						this.state = 1;
						break;
					}
				}
				
				if(this.state != 1) {
					sign.insert_signup(receiveString);
					send.set_message(message);
				}

				this.state = 0;
			}

		} catch (SQLException e) {
		}
	}
}
