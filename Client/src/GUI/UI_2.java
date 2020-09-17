package GUI;

import DB.*;

import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
/*
 * 회원 정보를 관리하는  클래스
 * 추가, 변경, 삭제, 검색
 * */

public class UI_2 {
	
	UserSQL userSql = new UserSQL();
	
	JFrame frame;
	JPanel table_panel;
	JPanel add_panel;
	JTable table;
	DefaultTableModel model;
	JTextField addNum_textField;
	JTextField addName_textField;
	JTextField addDp_textField;
	JTextField addId_textField;
	JTextField addPw_textField;
	JTextField search_textField;
	JComboBox<String> comboBox;
	JButton search_Button;
	JButton idCheck_Button;
	JDialog dialog = new JDialog(frame);
	
	public final int SEARCH_NUM = 0;
	public final int SEARCH_NAME = 1;
	public final int SEARCH_DP = 2;
	public final int SEARCH_ID = 3;
	public final int SEARCH_PW = 4;
	public final int SEARCH_ALL = 5;
	public final int SEARCH_NONE = 6;
	
	public final int NEW_MODE = 1;
	public final int EDIT_MODE = 2;
	int dialogMode = NEW_MODE;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI_2 window = new UI_2();
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
	public UI_2() {
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame();
		frame.setTitle("Project");
		frame.setBounds(100, 100, 550, 520);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		dialog.setSize(350, 250);
		dialog.setLocationRelativeTo(frame);
		dialog.setResizable(false);
		dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		table_panel = new JPanel();
		table_panel.setBounds(0, 0, 546, 492);
		table_panel.setLayout(null);
		table_panel.setVisible(true);
		
		String[] headers = new String [] {"학번", "이름", "학과", "ID", "PW"};
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
		table.setAutoCreateRowSorter(true);	//정렬
		table.setSize(450,450);
		table.setPreferredScrollableViewportSize(new Dimension(450, 450));
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(5, 35, 529, 409);
		table_panel.add(scrollPane);
		frame.getContentPane().add(table_panel);
		
		JButton search_Button = new JButton("검색");
		search_Button.setFont(new Font("굴림", Font.BOLD, 15));
		search_Button.setBackground(Color.LIGHT_GRAY);
		search_Button.setBounds(373, 451, 77, 31);
		table_panel.add(search_Button);
		
		JButton add_Button = new JButton("추가");
		add_Button.setForeground(Color.WHITE);
		add_Button.setFont(new Font("굴림", Font.BOLD, 15));
		add_Button.setBackground(new Color(0, 102, 153));
		add_Button.setBounds(245, 2, 77, 31);
		table_panel.add(add_Button);
		
		JButton edit_Button = new JButton("변경");
		edit_Button.setForeground(Color.WHITE);
		edit_Button.setFont(new Font("굴림", Font.BOLD, 15));
		edit_Button.setBackground(new Color(0, 102, 153));
		edit_Button.setBounds(351, 2, 77, 31);
		table_panel.add(edit_Button);
		
		JButton delete_Button = new JButton("삭제");
		delete_Button.setForeground(Color.WHITE);
		delete_Button.setFont(new Font("굴림", Font.BOLD, 15));
		delete_Button.setBackground(new Color(0, 102, 153));
		delete_Button.setBounds(457, 2, 77, 31);
		table_panel.add(delete_Button);
		
		search_textField = new JTextField();
		search_textField.setFont(new Font("굴림", Font.PLAIN, 14));
		search_textField.setBounds(162, 451, 183, 31);
		table_panel.add(search_textField);
		search_textField.setColumns(10);
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setFont(new Font("굴림", Font.PLAIN, 14));
		comboBox.setBounds(64, 452, 77, 28);
		comboBox.addItem("이름");
		comboBox.addItem("학번");
		comboBox.addItem("학과");
		comboBox.addItem("ID");
		comboBox.addItem("PW");
		comboBox.addItem("전체");
		table_panel.add(comboBox);
		
		JLabel title_Label = new JLabel("학생 목록");
		title_Label.setHorizontalAlignment(SwingConstants.CENTER);
		title_Label.setFont(new Font("굴림", Font.BOLD, 17));
		title_Label.setBounds(27, 2, 178, 32);
		table_panel.add(title_Label);
		
		add_panel = new JPanel();
		add_panel.setBounds(0, 0, 546, 492);
		frame.getContentPane().add(add_panel);
		add_panel.setLayout(null);
		
		JButton addBack_Button = new JButton("←");
		addBack_Button.setBounds(12, 10, 45, 36);
		addBack_Button.setBackground(Color.LIGHT_GRAY);
		addBack_Button.setFont(new Font("굴림", Font.PLAIN, 12));
		add_panel.add(addBack_Button);
		
		addName_textField = new JTextField();
		addName_textField.setBounds(193, 84, 216, 40);
		addName_textField.setHorizontalAlignment(SwingConstants.CENTER);
		addName_textField.setFont(new Font("굴림", Font.BOLD, 17));
		addName_textField.setColumns(10);
		add_panel.add(addName_textField);
		
		JLabel addName_Label = new JLabel("이름");
		addName_Label.setBounds(50, 86, 172, 36);
		addName_Label.setHorizontalAlignment(SwingConstants.CENTER);
		addName_Label.setFont(new Font("굴림", Font.BOLD, 17));
		add_panel.add(addName_Label);
		
		addNum_textField = new JTextField();
		addNum_textField.setBounds(193, 147, 216, 40);
		addNum_textField.setHorizontalAlignment(SwingConstants.CENTER);
		addNum_textField.setFont(new Font("굴림", Font.BOLD, 17));
		addNum_textField.setColumns(10);
		add_panel.add(addNum_textField);
		
		addDp_textField = new JTextField();
		addDp_textField.setBounds(193, 213, 216, 40);
		addDp_textField.setHorizontalAlignment(SwingConstants.CENTER);
		addDp_textField.setFont(new Font("굴림", Font.BOLD, 17));
		addDp_textField.setColumns(10);
		add_panel.add(addDp_textField);
		
		addId_textField = new JTextField();
		addId_textField.setBounds(193, 273, 216, 40);
		addId_textField.setHorizontalAlignment(SwingConstants.CENTER);
		addId_textField.setFont(new Font("굴림", Font.BOLD, 17));
		addId_textField.setColumns(10);
		add_panel.add(addId_textField);
		
		addPw_textField = new JTextField();
		addPw_textField.setBounds(193, 340, 216, 40);
		addPw_textField.setHorizontalAlignment(SwingConstants.CENTER);
		addPw_textField.setFont(new Font("굴림", Font.BOLD, 17));
		addPw_textField.setColumns(10);
		add_panel.add(addPw_textField);
		
		JLabel addNum_Label = new JLabel("학번");
		addNum_Label.setBounds(50, 147, 172, 40);
		addNum_Label.setHorizontalAlignment(SwingConstants.CENTER);
		addNum_Label.setFont(new Font("굴림", Font.BOLD, 17));
		add_panel.add(addNum_Label);
		
		JLabel addDp_Label = new JLabel("학과");
		addDp_Label.setBounds(50, 213, 172, 40);
		addDp_Label.setHorizontalAlignment(SwingConstants.CENTER);
		addDp_Label.setFont(new Font("굴림", Font.BOLD, 17));
		add_panel.add(addDp_Label);
		
		JLabel addId_Label = new JLabel("ID");
		addId_Label.setBounds(50, 273, 172, 40);
		addId_Label.setHorizontalAlignment(SwingConstants.CENTER);
		addId_Label.setFont(new Font("굴림", Font.BOLD, 17));
		add_panel.add(addId_Label);
		
		JLabel addPw_Label = new JLabel("PW");
		addPw_Label.setBounds(50, 340, 172, 40);
		addPw_Label.setHorizontalAlignment(SwingConstants.CENTER);
		addPw_Label.setFont(new Font("굴림", Font.BOLD, 17));
		add_panel.add(addPw_Label);
		
		JLabel addTitle_Label = new JLabel("정보 추가");
		addTitle_Label.setBounds(152, 10, 241, 36);
		addTitle_Label.setHorizontalAlignment(SwingConstants.CENTER);
		addTitle_Label.setFont(new Font("굴림", Font.PLAIN, 20));
		add_panel.add(addTitle_Label);
		
		JButton add_Button2 = new JButton("추가");
		add_Button2.setBounds(422, 411, 97, 55);
		add_Button2.setForeground(Color.WHITE);
		add_Button2.setBackground(new Color(0, 102, 153));
		add_Button2.setFont(new Font("굴림", Font.BOLD, 17));
		add_panel.add(add_Button2);
		
		JButton idCheck_Button = new JButton("중복 확인");
		idCheck_Button.setForeground(Color.WHITE);
		idCheck_Button.setFont(new Font("굴림", Font.BOLD, 14));
		idCheck_Button.setBackground(new Color(0, 102, 153));
		idCheck_Button.setBounds(421, 274, 97, 40);
		add_panel.add(idCheck_Button);
		
		add_panel.setVisible(false);
		
		//테이블 DB 불러오기
		try {
			ResultSet r = null;
			CONNECT connect = new CONNECT();
			Connection conn = connect.getDB();
			Statement stmt = conn.createStatement();
			
			r = stmt.executeQuery("SELECT * FROM User");
			
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
				table.setRowSelectionInterval(0, 0); //첫번째줄 포커싱
			}
		} catch(Exception e) {
			System.out.println("연결 오류 " + e.getStackTrace());
		} finally {
			userSql.closeDatabase();
		}
		
		
		idCheck_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//ID를 입력한 경우에만
				if(addId_textField.getText().length() > 0) {
					//아이디가 존재하는 경우
					if(userSql.idCheck(addId_textField.getText())) {
						JOptionPane.showMessageDialog(dialog, "사용할 수 없는 아이디입니다.");
						
						addId_textField.setText(null);
						addId_textField.requestFocus();
					}
					//없는 아이디인 경우
					else {
						JOptionPane.showMessageDialog(dialog, "사용 가능한 아이디입니다.");
					}
				}
				else {
					JOptionPane.showMessageDialog(dialog, "아이디를 입력해주세요.");
					addId_textField.requestFocus();
				}
			}
		});
		
		search_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(search_Button.getText().equals("검색")) {
					if(search_textField.getText().trim().length() > 0){
						switch(comboBox.getSelectedIndex()) {
						
						case SEARCH_NAME: //이름
							searchUser(SEARCH_NAME, search_textField.getText().trim());
							break;
							
						case SEARCH_NUM: //학번
							searchUser(SEARCH_NUM, search_textField.getText().trim());
							break;
							
						case SEARCH_DP: //학과
							searchUser(SEARCH_DP, search_textField.getText().trim());
							break;
							
						case SEARCH_ID: //아이디
							searchUser(SEARCH_ID, search_textField.getText().trim());
							break;
							
						case SEARCH_PW: //비밀번호
							searchUser(SEARCH_PW, search_textField.getText().trim());
							break;
							
						case SEARCH_ALL: //전체
							searchUser(SEARCH_ALL, search_textField.getText().trim());
							break;
						}
						search_Button.setText("취소");
					} else {
						JOptionPane.showMessageDialog(frame, "검색어를 입력해주세요.");
					}
				} else {
					search_Button.setText("검색");
					searchUser(SEARCH_NONE, null);
				}
			}
			
			/*
			 * 유저 검색 및 출력
			 * searchMode(검색모드)에 따라 출력
			 * 또는 keyword(검색어)와 일치하는 유저 출력
			 * */
			public void searchUser(int searchMode, String keyWord) {
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
							search_textField.setText(null);
							comboBox.setSelectedIndex(0);
						break;
					}
					
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
				}
				catch (SQLException e) {
					System.out.println("연결 오류 " +  e.getStackTrace());
				} finally {
					userSql.closeDatabase();
				}
			}
		});
		
		delete_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRowCount() > 0) {
					
					if(JOptionPane.showConfirmDialog(table_panel, "선택된 컬럼들을 삭제할까요?", "삭제 확인", 0) == 0) {
						for(int i : table.getSelectedRows()) {
							userSql.deleteUser(String.valueOf(model.getValueAt(i, 0)));
						}
						model.fireTableDataChanged();
//						searchUser(SEARCH_NONE, null);
						
						try {
							Statement stmt = conn.createStatement();
							r = stmt.executeQuery("SELECT * FROM User");
							search_textField.setText(null);
							comboBox.setSelectedIndex(0);
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
						}
						catch (SQLException e1) {
							System.out.println("연결 오류 " +  e1.getStackTrace());
						} finally {
							userSql.closeDatabase();
						}
					}
				}
				else {
					JOptionPane.showMessageDialog(frame, "선택된 컬럼이 없습니다.");
				}
			}
			
		});
		
		edit_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				switch(table.getSelectedRowCount()) {
				
				case 0:
					JOptionPane.showMessageDialog(frame, "선택된 유저가 없습니다.");
					break;
				
				case 1:
					int num = Integer.valueOf((String)model.getValueAt(table.getSelectedRow(), 0));
					String name = String.valueOf(model.getValueAt(table.getSelectedRow(), 1));
					String dp = String.valueOf(model.getValueAt(table.getSelectedRow(), 2));
					String id = String.valueOf(model.getValueAt(table.getSelectedRow(), 3));
					String pw = String.valueOf(model.getValueAt(table.getSelectedRow(), 4));
					
					table_panel.setVisible(false);
					add_panel.setVisible(true);
				
					addTitle_Label.setText("정보 수정");
					addNum_textField.setText(String.valueOf(num));
					addName_textField.setText(name);
					addId_textField.setText(id);
					addDp_textField.setText(dp);
					addPw_textField.setText(pw);
					add_Button2.setText("수정");

					break;
				
				default:
					JOptionPane.showMessageDialog(frame, "하나의 유저만 선택해주세요.");
					break;
				}
			}
		});
		
		add_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				add_panel.setVisible(true);
				table_panel.setVisible(false);
				addTitle_Label.setText("정보 추가");
				addNum_textField.setText(null);
				addName_textField.setText(null);
				addId_textField.setText(null);
				addDp_textField.setText(null);
				addPw_textField.setText(null);
				add_Button2.setText("추가");
			}
		});
		
		addBack_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				add_panel.setVisible(false);
				table_panel.setVisible(true);
			}
		});
		
		add_Button2.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				addEditUser();
			}			
		});
		
		
	}
	public boolean isNumber(String s) {
		try	{
			//숫자로 바뀌는데 오류가 없으면 true
			Integer.valueOf(s);
			return true;
		}
		catch(NumberFormatException e) {
			//오류가 생기면 숫자가 아니므로 false
			return false;
		}
	}
	
	ResultSet r = null;
	CONNECT connect = new CONNECT();
	Connection conn = connect.getDB();
	
	public void addEditUser() {
		if(addId_textField.getText().trim().length() == 0){
			JOptionPane.showMessageDialog(dialog, "아이디를 입력해주세요.");
			addId_textField.requestFocus();
		}
		else if(addId_textField.getText().trim().length() > 30) {
			JOptionPane.showMessageDialog(dialog, "아이디는 30자 이내로 입력해야 합니다.");
			addId_textField.requestFocus();
		}
		else if(dialogMode == NEW_MODE && userSql.idCheck(addId_textField.getText())) {
			JOptionPane.showMessageDialog(dialog, "사용할 수 없는 아이디입니다.");
			
			addId_textField.setText(null);
			addId_textField.requestFocus();
		}
		else if(addName_textField.getText().trim().length() == 0 ) {
			JOptionPane.showMessageDialog(dialog, "이름을 입력해주세요.");
			addName_textField.requestFocus();
		}
		else if(addName_textField.getText().trim().length() > 20) {
			JOptionPane.showMessageDialog(dialog, "이름 20자 이내로 입력해야 합니다.");
			addName_textField.requestFocus();
		}
		else if(addNum_textField.getText().trim().length() == 0 ) {
			JOptionPane.showMessageDialog(dialog, "학번을 입력해주세요.");
			addNum_textField.requestFocus();
		}
		else if(!isNumber(addNum_textField.getText().trim())) {
			JOptionPane.showMessageDialog(dialog, "학번을 숫자로 입력해주세요.");

			addNum_textField.setText(null);
			addNum_textField.requestFocus();
		}
		else if(addPw_textField.getText().trim().length() == 0 ) {
			JOptionPane.showMessageDialog(dialog, "비밀번호를 입력해주세요.");
			addPw_textField.requestFocus();
		}
		else if(addPw_textField.getText().trim().length() > 20 ) {
			JOptionPane.showMessageDialog(dialog, "비밀번호는 20자 이내로 입력해야 합니다.");
			addPw_textField.requestFocus();
		}
		
		//오류가 없는 경우
		else {
			if(userSql.addUser(dialogMode, Integer.valueOf(addNum_textField.getText()), addName_textField.getText().trim(), addDp_textField.getText().trim(), addId_textField.getText().trim(), addPw_textField.getText().trim())) {
				if(dialogMode == NEW_MODE) {
					JOptionPane.showMessageDialog(dialog, "추가 완료");
				}
				else {
					JOptionPane.showMessageDialog(dialog, "수정 완료");
				}
				add_panel.setVisible(false);
				table_panel.setVisible(true);
			}
			//없는 아이디인 경우
			else {
				JOptionPane.showMessageDialog(dialog, "입력 과정에서 오류 발생");							
			}
		}
	}
}
