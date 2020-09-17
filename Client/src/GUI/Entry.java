package GUI;

import DB.*;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Label;

/*
 * 건물에서 입장을 관리하는 클래스
 * 
 * */
public class Entry {
	UserSQL userSql = new UserSQL();

	JFrame frame;
	JTable table;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Entry window = new Entry();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Entry() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Project");
		frame.setBounds(100, 100, 550, 520);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel table_panel = new JPanel();
		table_panel.setBounds(0, 0, 546, 492);
		frame.getContentPane().add(table_panel);
		
		String[] headers = new String [] {"학번", "이름", "학과", "ID", "입장시간"};
		DefaultTableModel model = new DefaultTableModel(headers, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		table = new JTable(model);
		table.setRowHeight(30);
		table.setFont(new Font("굴림", Font.PLAIN, 15));
		table.setAlignmentX(0);
		table.setEnabled(true); 	//셀 편집 가능 여부
		table.getTableHeader().setReorderingAllowed(false);	//컬럼 순서 변경 여부
		table_panel.setLayout(null);
		table.setAutoCreateRowSorter(true);	//정렬
		table.setSize(450,450);
		table.setPreferredScrollableViewportSize(new Dimension(450, 450));
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(8, 87, 522, 395);
		table_panel.add(scrollPane);
		frame.getContentPane().add(table_panel);
		
		Label label = new Label("New label");
		label.setBounds(8, 10, 98, 54);
		table_panel.add(label);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 0, 546, 492);
		frame.getContentPane().add(panel_1);
		
		
/*		try {
			ResultSet r = null;
			CONNECT connect = new CONNECT();
			Connection conn = connect.getDB();
			Statement stmt = conn.createStatement();
			
			
			r = stmt.executeQuery("SELECT Num, Name, DP, ID FROM User");
			
			ResultSetMetaData resultSetMetaData = r.getMetaData();
			Object[] tempObject = new Object[resultSetMetaData.getColumnCount()];
			model.setRowCount(0);
			while (r.next()) {
				for(int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
					tempObject[i] = r.getString(i+1);
				}
				model.addRow(tempObject);
			}
			if(model.getRowCount() > 0 ) {
				table.setRowSelectionInterval(0, 0);
			}
		} catch(Exception e) {
			System.out.println("연결 오류 " + e.getStackTrace());
		} finally {
			userSql.closeDatabase();
		}*/

	}
}
