package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class send_server {//extends Thread {

	String message = null;
/*
	@Override
	public void run() {

		super.run();

		try {
			BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
			PrintWriter print = new PrintWriter(s_socket.getOutputStream());

			while (true) {

				// message = buffer.readLine();
				if (message != null) {
					// System.out.println(message);
					print.println(message);
					print.flush();
				}
			}
		} catch (IOException e) {
		}
	}
*/
	public void set_message(String message) {
		

		this.message = message;
		OutputStream os = null; 
		OutputStreamWriter osw = null; 
		BufferedWriter bw = null;

		System.out.println(message);
		try {

			PrintWriter print = new PrintWriter(Server.socket.getOutputStream());

			System.out.println(message);
			print.println(message);
			print.flush();
			/*os = Server.socket.getOutputStream(); 
			osw = new OutputStreamWriter(os); 
			bw = new BufferedWriter(osw); 
			bw.write(message);
			bw.flush();*/

		} catch (IOException e) {
		}
	}
}
