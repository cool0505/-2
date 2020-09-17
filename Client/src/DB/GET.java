package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/*
 * 테이블 생성 및 출력
 * 사용법
 * String[][] data = GET.getUser();
 * DefaultTableModel defaultTableModel = new DefaultTableModel(data, headers);
 * 
 * */
public class GET {

	public static String[][] getUser(){
		try{
			Connection conn = getConnection();
			PreparedStatement statement = conn.prepareStatement("Select Num, Name, DP, ID, PW FROM User");
			ResultSet results = statement.executeQuery();
			ArrayList<String[]> list = new ArrayList<String[]>();
			while(results.next()){
				list.add(new String[]{
							results.getString("Num"),
							results.getString("Name"),
							results.getString("DP"),
							results.getString("ID"),
							results.getString("PW")
						});
			}
			System.out.println("The data has been fetched");
			String[][] arr = new String[list.size()][5];
			return list.toArray(arr);
			
		}catch(Exception e){
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public static void createUser(String num, String name, String dp, String id, String pw ){
		try{
			Connection conn = getConnection();
			PreparedStatement insert = conn.prepareStatement(""
					+ "INSERT INTO User"
					+ "(Num, Name, DP, ID, PW) "
					+ "VALUE "
					+ "('"+num+"','"+name+"','"+dp+"','"+id+"','"+pw+"')");
			insert.executeUpdate();
			System.out.println("The data has been saved!");
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public static void createTable(){
		try{
			Connection conn = getConnection();
			PreparedStatement create = conn.prepareStatement(
					"CREATE TABLE IF NOT EXISTS "
					+ "User(Num int NOT NULL AUTO_INCREMENT,"
					+ "Num varChar(45),"
					+ "Name varChar(45),"
					+ "DP varChar(45),"
					+ "ID varChar(45),"
					+ "PW varChar(45),"
					+ "PRIMARY KEY(Num))");
			create.execute();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally{
			System.out.println("Table successfully created");
		}
	}

	
	public static Connection getConnection(){
		try {
			Connection conn = null;
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sign_up?serverTimezone=UTC", "root",
					"0000");
			System.out.println("Success!");
			return conn;
		}catch(Exception e){
			System.out.println(e.getMessage());
			return null;
		}
	}
}

