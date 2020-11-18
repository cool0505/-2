package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// bus_owner DB와 연결하기 위한 클래스
public class connect_bus {
	public Connection makeconnect() {
		
		Connection conn = null;
	    
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bus_owner?serverTimezone=UTC", "root", "Rlanwjd67!");
	        if(conn != null)
	        	System.out.println("> bus_owner DB connect!");
	    } catch (SQLException ex) {
	        System.out.println("SQLException:" + ex);
	    } catch (Exception e) {
	        System.out.println("Exception:" + e);
	    }
	    
	    return conn;
		
	}
}
