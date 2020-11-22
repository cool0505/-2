package server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

//클라이언트로부터 전달받은 데이터를 검수하고 sign_up, login 클래스에 전달
public class arraylist_user {

	connect_signup connect1 = new connect_signup();
	connect_building connect2 = new connect_building();
	Statement stmt1 = null;
	Statement stmt2 = null;
	ResultSet r;
	int r2;
	String message = "1";
	String num_data = null;
	static int s = 0;
	int state = 0;
	
	//receive_server 클래스에서 String 전달받음
	public void list(String receiveString) {

		sign_up sign = new sign_up();
		login login = new login();
		Date now = new Date();
	
		send_server send = new send_server();
		
		Connection conn1 = connect1.makeconnect();
		Connection conn2 = connect2.makeconnect();
		
		// sign_up DB의 데이터를 불러와 array list에 저장
		ArrayList<user> list = new ArrayList<user>();

		try {

			stmt1 = conn1.createStatement();
			
			// SELECT문을 사용해 sign_up 테이블에서 데이터 가져오기
			r = stmt1.executeQuery("SELECT StudentNum, Name, ID, PW FROM user_signup");

			//DB에서 가져온 데이터를 list에 저장
			while (r.next()) {
				user user = new user();
				user.setStudentNum(r.getString("StudentNum"));
				user.setName(r.getString("Name"));
				user.setID(r.getString("ID"));
				user.setPW(r.getString("PW"));
				list.add(user);
			}
			
			System.out.println("> Create Arraylist\n");
			
			// 클라리언트에서 수신한 String을 /로 구분하여 배열에 저장
			StringTokenizer token = new StringTokenizer(receiveString, "/");
			String[] tokens = receiveString.split("/");

			// 학생증에서 도출한 학번을 받은 경우
			if (tokens[0].equals("1")) {
				
				OutputStream os = null; 
				OutputStreamWriter osw = null; 
				BufferedWriter bw = null;

				System.out.println("> Student Number : " + tokens[1]);
				
				for(int i=0; i<list.size();i++) {
					if(tokens[1].equals(list.get(i).getStudentNum()) == true) {
						num_data = list.get(i).getName() + "님 환영합니다.";
						System.out.println("> Data Correct\n");
						s = 1;
					}
				}
				
				if(s == 0) {
					num_data = "일치하는 회원정보가 없습니다.";
					System.out.println("> Data Incorrect\n");
				}
				
				try {

					PrintWriter print = new PrintWriter(Server.socket.getOutputStream());

					print.println(num_data);
					print.flush();
					
					System.out.println("> Data Send!\n");
					
					try {
						
						if(s == 1) {
							stmt2 = conn2.createStatement();
						
							r2 = stmt2.executeUpdate("insert into main" + "(StudentNum, Status, Date) value ('" + tokens[1] + "','"
									+ "1" + "','" + now + "')");
							s = 0;
							if (r2 == 1)   // 저장 성공
								System.out.println("> Save Data\n");
						}
					}catch(SQLException e) {}
				} catch (IOException e) {
				}	
			}
			
			// 로그인 시, 아이디와 비밀번호를 받은 경우
			else if (tokens[0].equals("2")) {
				for (int i = 0; i < list.size(); i++) {
					
					// 회원가입 시 입력한 데이터와 로그인 시 입력한 데이터가 동일
					if (tokens[1].equals(list.get(i).getID()) == true && tokens[2].equals(list.get(i).getPW()) == true) {
						
						// login 클래스로 전달
						login.insert_login(receiveString);
						send.set_message(message);
						this.state = 1;
						break;
					}
				}
				
				//데이터가 일치하지 않을 경우
				if(this.state != 1) {
					message = "0";
					send.set_message(message);
					this.state = 0;
				}
				this.state = 0;
			}
			
			// 회원가입 시, 기존의 다른 사용자와 학번이나 아이디가 동일할 경우
			else if (tokens[0].equals("3")) {
				for (int i = 0; i < list.size(); i++) {
					if (tokens[1].equals(list.get(i).getStudentNum()) == true || tokens[3].equals(list.get(i).getID()) == true) {
						message = "0";
						send.set_message(message);
						this.state = 1;
						break;
					}
				}
				
				// 문제가 없는 경우
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
