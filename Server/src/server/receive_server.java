package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.StringTokenizer;

// 클라이언트로부터 데이터를 받는 클래스
public class receive_server extends Thread{
	
	login login = new login();
	sign_up signup = new sign_up();
	private Socket c_socket;
	
	@Override
	public void run() {
		
		super.run();
		
		arraylist_user array = new arraylist_user();
		
		try {
			BufferedReader buffer = new BufferedReader(new InputStreamReader(c_socket.getInputStream()));
			
			String receiveString;
			
			while(true) {   // 무한 반복으로 데이터 수신을 기다림
				
				receiveString = buffer.readLine();
				
				if(receiveString == "exit") {
					System.out.println("connect exit");
					break;
				}
				
				else {
					System.out.println(receiveString);
					
					array.list(receiveString);
				}
			}
			
			buffer.close();
		}catch(IOException e) {}
	}
	
	public void setSocket(Socket socket) {
		c_socket = socket;
	}
}
