package server;

import java.util.Date;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

// 클라이언트의 로그인을 관리하는 클래스
public class login {

	connect_login connect1 = new connect_login();
	connect_signup connect2 = new connect_signup();
	Statement stmt = null;
	int r1;
	ResultSet r2;
	String message = null;

	String[][] data = new String[10][3];
	int count = 0;
	int count2 = 0;
	
	public void insert_login(String receiveString) {

		System.out.println(receiveString + "login mes");
		Date now = new Date();
		Connection conn1 = connect1.makeconnect();
		Connection conn2 = connect2.makeconnect();

		try {

			Statement stmt1 = conn1.createStatement();
			Statement stmt2 = conn2.createStatement();
			
			String[] tokens = receiveString.split("/");

			// INSERT 문을 사용해 login DB의 user_login 테이블에 데이터 저장
			// 저장 데이터 -> 아이디, 비밀번호, 날짜
			r1 = stmt1.executeUpdate("insert into user_login" + "(ID, PW, Date) value ('" + tokens[1] + "','"
					+ tokens[2] + "','" + now + "')");

			if (r1 == 1)   // 저장 성공
				System.out.println(">>User login\n");
			else   // 저장 실패
				System.out.println(">>DB connect fail");

			r2 = stmt2.executeQuery("SELECT Building, Status, Date FROM " + tokens[1]);
			
		//	String[][] data = new String[10][3];
		//	int count = 0;
		//	int count2 = 0;
			
			while (r2.next()) {

				data[count][0] = r2.getString("Building");
				data[count][1] = r2.getString("Status");
				data[count][2] = r2.getString("Date");
				
				count++;	
			}
			
		//	for(int i = 0; i<count; i++)
			String send_data = data[0][0] + "/" + data[0][1] + "/" + data[0][2] + "/" + "-";
			
			for(int i=1; i<count; i++) {
				for(int j=0; j<3;j++)  {
					
					send_data = send_data + data[i][j] + "/";
					if(j==2) {
						send_data = send_data + "-";
					}
				//	if(data[i][0].equals(null) == true)
				//		break;
				}
			}
			

			
			try {

				PrintWriter print = new PrintWriter(Server.socket.getOutputStream());

				print.println(send_data);
				print.flush();
				
				System.out.println("> Send!\n");

			} catch (IOException e) {
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
