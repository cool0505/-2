
import java.io.*;
import java.net.*;

public class SendThread extends Thread {

	private Socket s_socket;
	private String sendString;

	@Override
	public void run() {

		super.run();
		
		try {
			
			PrintWriter out = new PrintWriter(s_socket.getOutputStream());

			out.println("Welcome!\n");
			
			if(sendString == null)
				System.out.println("Sending Error ..");
			out.close();
			s_socket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
/*
	public void setString(String str) {
		sendString = str;
	}
*/
	public void setSocket(Socket socket) {
		s_socket = socket;
	}
}
