import java.io.*;
import java.net.*;

public class SendThread extends Thread {

	INSERT insert = new INSERT();
	private Socket s_socket;
	String sign_up;

	@Override
	public void run() {

		super.run();

		try {
			
			PrintWriter out = new PrintWriter(s_socket.getOutputStream());
			
			sign_up = insert.state();
			out.println(sign_up);
			
			out.close();
			s_socket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setSocket(Socket socket) {
		s_socket = socket;
	}
}
