package server;

import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;

//클라이언트로부터 전달받은 데이터를 검수하고 sign_up, login 클래스에 전달
public class arraylist_user {

	connect_signup connect = new connect_signup();
	Statement stmt = null;
	ResultSet r;
	String message = "1";
	int state = 0;
	
	//receive_server 클래스에서 String 전달받음
	public void list(String receiveString) {

		sign_up sign = new sign_up();
		login login = new login();
	
		send_server send = new send_server();
		
		Connection conn = connect.makeconnect();
		
		// sign_up DB의 데이터를 불러와 array list에 저장
		ArrayList<user> list = new ArrayList<user>();

		try {

			stmt = conn.createStatement();
			
			// SELECT문을 사용해 sign_up 테이블에서 데이터 가져오기
			r = stmt.executeQuery("SELECT Classof, Name, ID, PW FROM user_signup");

			//DB에서 가져온 데이터를 list에 저장
			while (r.next()) {
				user user = new user();
				user.setClassof(r.getString("Classof"));
				user.setID(r.getString("Name"));
				user.setID(r.getString("ID"));
				user.setPW(r.getString("PW"));
				list.add(user);
			}
			
			System.out.println("> Create Arraylist\n");
			
			// 클라리언트에서 수신한 String을 /로 구분하여 배열에 저장
			StringTokenizer token = new StringTokenizer(receiveString, "/");
			String[] tokens = receiveString.split("/");

			// 학생증에서 도출한 학번을 받은 경우
			if (tokens[0] == "1")
				System.out.println(tokens[0]);
			
			// 로그인 시, 아이디와 비밀번호를 받은 경우
			else if (tokens[0] == "2") {
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
			else if (tokens[0] == "3") {
				for (int i = 0; i < list.size(); i++) {
					if (tokens[1].equals(list.get(i).getClassof()) == true || tokens[3].equals(list.get(i).getID()) == true) {
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
