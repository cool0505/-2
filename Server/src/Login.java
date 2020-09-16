
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Login {

	Connect connect = new Connect();
	Statement stmt = null;
	ResultSet r;

	public void login(String receiveString) {
		
		Scanner scn = new Scanner(System.in);
		Connection conn = connect.makeconnect();
		ArrayList<USER> list = new ArrayList<USER>();
		
		try {
			Statement stmt = conn.createStatement();
			r = stmt.executeQuery("SELECT ID, PW FROM User");
			
			while(r.next()) {
				USER user = new USER();
				user.setID(r.getString("ID"));
				user.setPW(r.getString("PW"));
				list.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String[]tokens = receiveString.split("/");

		for(int i=0;i<list.size(); i++) {
			if(tokens[0].equals(list.get(i).getID())&&tokens[1].equals(list.get(i).getPW())) {
				System.out.println("Login!");
			}
		}
	}
}
