package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	static Socket socket;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		

		try {
			ServerSocket serversocket = new ServerSocket(8282);
			
			socket = serversocket.accept();
			
			receive_server receive = new receive_server();
			receive.setSocket(socket);
			
		//	send_server send = new send_server(socket);
		//	send.setSocket(socket);
			
		//	arraylist_user array = new arraylist_user();
			
			receive.start();
			//send.start();
			
		}catch(IOException e) {}
	}

}