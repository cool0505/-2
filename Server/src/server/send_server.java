package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

// 클라이언트에 데이터를 송신하는 클래스
// receive_server 클래스와 달리, 전달 하고자 할 때만 동작 (무한 반복 x)
public class send_server {

	String message = "0";   // 송신할 데이터를 저장하는 변수

	public void set_message(String message) {
		

		this.message = message;
		OutputStream os = null; 
		OutputStreamWriter osw = null; 
		BufferedWriter bw = null;

		System.out.println("> Status : " + message);
		
		try {

			PrintWriter print = new PrintWriter(Server.socket.getOutputStream());

			print.println(message);
			print.flush();
			
			System.out.println("> Send!\n");

		} catch (IOException e) {
		}
	}
}
