package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class admin {

	connect_signup signup = new connect_signup();
	connect_building building = new connect_building();
	connect_bus bus = new connect_bus();

	Statement stmt = null;
	int r1;
	ResultSet r2;

	String data[][] = new String[20][3];
	String user[][] = new String[20][5];
	String send_data;
	int count = 0;

	void work(String receiveString) throws SQLException {

		Connection conn_building = building.makeconnect();
		Connection conn_signup = signup.makeconnect();
		Connection conn_bus = bus.makeconnect();

		String[] tokens = receiveString.split("/");

		// < 4/(1-조회 2-추가 3-변경 4-삭제)/(앞이 1-건물or버스or사용자 2,3,4-내용)/(앞이 건물-건물이름 버스-경로명)

		try {

			Statement stmt_building = conn_building.createStatement();
			Statement stmt_bus = conn_bus.createStatement();
			Statement stmt_signup = conn_signup.createStatement();
			sign_up signup = new sign_up();
			Date now = new Date();

			// 건물, 빌딩 DB 조회
			if (tokens[1].equals("1") == true) {
				if (tokens[2].equals("building") == true || tokens[2].equals("bus") == true) {
					r2 = stmt_building.executeQuery("SELECT StudentNum, Status, Date FROM " + tokens[3]);
					while (r2.next()) {

						data[count][0] = r2.getString("StateNum");
						data[count][1] = r2.getString("Status");
						data[count][2] = r2.getString("Date");

						count++;
					}

					send_data = data[0][0] + "/" + data[0][1] + "/" + data[0][2] + "/" + "-";

					for (int i = 1; i < count; i++) {
						for (int j = 0; j < 3; j++) {

							send_data = send_data + data[i][j] + "/";
							if (j == 2) {
								send_data = send_data + "-";
							}
						}
					}
				}

				// 사용자 DB 조회
				if (tokens[2].equals("user") == true) {
					r2 = stmt_building.executeQuery("SELECT StudentNum, Name, ID, PW, Date FROM " + tokens[3]);
					while (r2.next()) {

						user[count][0] = r2.getString("StateNum");
						user[count][1] = r2.getString("Name");
						user[count][2] = r2.getString("ID");
						user[count][3] = r2.getString("PW");
						user[count][4] = r2.getString("Date");

						count++;
					}

					send_data = user[0][0] + "/" + user[0][1] + "/" + user[0][2] + "/" + user[0][3] + "/" + user[0][4] + "/" +"-";

					for (int i = 1; i < count; i++) {
						for (int j = 0; j < 5; j++) {

							send_data = send_data + user[i][j] + "/";
							if (j == 4) {
								send_data = send_data + "-";
							}
						}
					}
				}
			}
			
			// 사용자 데이터 추가
			if(tokens[1].equals("2")==true) {
				
				String send = "3/" + tokens[2] + "/" + tokens[3] + "/" + tokens[4] + "/" + tokens[5];
				signup.insert_signup(send);
			}
			
			// 사용자 데이터 변경
			if(tokens[1].equals("3")==true) {
				r1 = stmt_signup.executeUpdate("update freeboard user_signup set " + tokens[3] + " StudentNum = " + tokens[2]);
				
				if(r1 == 1) {
					System.out.println(">> Update User\n");
					send_data = "1";
				}
				else {
					System.out.println(">> Fail\n");
					send_data = "0";
				}
			}
			
			// 사용자 데이터 삭제
			if(tokens[1].equals("4")==true) {
				r1 = stmt_signup.executeUpdate("delete from user_signup where StudentNum = " + tokens[2]);
				
				if(r1 == 1) {
					System.out.println(">> Delete User\n");
					send_data = "1";
				}
				else {
					System.out.println(">> Fail\n");
					send_data = "0";
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
		}
	}
}
