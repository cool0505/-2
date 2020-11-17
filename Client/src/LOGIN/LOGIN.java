package LOGIN;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;



public class LOGIN {

	INPUT input = new INPUT();
	// ADDLIST alist = new ADDLIST();

	//
	Statement stmt = null;
	ResultSet r;
	//

	String id, pw;
	int x = 0;
	
	public void infoId(String id) {
		this.id = id;
	}
	public void infoPw(String pw) {
		this.pw = pw;
	}
	
	public int ex() {
		return x;
	}
	
	public void login() {
		//
		ArrayList<USER> list = new ArrayList<USER>();

		try {

			while (r.next()) {
				USER user = new USER();
				user.setName(r.getString("Name"));
				user.setID(r.getString("ID"));
				user.setPW(r.getString("PW"));
				list.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		//

		for (int i = 0; i < list.size(); i++) {
			if (id.equals(list.get(i).getID()) && pw.equals(list.get(i).getPW())) {
					x = 1;
			}
		}
	}
}
