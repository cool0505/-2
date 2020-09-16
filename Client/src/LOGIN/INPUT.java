package LOGIN;

import java.util.Scanner;

public class INPUT {

	Scanner sc = new Scanner(System.in);

	String getString() {

		String string;

		string = sc.nextLine();

		return string;
	}

	int getInteger() {

		int x;

		x = sc.nextInt();

		return x;
	}
}
