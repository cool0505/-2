package DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserSQL {
	
	public final int SEARCH_NUM = 0;
	public final int SEARCH_NAME = 1;
	public final int SEARCH_DP = 2;
	public final int SEARCH_ID = 3;
	public final int SEARCH_PW = 4;
	public final int SEARCH_ALL = 5;
	public final int SEARCH_NONE = 6;
	
	public final int NEW_MODE = 1;
	public final int EDIT_MODE = 2;
	
	
	PreparedStatement ps;
	CONNECT connect = new CONNECT();
	Statement stmt = null;
	ResultSet r;
	Connection conn = connect.getDB();
	
	/*
	 * 회원가입 및 추가 시, 아이디 중복 체크
	 * */
	public boolean idCheck(String id) {
		int result = 0;

		try {
			Statement stmt = conn.createStatement();
			r = stmt.executeQuery("SELECT ID FROM User WHERE ID='"+ id +"'");
			if(r.next()) {
				result = Integer.valueOf(r.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDatabase();
		}
		return (result > 0) ? true : false;
	}
	
	public boolean addUser(int dialogMode, int num, String name, String dp, String id, String pw) {
		int result = 0;

		try {
			if(dialogMode == NEW_MODE) {
				ps = conn.prepareStatement("INSERT INTO User VALUES (?,?,?,?,?)");
				ps.setInt(1, num);
				ps.setString(2, name);
				ps.setString(3, dp);
				ps.setString(4, id);
				ps.setString(5, pw);
			} else {
				ps = conn.prepareStatement("UPDATE User SET Name=?, Num=?, DP=?, PW=?, ID=?");
				ps.setInt(1, num);
				ps.setString(2, name);
				ps.setString(3, dp);
				ps.setString(4, pw);
				ps.setString(5, id);
			}
			result = ps.executeUpdate();
			
		} catch (SQLException e){
			System.out.println("SQLException\n" + e.getStackTrace());
		} finally {
			closeDatabase();
		}
		return (result > 0) ? true : false;
	}
	
	/*
	 * 유저 삭제
	 * ID를 비교해서 삭제
	 * */
	public boolean deleteUser(String id) {
		int result = 0;
		
		try {
			ps = conn.prepareStatement("DELETE FROM User WHERE ID=?");
			ps.setString(1, id);
			result = ps.executeUpdate();
		} catch(Exception e) {
			System.out.println("Exception\n" + e.getStackTrace());
		} finally {
			closeDatabase();
		}
		return (result > 0) ? true : false;
	}
	
	

/*	public void searchUser(int searchMode, String keyWord) {
		UI_2 ui2 = new UI_2();
		try	{
			Statement stmt = conn.createStatement();
 
			switch(searchMode){
				//이름
				case SEARCH_NAME:
					r = stmt.executeQuery("SELECT * FROM User WHERE Name LIKE '%" + keyWord + "%'");
				break;

				//학번
				case SEARCH_NUM:
					r = stmt.executeQuery("SELECT * FROM User WHERE Num LIKE '%" + keyWord + "%'");
				break;
				
				//학과
				case SEARCH_DP:
					r = stmt.executeQuery("SELECT * FROM User WHERE DP LIKE '%" + keyWord + "%'");
				break;
				
				//아이디
				case SEARCH_ID:
					r = stmt.executeQuery("SELECT * FROM User WHERE ID LIKE '%" + keyWord + "%'");
				break;
				
				//전체
				case SEARCH_ALL:
					r = stmt.executeQuery("SELECT DISTINCT * FROM User WHERE Name LIKE '%" + keyWord + "%' OR Num LIKE '%" + keyWord + "%' OR DP LIKE '%" + keyWord + "%' OR ID LIKE '%" + keyWord + "%' OR PW LIKE '%" + keyWord + "%'");
				break;
				
				//검색 안함
				case SEARCH_NONE:
					r = stmt.executeQuery("SELECT * FROM User");
					ui2.search_textField.setText(null);
					ui2.search_Button.setText("검색");
					ui2.comboBox.setSelectedIndex(0);
				break;
			}
			
			ResultSetMetaData resultSetMetaData = r.getMetaData();

			Object[] tempObject = new Object[resultSetMetaData.getColumnCount()];
			
			// defaultTableModel 초기화
			ui2.defaultTableModel.setRowCount(0);
			while (r.next()) {
				for(int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
					tempObject[i] = r.getString(i+1);
				}
				ui2.defaultTableModel.addRow(tempObject);
			}
			if( ui2.defaultTableModel.getRowCount() > 0 ) {
				ui2.table.setRowSelectionInterval(0, 0); //첫번째줄 포커싱
			}
		}
		catch (SQLException e) {
			System.out.println("연결 오류\n" +  e.getStackTrace());
		} finally {
			closeDatabase();
		}
	}*/
	
	public void closeDatabase() {
		try	{
			if( conn != null ) conn.close();
			 
			if( stmt != null ) stmt.close();
			
			if( r != null) r.close();
		}
		catch (SQLException e){
			System.out.println(e.getStackTrace());
		}
	}
}
