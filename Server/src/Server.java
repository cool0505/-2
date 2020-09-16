import java.io.*;
import java.net.*;
import java.sql.*;

public class Server {

	public static void main(String[] args){
		
		try {
			ServerSocket serversocket = new ServerSocket(8889);
			System.out.println("server Info : " + serversocket.getInetAddress().getLocalHost());
			System.out.println("DB connecting ..");
			
			while(true) {
				Socket socket = serversocket.accept();
				
				ReceiveThread receive = new ReceiveThread();
				receive.setSocket(socket);
				SendThread send = new SendThread();
				send.setSocket(socket);
				
				receive.start();
				send.start();
			}
			
		}catch(Exception e) {}
	}
}
