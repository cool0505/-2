
import java.util.Date;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Login {

	Connect_signup connect1 = new Connect_signup();
	Connect_login connect2 = new Connect_login();
	Statement stmt = null;
	ResultSet r1;
	int r2;

	public void login(String receiveString) {
		System.out.println(receiveString+"login mes");
		Date now = new Date();
		Scanner scn = new Scanner(System.in);
		Connection conn1 = connect1.makeconnect();
		Connection conn2 = connect2.makeconnect();
		ArrayList<USER> list = new ArrayList<USER>();
		
		try {
			Statement stmt1 = conn1.createStatement();
			r1 = stmt1.executeQuery("SELECT ID, PW FROM user_signup");
			
			while(r1.next()) {
				USER user = new USER();
			//	user.setID(r1.getString("NAME"));
				user.setID(r1.getString("ID"));
				user.setPW(r1.getString("PW"));
				list.add(user);
			}
			String[]tokens = receiveString.split("/");

			Statement stmt2 = conn2.createStatement();
			
			for(int i=0;i<list.size(); i++) {
				if(tokens[0].equals(list.get(i).getID())&&tokens[1].equals(list.get(i).getPW())) {
					//System.out.println("User '" + list.get(i).getName() + "' Login!");
					r2 = stmt2.executeUpdate("insert into user_login" + "(ID, PW, Date) value ('"
							+ list.get(i).getID() + "','" + list.get(i).getPW() + "','" + now + "')");
				}
			}
			
			if(r2==1) {
				System.out.println("User login");
			}
			else {
				System.out.println("fail");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
