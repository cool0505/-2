package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

// main 클래스
public class Server {

	// 소켓 생성 -> 객체 생성해서 전달할 필요 없음 (Server.socket)
	static Socket socket;   // 주원

	public static void main(String[] args) {

		try {
			// 포트넘버 : 8282
			ServerSocket serversocket = new ServerSocket(8282);

			while (true) {
				// socket 데이터 받아오기
				socket = serversocket.accept();

				// thread로 동작하는 receive_server 클래스 동작
				receive_server receive = new receive_server();
				receive.setSocket(socket);

				receive.start();
			}
		} catch (IOException e) {
		}
	}
}
