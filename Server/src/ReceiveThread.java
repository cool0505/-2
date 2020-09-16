import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.StringTokenizer;


public class ReceiveThread extends Thread{
	
	Login login = new Login();
	INSERT insert = new INSERT();
	private Socket s_socket;
	
	@Override
	public void run() {
		
		super.run();
			
		try {
			while(true) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(s_socket.getInputStream()));
				
				String receiveString = reader.readLine();
				
				System.out.println(receiveString);
				
				StringTokenizer token = new StringTokenizer(receiveString, "/");
				
				if(token.countTokens() > 2)
					insert.insert(receiveString);
				else
					login.login(receiveString);	
			}
			
		//	reader.close();
		//	s_socket.close();
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void setSocket(Socket socket) {
		s_socket = socket;
	}
}
