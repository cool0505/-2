
import java.io.*;
import java.net.*;
import java.sql.*;

public class Server {
	
	public static void main(String[] args) {
		
		Runnable r = new server_();
		Thread t = new Thread(r);
		t.start();
	}
}

/*  서버와 클라이언트에 각각 Send, Receive 클래스를 추가하면 오류 발생
 *  java.net.SocketException: Connection reset
 *  -> at ReceiveThread.run(ReceiveThread.java:24)
 *  
 *  서버보다 클라이언트를 더 먼저 닫을 경우, 같은 오류 발생
 *  
 *  서버
 *  java.net.SocketException: Connection reset
 *  -> at ReceiveThread.run(ReceiveThread.java:24)
 *  
 *  클라이언트
 *  java.net.SocketException: Socket is closed
 *  -> at Client.ReceiveThread.run(ReceiveThread.java:22)
 */

class server_ implements Runnable{
	
	@Override
	public void run() {
		
		try {
			ServerSocket serversocket = new ServerSocket(8889);
			System.out.println("server Info : " + serversocket.getInetAddress().getLocalHost());
			System.out.println("DB connecting ..");
			
			
			while(true) {
				Socket socket = serversocket.accept();
				
				ReceiveThread receive = new ReceiveThread();
				receive.setSocket(socket);
				receive.start();
				
				SendThread send = new SendThread();
			//	send.setSocket(socket);
			//	send.start();
			}
			
		}catch(Exception e) {}
	}
}
