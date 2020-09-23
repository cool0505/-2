package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class send_server extends Thread{

	private Socket s_socket;
	
	@Override
	public void run() {
		
		super.run();
		
		try {
			BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
			PrintWriter print = new PrintWriter(s_socket.getOutputStream());
			
			String sendString;
			
			while(true) {
				
				sendString = buffer.readLine();
				
				print.println(sendString);
				print.flush();
			}
		}catch(IOException e) {}
	}
	
	public void setSocket(Socket socket) {
		
		s_socket = socket;
	}
}
