package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import DB.CONNECT;
import DB.UserSQL;

/*
 * 관리자 인터페이스
 * */

public class UI_2 {
	UserSQL userSql = new UserSQL();
	
	JFrame frame;
	JPanel main_panel, where_panel, hall_panel, bus_panel, table_panel, enter_panel, add_panel;
	JTable table, table2;
	DefaultTableModel model, model2;	//학생 정보 테이블, 출입기록 테이블
	JLabel enter_title_Label;
	JTextField addNum_textField;
	JLabel addNum_textLabel;
	JTextField addName_textField;
	JTextField addId_textField;
	JTextField addPw_textField;
	JTextField search_textField;
	JComboBox<String> comboBox;
	JButton search_Button;
	JButton idCheck_Button;
	JDialog dialog = new JDialog(frame);
	BufferedImage img = null;
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
		//프레임 설정
		frame = new JFrame();
		frame.setTitle("UNIV-PASS");
		frame.setBounds(100, 100, 550, 520);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		//이미지 객체 생성
		try {
			img = ImageIO.read(new File("C:\\Users\\User\\eclipse-workspace\\Project\\img.jpg"));			
		} catch(IOException e) {
			
		}
		
		//다이얼로그 설정
		dialog.setSize(350, 250);
		dialog.setLocationRelativeTo(frame);
		dialog.setResizable(false);
		dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		/*
		 * 사용자 정보를 추가하는 페이지 (add_panel)
		 * */
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
		addName_textField.setBounds(193, 85, 216, 40);
		addName_textField.setHorizontalAlignment(SwingConstants.CENTER);
		addName_textField.setFont(new Font("굴림", Font.BOLD, 17));
		addName_textField.setColumns(10);
		add_panel.add(addName_textField);
		
		JLabel addName_Label = new JLabel("이름");
		addName_Label.setBounds(50, 85, 172, 36);
		addName_Label.setHorizontalAlignment(SwingConstants.CENTER);
		addName_Label.setFont(new Font("굴림", Font.BOLD, 17));
		add_panel.add(addName_Label);
		
/*		addNum_textField = new JTextField();
		addNum_textField.setBounds(193, 170, 216, 40);
		addNum_textField.setHorizontalAlignment(SwingConstants.CENTER);
		addNum_textField.setFont(new Font("굴림", Font.BOLD, 17));
		addNum_textField.setColumns(10);
		add_panel.add(addNum_textField);*/
		
		addNum_textLabel = new JLabel();
		addNum_textLabel.setBounds(193, 170, 216, 40);
		addNum_textLabel.setHorizontalAlignment(SwingConstants.CENTER);
		addNum_textLabel.setFont(new Font("굴림", Font.BOLD, 17));
		add_panel.add(addNum_textLabel);
		
		addId_textField = new JTextField();
		addId_textField.setBounds(193, 255, 216, 40);
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
		addNum_Label.setBounds(50, 170, 172, 40);
		addNum_Label.setHorizontalAlignment(SwingConstants.CENTER);
		addNum_Label.setFont(new Font("굴림", Font.BOLD, 17));
		add_panel.add(addNum_Label);
		
		JLabel addId_Label = new JLabel("ID");
		addId_Label.setBounds(50, 255, 172, 40);
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
		idCheck_Button.setBounds(421, 255, 97, 40);
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
		
		/*
		 * 관리자 페이지 (main_panel)
		 * 학생 관리, 출입 관리, 종료 버튼 중 선택한다.
		 * */
		main_panel = new JPanel();
		main_panel.setBounds(0, 0, 546, 492);
		frame.getContentPane().add(main_panel);
		main_panel.setLayout(null);
		
		JButton tablePage_Button = new JButton("학생 관리");
		tablePage_Button.setForeground(Color.WHITE);
		tablePage_Button.setFont(new Font("굴림", Font.BOLD, 28));
		tablePage_Button.setBackground(new Color(0, 102, 153));
		tablePage_Button.setBounds(161, 129, 223, 92);
		main_panel.add(tablePage_Button);
		
		JButton wherePage_Button = new JButton("출입 관리");
		wherePage_Button.setForeground(Color.WHITE);
		wherePage_Button.setFont(new Font("굴림", Font.BOLD, 28));
		wherePage_Button.setBackground(new Color(0, 102, 153));
		wherePage_Button.setBounds(161, 264, 223, 92);
		main_panel.add(wherePage_Button);
		
		JButton end_Button = new JButton("종료");
		end_Button.setFont(new Font("굴림", Font.BOLD, 15));
		end_Button.setBackground(new Color(220, 220, 220));
		end_Button.setBounds(440, 436, 72, 41);
		main_panel.add(end_Button);
		
		/*
		 * main_panel에서 HallPage_Button을 선택하면 출력될 페이지
		 * 건물, 셔틀버스 버튼을 선택한다.
		 * */
		where_panel = new JPanel();
		where_panel.setBounds(0, 0, 546, 492);
		frame.getContentPane().add(where_panel);
		where_panel.setLayout(null);
		
		JButton hallPage_Button = new JButton("건물");
		hallPage_Button.setForeground(Color.WHITE);
		hallPage_Button.setFont(new Font("굴림", Font.BOLD, 28));
		hallPage_Button.setBackground(new Color(0, 102, 153));
		hallPage_Button.setBounds(161, 129, 223, 92);
		where_panel.add(hallPage_Button);
		
		JButton busPage_Button = new JButton("셔틀버스");
		busPage_Button.setForeground(Color.WHITE);
		busPage_Button.setFont(new Font("굴림", Font.BOLD, 28));
		busPage_Button.setBackground(new Color(0, 102, 153));
		busPage_Button.setBounds(161, 264, 223, 92);
		where_panel.add(busPage_Button);
		
		JButton whereEnd_Button = new JButton("←");
		whereEnd_Button.setFont(new Font("굴림", Font.BOLD, 10));
		whereEnd_Button.setBackground(new Color(220, 220, 220));
		whereEnd_Button.setBounds(3, 3, 47, 32);
		where_panel.add(whereEnd_Button);
		where_panel.setVisible(false);

		
		/*
		 * 셔틀버스를 선택하는 페이지 (bus_panel)
		 * 아산역, 천안역 버튼 중에서 선택한다.
		 * */
		bus_panel = new JPanel();
		bus_panel.setBounds(0, 0, 546, 492);
		frame.getContentPane().add(bus_panel);
		bus_panel.setLayout(null);
		
		JButton Asan_Button = new JButton("아산역");
		Asan_Button.setForeground(Color.WHITE);
		Asan_Button.setFont(new Font("굴림", Font.BOLD, 28));
		Asan_Button.setBackground(new Color(0, 102, 153));
		Asan_Button.setBounds(161, 129, 223, 92);
		bus_panel.add(Asan_Button);
		
		JButton Cheonan_Button = new JButton("천안역");
		Cheonan_Button.setForeground(Color.WHITE);
		Cheonan_Button.setFont(new Font("굴림", Font.BOLD, 28));
		Cheonan_Button.setBackground(new Color(0, 102, 153));
		Cheonan_Button.setBounds(161, 264, 223, 92);
		bus_panel.add(Cheonan_Button);
		
		JButton bus_back_Button = new JButton("←");
		bus_back_Button.setFont(new Font("굴림", Font.BOLD, 10));
		bus_back_Button.setBackground(new Color(220, 220, 220));
		bus_back_Button.setBounds(3, 3, 47, 32);
		bus_panel.add(bus_back_Button);
		bus_panel.setVisible(false);
		
		/*
		 * 건물 출입기록 페이지 (enter_panel)
		 * 학번, 날짜, 출입 중 선택하여 검색한다.
		 * */
		enter_panel = new JPanel();
		enter_panel.setBounds(0, 0, 546, 492);
		frame.getContentPane().add(enter_panel);
		enter_panel.setLayout(null);
		
		JLabel enter_title_Label = new JLabel();
		enter_title_Label.setText("ㅇㅇ관");
		enter_title_Label.setHorizontalAlignment(SwingConstants.CENTER);
		enter_title_Label.setFont(new Font("굴림", Font.BOLD, 25));
		enter_title_Label.setBounds(13, 35, 127, 42);
		enter_panel.add(enter_title_Label);
		
		JButton enter_back_Button = new JButton("←");
		enter_back_Button.setFont(new Font("굴림", Font.BOLD, 10));
		enter_back_Button.setBackground(new Color(220, 220, 220));
		enter_back_Button.setBounds(3, 3, 47, 32);
		enter_panel.add(enter_back_Button);
		enter_panel.setVisible(false);
		
		String[] headers2 = new String [] {"학번", "날짜", "출입"};
		DefaultTableModel model2 = new DefaultTableModel(headers2, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		table2 = new JTable(model);
		table2.setRowHeight(30);
		table2.setFont(new Font("굴림", Font.PLAIN, 15));
		table2.setAlignmentX(0);
		table2.setEnabled(true); 	//셀 편집 가능 여부
		table2.getTableHeader().setReorderingAllowed(false);	//컬럼 순서 변경 여부
		table2.setAutoCreateRowSorter(true);	//정렬
		table2.setSize(450,450);
		table2.setPreferredScrollableViewportSize(new Dimension(450, 450));
		
		JScrollPane scrollPane2 = new JScrollPane(table2);
		scrollPane2.setBounds(5, 100, 529, 369);
		enter_panel.add(scrollPane2);
		frame.getContentPane().add(enter_panel);
		
		JButton search_Button_1 = new JButton("검색");
		search_Button_1.setFont(new Font("굴림", Font.BOLD, 15));
		search_Button_1.setBackground(Color.LIGHT_GRAY);
		search_Button_1.setBounds(444, 42, 77, 31);
		enter_panel.add(search_Button_1);
		
		textField = new JTextField();
		textField.setFont(new Font("굴림", Font.PLAIN, 14));
		textField.setColumns(10);
		textField.setBounds(249, 42, 183, 31);
		enter_panel.add(textField);
		
		JComboBox<String> enter_comboBox = new JComboBox<String>();
		enter_comboBox.setFont(new Font("굴림", Font.PLAIN, 14));
		enter_comboBox.setBounds(160, 42, 77, 28);
		enter_comboBox.addItem("학번");
		enter_comboBox.addItem("날짜");
		enter_comboBox.addItem("출입");
		enter_comboBox.addItem("전체");
		enter_panel.add(enter_comboBox);
		enter_panel.setVisible(false);
		
		
		/*
		 * 건물 출입기록 페이지로 가기 위해 버튼을 선택하는 페이지
		 * 본관, 공학관, 자연관, 인문관, 원화관, 보건관, 도서관, 학생회관, 스포츠관
		 * */
		hall_panel = new JPanel();
		hall_panel.setBounds(0, 0, 546, 492);
		frame.getContentPane().add(hall_panel);
		hall_panel.setLayout(null);
		String[] hall = {"공학관", "본관", "원화관", "자연관", "인문관", "보건관", "도서관", "학생회관", "스포츠관"};
		
		JLabel hall_title_Label = new JLabel("건물 선택");
		hall_title_Label.setHorizontalAlignment(SwingConstants.CENTER);
		hall_title_Label.setFont(new Font("굴림", Font.BOLD, 25));
		hall_title_Label.setBounds(60, 5, 178, 32);
		hall_panel.add(hall_title_Label);
		
		JButton Gong_Button = new JButton(hall[0]);
		Gong_Button.setForeground(Color.WHITE);
		Gong_Button.setFont(new Font("굴림", Font.BOLD, 20));
		Gong_Button.setBackground(new Color(0, 102, 153));
		Gong_Button.setBounds(44, 76, 128, 62);
		hall_panel.add(Gong_Button);
		
		JButton Bon_Button = new JButton(hall[1]);
		Bon_Button.setForeground(Color.WHITE);
		Bon_Button.setFont(new Font("굴림", Font.BOLD, 20));
		Bon_Button.setBackground(new Color(0, 102, 153));
		Bon_Button.setBounds(204, 76, 128, 62);
		hall_panel.add(Bon_Button);
		
		JButton Won_Button = new JButton(hall[2]);
		Won_Button.setForeground(Color.WHITE);
		Won_Button.setFont(new Font("굴림", Font.BOLD, 20));
		Won_Button.setBackground(new Color(0, 102, 153));
		Won_Button.setBounds(364, 76, 128, 62);
		hall_panel.add(Won_Button);
		
		JButton Ja_Button = new JButton(hall[3]);
		Ja_Button.setForeground(Color.WHITE);
		Ja_Button.setFont(new Font("굴림", Font.BOLD, 20));
		Ja_Button.setBackground(new Color(0, 102, 153));
		Ja_Button.setBounds(44, 214, 128, 62);
		hall_panel.add(Ja_Button);
		
		JButton In_Button = new JButton(hall[4]);
		In_Button.setForeground(Color.WHITE);
		In_Button.setFont(new Font("굴림", Font.BOLD, 20));
		In_Button.setBackground(new Color(0, 102, 153));
		In_Button.setBounds(204, 214, 128, 62);
		hall_panel.add(In_Button);
		
		JButton Bo_Button = new JButton(hall[5]);
		Bo_Button.setForeground(Color.WHITE);
		Bo_Button.setFont(new Font("굴림", Font.BOLD, 20));
		Bo_Button.setBackground(new Color(0, 102, 153));
		Bo_Button.setBounds(364, 214, 128, 62);
		hall_panel.add(Bo_Button);
		
		JButton Do_Button = new JButton(hall[6]);
		Do_Button.setForeground(Color.WHITE);
		Do_Button.setFont(new Font("굴림", Font.BOLD, 20));
		Do_Button.setBackground(new Color(0, 102, 153));
		Do_Button.setBounds(44, 352, 128, 62);
		hall_panel.add(Do_Button);
		
		JButton Hak_Button = new JButton(hall[7]);
		Hak_Button.setForeground(Color.WHITE);
		Hak_Button.setFont(new Font("굴림", Font.BOLD, 20));
		Hak_Button.setBackground(new Color(0, 102, 153));
		Hak_Button.setBounds(204, 352, 128, 62);
		hall_panel.add(Hak_Button);
		
		JButton Sp_Button = new JButton(hall[8]);
		Sp_Button.setForeground(Color.WHITE);
		Sp_Button.setFont(new Font("굴림", Font.BOLD, 20));
		Sp_Button.setBackground(new Color(0, 102, 153));
		Sp_Button.setBounds(364, 352, 128, 62);
		hall_panel.add(Sp_Button);
		
		JButton hall_back_Button = new JButton("←");
		hall_back_Button.setFont(new Font("굴림", Font.BOLD, 10));
		hall_back_Button.setBackground(new Color(220, 220, 220));
		hall_back_Button.setBounds(3, 3, 47, 32);
		hall_panel.add(hall_back_Button);
		hall_panel.setVisible(false);

		/*
		 * 사용자 정보 관리 페이지 (table_panel)
		 * 추가, 변경, 삭제 버튼 눌러 페이지 이동 후 관리 기능 수행
		 * */
		table_panel = new JPanel();
		table_panel.setBounds(0, 0, 546, 492);
		table_panel.setLayout(null);
		
		String[] headers = new String [] {"학번", "이름", "ID", "PW"};
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
		
		JComboBox<String> table_comboBox = new JComboBox<String>();
		table_comboBox.setFont(new Font("굴림", Font.PLAIN, 14));
		table_comboBox.setBounds(64, 452, 77, 28);
		table_comboBox.addItem("이름");
		table_comboBox.addItem("ID");
		table_comboBox.addItem("PW");
		table_comboBox.addItem("전체");
		table_panel.add(table_comboBox);
		
		JLabel table_title_Label = new JLabel("학생 관리");
		table_title_Label.setHorizontalAlignment(SwingConstants.CENTER);
		table_title_Label.setFont(new Font("굴림", Font.BOLD, 17));
		table_title_Label.setBounds(27, 2, 178, 32);
		table_panel.add(table_title_Label);
		
		JButton table_back_Button = new JButton("←");
		table_back_Button.setFont(new Font("굴림", Font.BOLD, 10));
		table_back_Button.setBackground(new Color(220, 220, 220));
		table_back_Button.setBounds(3, 3, 47, 32);
		table_panel.add(table_back_Button);
		table_panel.setVisible(false);
		

		
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
						switch(table_comboBox.getSelectedIndex()) {
						
						case SEARCH_NAME: //이름
							searchUser(SEARCH_NAME, search_textField.getText().trim());
							break;
							
						case SEARCH_NUM: //학번
							searchUser(SEARCH_NUM, search_textField.getText().trim());
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
							table_comboBox.setSelectedIndex(0);
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
					
					if(JOptionPane.showConfirmDialog(table_panel, "해당 사용자 정보를 삭제하시겠습니까?", "삭제 확인", 0) == 0) {
						for(int i : table.getSelectedRows()) {
							userSql.deleteUser(String.valueOf(model.getValueAt(i, 0)));
						}
						model.fireTableDataChanged();
//						searchUser(SEARCH_NONE, null);
						
						try {
							Statement stmt = conn.createStatement();
							r = stmt.executeQuery("SELECT * FROM User");
							search_textField.setText(null);
							table_comboBox.setSelectedIndex(0);
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
					String id = String.valueOf(model.getValueAt(table.getSelectedRow(), 2));
					String pw = String.valueOf(model.getValueAt(table.getSelectedRow(), 3));
					
					table_panel.setVisible(false);
					add_panel.setVisible(true);
				
					addTitle_Label.setText("정보 변경");

					addNum_Label.setText(String.valueOf(num));
					addName_textField.setText(name);
					addId_textField.setText(id);
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
				try {
					
				addNum_textField.setText(null);
				addName_textField.setText(null);
				addId_textField.setText(null);
				addPw_textField.setText(null);
				}catch (NullPointerException e1) {
					
				}
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
		
		tablePage_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				table_panel.setVisible(true);
				main_panel.setVisible(false);
			}
		});

		
		hallPage_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				hall_panel.setVisible(true);
				where_panel.setVisible(false);
			}
		});
		
		busPage_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				bus_panel.setVisible(true);
				where_panel.setVisible(false);
			}
		});
		
		whereEnd_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				where_panel.setVisible(false);
				main_panel.setVisible(true);
			}
		});
		
		
		wherePage_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				where_panel.setVisible(true);
				main_panel.setVisible(false);
			}
		});
		
		hall_back_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				hall_panel.setVisible(false);
				main_panel.setVisible(true);
			}
		});
		
		table_back_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				table_panel.setVisible(false);
				main_panel.setVisible(true);
			}
		});
		
		/*
		 * hall_panel에서 각 건물의 버튼을 눌렀을 때,
		 * 다음 페이지인 enter_panel의 enter_title_Label 텍스트를 변경한다.
		 * */
		Gong_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				enter_title_Label.setText(hall[0]);
				hallButton();
			}
		});
		
		Bon_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				enter_title_Label.setText(hall[1]);
				hallButton();
			}
		});
		
		Won_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				enter_title_Label.setText(hall[2]);
				hallButton();
			}
		});
		
		Ja_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				enter_title_Label.setText(hall[3]);
				hallButton();
			}
		});
		
		In_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				enter_title_Label.setText(hall[4]);
				hallButton();
			}
		});
		
		Bo_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				enter_title_Label.setText(hall[5]);
				hallButton();
			}
		});
		
		Do_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				enter_title_Label.setText(hall[6]);
				hallButton();
			}
		});
		
		Hak_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				enter_title_Label.setText(hall[7]);
				hallButton();
			}
		});
		
		Sp_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				enter_title_Label.setText(hall[8]);
				hallButton();
			}
		});
		
		//enter_panel의 액션리스너
		enter_back_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//건물
				if(enter_title_Label.getText().equals("아산역") || enter_title_Label.getText().equals("천안역")) {
					enter_panel.setVisible(false);
					bus_panel.setVisible(true);
				} else {
					enter_panel.setVisible(false);
					hall_panel.setVisible(true);
				}
			}
		});
		
		Asan_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				enter_title_Label.setText("아산역");
				busButton();
			}
		});
		
		Cheonan_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				enter_title_Label.setText("천안역");
				busButton();
			}
		});
		
		bus_back_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				where_panel.setVisible(true);
				bus_panel.setVisible(false);
			}
		});
	}
	
	public void busButton() {
		bus_panel.setVisible(false);
		enter_panel.setVisible(true);
	}
	
	public void hallButton() {			//건물 선택 페이지 -> 출입기록 페이지를 보여주는 메소드
		hall_panel.setVisible(false);
		enter_panel.setVisible(true);
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
	private JTextField textField;
	
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
		
		/* 학번은 변경 불가
		else if(addNum_textField.getText().trim().length() == 0 ) {
			JOptionPane.showMessageDialog(dialog, "학번을 입력해주세요.");
			addNum_textField.requestFocus();
		}
		else if(!isNumber(addNum_textField.getText().trim())) {
			JOptionPane.showMessageDialog(dialog, "학번을 숫자로 입력해주세요.");

			addNum_textField.setText(null);
			addNum_textField.requestFocus();
		}*/
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
			if(userSql.addUser(dialogMode, Integer.valueOf(addNum_textField.getText()), addName_textField.getText().trim(), addId_textField.getText().trim(), addPw_textField.getText().trim())) {
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
