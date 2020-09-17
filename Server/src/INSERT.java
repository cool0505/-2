
import java.sql.*;
import java.util.Date;

public class INSERT {

	Connect_signup connect = new Connect_signup();
	
	Statement stmt =  null;
	int r = 0;
	private String state;
	
	public void insert(String receiveString) {
		
		Connection conn = connect.makeconnect();
		Date now = new Date();
		
		try {
			stmt = conn.createStatement();
			
			String[]tokens = receiveString.split("/");
			
			r = stmt.executeUpdate("insert into user_signup" + "(SchoolNum, Name, ID, PW, Date) value ('"
					+ tokens[0] + "','" + tokens[1] + "','" + tokens[2] + "','" + tokens[3] + "','" + now + "')");
			if(r==1) {
				System.out.println("User " + tokens[1] + " Sign up");
				state = "Success"; 
			}
			else {
				System.out.println("fail");
				state = "Fail";
			}

			stmt.close();
			
		} catch (SQLException ex) {
	        System.out.println("SQLException:" + ex);
	    } catch (Exception e) {
	        System.out.println("Exception:" + e);
	    }
	}
	
	public String state() {
		String sign_up;
		sign_up = state;
		
		return sign_up;
	}
}