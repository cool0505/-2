package Gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;

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
import java.awt.Toolkit;

/*
 * 관리자 GUI
 * 학생 정보를 관리한다. (추가, 변경, 삭제)
 * 건물/셔틀버스의 출입기록을 관리한다.
 * */

public class UI_2 {
	JFrame frame;
	JPanel main_panel, where_panel, hall_panel, bus_panel, stud_panel, enter_panel, add_panel;
	JTable stud_table, enter_table;
	JLabel enter_title_Label, editNum_textLabel;
	JTextField addNum_textField, addName_textField, addId_textField, addPw_textField, search_textField;
	JComboBox<String> comboBox;
	JButton search_Button;
	JDialog dialog = new JDialog(frame);
	
	
	static String[][] stud_contents;
	static String[][] enter_contents;
	static String[] tokens2;
	static String[] tokens;
	
	String data = "/-";
	StringTokenizer token1 = new StringTokenizer(data, "-");
	String[] tokens1 = data.split("-");
	
	/*
	 * 관리자가 입력한 값
	 * 학번 = UserNum
	 * 이름 = UserName
	 * 아이디 = UserID
	 * 비밀번호 = UserPW
	 * 검색한 내용 = UserSearch
	 * 
	 * UI_2에서 필요한 값
	 * 추가 결과 = AddResult (성공 1 / 실패  0)
	 * 변경 결과 = EditResult (성공 1 / 실패 0)
	 * 삭제 결과 = DelResult (성공 1 / 실패 0)
	 * 
	 * */
	
	String UserNum = null;
	String UserName = null;
	String UserId = null;
	String UserPw = null;
	String UserSearch = null;
	String AddResult = "0";
	String EditResult = "0";
	String DelResult = "0";

	
	public final int SEARCH_NUM = 0;
	public final int SEARCH_NAME = 1;
	public final int SEARCH_ID = 2;
	public final int SEARCH_PW = 3;
	public final int SEARCH_ALL = 4;
	public final int SEARCH_NONE = 5;
	
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
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\User\\eclipse-workspace\\Project\\Logo.JPG"));
		frame.setTitle("UNIV-PASS");
		frame.setBounds(100, 100, 550, 520);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//다이얼로그 설정
		dialog.setSize(350, 250);
		dialog.setLocationRelativeTo(frame);
		dialog.setResizable(false);
		dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		
		/*
		 * 셔틀버스를 선택하는 페이지 (bus_panel)
		 * 아산역, 천안역 버튼 중에서 선택한다.
		 * */
		bus_panel = new JPanel();
		bus_panel.setBounds(0, 0, 546, 492);
		frame.getContentPane().add(bus_panel);
		bus_panel.setLayout(null);
		
		JButton Onyang_Button = new JButton("온양역");
		Onyang_Button.setForeground(Color.WHITE);
		Onyang_Button.setFont(new Font("굴림", Font.BOLD, 28));
		Onyang_Button.setBackground(new Color(0, 102, 153));
		Onyang_Button.setBounds(161, 336, 223, 92);
		bus_panel.add(Onyang_Button);
		
		JButton Cheonan_Asan_Button = new JButton("천안아산역");
		Cheonan_Asan_Button.setForeground(Color.WHITE);
		Cheonan_Asan_Button.setFont(new Font("굴림", Font.BOLD, 28));
		Cheonan_Asan_Button.setBackground(new Color(0, 102, 153));
		Cheonan_Asan_Button.setBounds(161, 62, 223, 92);
		bus_panel.add(Cheonan_Asan_Button);
		
		JButton bus_back_Button = new JButton("←");
		bus_back_Button.setFont(new Font("굴림", Font.BOLD, 10));
		bus_back_Button.setBackground(new Color(220, 220, 220));
		bus_back_Button.setBounds(3, 3, 47, 32);
		bus_panel.add(bus_back_Button);
		
		JButton Terminal_Button = new JButton("천안터미널");
		Terminal_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		Terminal_Button.setForeground(Color.WHITE);
		Terminal_Button.setFont(new Font("굴림", Font.BOLD, 28));
		Terminal_Button.setBackground(new Color(0, 102, 153));
		Terminal_Button.setBounds(161, 200, 223, 92);
		bus_panel.add(Terminal_Button);
		bus_panel.setVisible(false);
		
		/*
		 * 관리자 페이지 (main_panel)
		 * 학생 관리, 출입 관리 중 선택한다.
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
		
/*		JButton end_Button = new JButton("종료");
		end_Button.setFont(new Font("굴림", Font.BOLD, 15));
		end_Button.setBackground(new Color(220, 220, 220));
		end_Button.setBounds(440, 436, 72, 41);
		main_panel.add(end_Button);
*/

		/*
		 * main_panel에서 wherePage_Button을 선택하면 출력될 페이지
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
		 * 건물 출입기록 페이지 (enter_panel)
		 * 테이블을 출력한다.
		 * 학번, 날짜, 출입 중 선택하여 검색한다.
		 * */
		enter_panel = new JPanel();
		enter_panel.setBounds(0, 0, 546, 492);
		frame.getContentPane().add(enter_panel);
		enter_panel.setLayout(null);
		
		JLabel enter_title_Label = new JLabel();
		enter_title_Label.setText("ㅇㅇ관");
		enter_title_Label.setHorizontalAlignment(SwingConstants.CENTER);
		enter_title_Label.setFont(new Font("굴림", Font.BOLD, 20));
		enter_title_Label.setBounds(13, 35, 127, 42);
		enter_panel.add(enter_title_Label);
		
		JButton enter_back_Button = new JButton("←");
		enter_back_Button.setFont(new Font("굴림", Font.BOLD, 10));
		enter_back_Button.setBackground(new Color(220, 220, 220));
		enter_back_Button.setBounds(3, 3, 47, 32);
		enter_panel.add(enter_back_Button);
		
		
		String[] enter_headers = new String [] {"학번", "출입", "날짜"};
		String enter_info = "2019243044/1/Sat Dec 12 19:14:47 KST 2020/-2019243111/1/Sat Dec 12 19:49:03 KST 2020/-";

		StringTokenizer token1 = new StringTokenizer(enter_info, "-");
		String[] tokens1 = enter_info.split("-");
		enter_contents = new String[tokens1.length][3];
		
		for(int i = 0; i < tokens1.length; i++) {
				tokens2 = tokens1[i].split("/");
				for(int j = 0; j < 3; j++) {
					if(j == 1) {
						if(tokens2[j].equals("1")) {
							enter_contents[i][j] = "PASS";
						} else if(tokens2[j].equals("0")) {
							enter_contents[i][j] = "FAIL";
						}
					} else {
						enter_contents[i][j] = tokens2[j];
					}
				}
		}
		
		enter_table = new JTable(enter_contents, enter_headers);
		enter_table.setRowHeight(30);
		enter_table.setFont(new Font("굴림", Font.PLAIN, 15));
		enter_table.setAlignmentX(0);
		enter_table.setEnabled(true);	 //셀 편집 가능 여부
		enter_table.getTableHeader().setReorderingAllowed(false);	//컬럼 순서 변경 여부
		enter_table.setAutoCreateRowSorter(true);	//정렬
		enter_table.setSize(450,450);
		enter_table.setPreferredScrollableViewportSize(new Dimension(450, 450));
		
		JScrollPane scrollPane2 = new JScrollPane(enter_table);
		scrollPane2.setBounds(5, 100, 529, 369);
		enter_panel.add(scrollPane2);
		frame.getContentPane().add(enter_panel);
		
		JButton enterSearch_Button = new JButton("검색");
		enterSearch_Button.setFont(new Font("굴림", Font.BOLD, 15));
		enterSearch_Button.setBackground(Color.LIGHT_GRAY);
		enterSearch_Button.setBounds(444, 42, 77, 31);
		enter_panel.add(enterSearch_Button);
		
		JTextField enterSearch_textField = new JTextField();
		enterSearch_textField.setFont(new Font("굴림", Font.PLAIN, 14));
		enterSearch_textField.setColumns(10);
		enterSearch_textField.setBounds(249, 42, 183, 31);
		enter_panel.add(enterSearch_textField);
		
		JComboBox<String> enter_comboBox = new JComboBox<String>();
		enter_comboBox.setFont(new Font("굴림", Font.PLAIN, 14));
		enter_comboBox.setBounds(160, 42, 77, 28);
		enter_comboBox.addItem("학번");
		enter_comboBox.addItem("출입");
		enter_comboBox.addItem("날짜");
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
		 * 학생 정보 관리 페이지 (stud_panel)
		 * 추가, 변경, 삭제 버튼 눌러 페이지 이동 후 관리 기능 수행
		 * */
		stud_panel = new JPanel();
		stud_panel.setBounds(0, 0, 546, 492);
		stud_panel.setLayout(null);
		
		String[] stud_headers = new String [] {"학번", "이름", "ID", "PW"};
		String stud_info_server="20190/고주원/idd/asdfa/-20191/김후정/id2/wefgfdg/-20192/신은진/id3/dsfgn/-";
		String stud_info = stud_info_server;

		token1 = new StringTokenizer(stud_info, "-");
		tokens1 = stud_info.split("-");
		stud_contents = new String[tokens1.length][4];
		
		for(int i = 0; i < tokens1.length; i++) {
				tokens2 = tokens1[i].split("/");
				for(int j = 0; j < 4; j++) {
					 stud_contents[i][j] = tokens2[j];
				}
		}
		
		stud_table = new JTable(stud_contents, stud_headers);
		stud_table.setRowHeight(30);
		stud_table.setFont(new Font("굴림", Font.PLAIN, 15));
		stud_table.setAlignmentX(0);
		stud_table.setEnabled(true);	 //셀 편집 가능 여부
		stud_table.getTableHeader().setReorderingAllowed(false);	//컬럼 순서 변경 여부
		stud_table.setAutoCreateRowSorter(true);	//정렬
		stud_table.setSize(450,450);
		stud_table.setPreferredScrollableViewportSize(new Dimension(450, 450));
		
		JScrollPane scrollPane = new JScrollPane(stud_table);
		scrollPane.setBounds(5, 35, 529, 409);
		stud_panel.add(scrollPane);
		frame.getContentPane().add(stud_panel);

		JButton search_Button = new JButton("검색");
		search_Button.setFont(new Font("굴림", Font.BOLD, 15));
		search_Button.setBackground(Color.LIGHT_GRAY);
		search_Button.setBounds(373, 451, 77, 31);
		stud_panel.add(search_Button);
		
		JButton add_Button = new JButton("추가");
		add_Button.setForeground(Color.WHITE);
		add_Button.setFont(new Font("굴림", Font.BOLD, 15));
		add_Button.setBackground(new Color(0, 102, 153));
		add_Button.setBounds(245, 2, 77, 31);
		stud_panel.add(add_Button);
		
		JButton edit_Button = new JButton("변경");
		edit_Button.setForeground(Color.WHITE);
		edit_Button.setFont(new Font("굴림", Font.BOLD, 15));
		edit_Button.setBackground(new Color(0, 102, 153));
		edit_Button.setBounds(351, 2, 77, 31);
		stud_panel.add(edit_Button);
		
		JButton delete_Button = new JButton("삭제");
		delete_Button.setForeground(Color.WHITE);
		delete_Button.setFont(new Font("굴림", Font.BOLD, 15));
		delete_Button.setBackground(new Color(0, 102, 153));
		delete_Button.setBounds(457, 2, 77, 31);
		stud_panel.add(delete_Button);
		
		search_textField = new JTextField();
		search_textField.setFont(new Font("굴림", Font.PLAIN, 14));
		search_textField.setBounds(162, 451, 183, 31);
		stud_panel.add(search_textField);
		search_textField.setColumns(10);
		
		JComboBox<String> table_comboBox = new JComboBox<String>();
		table_comboBox.setFont(new Font("굴림", Font.PLAIN, 14));
		table_comboBox.setBounds(64, 452, 77, 28);
		table_comboBox.addItem("학번");
		table_comboBox.addItem("이름");
		table_comboBox.addItem("ID");
		table_comboBox.addItem("PW");
		table_comboBox.addItem("전체");
		stud_panel.add(table_comboBox);
		
		JLabel table_title_Label = new JLabel("학생 관리");
		table_title_Label.setHorizontalAlignment(SwingConstants.CENTER);
		table_title_Label.setFont(new Font("굴림", Font.BOLD, 17));
		table_title_Label.setBounds(27, 2, 178, 32);
		stud_panel.add(table_title_Label);
		
		JButton table_back_Button = new JButton("←");
		table_back_Button.setFont(new Font("굴림", Font.BOLD, 10));
		table_back_Button.setBackground(new Color(220, 220, 220));
		table_back_Button.setBounds(3, 3, 47, 32);
		stud_panel.add(table_back_Button);
		stud_panel.setVisible(false);
		
		/*
		 * 학생 정보를 추가하는 페이지 (add_panel)
		 * */
		add_panel = new JPanel();
		add_panel.setBounds(0, 0, 546, 492);
		frame.getContentPane().add(add_panel);
		add_panel.setLayout(null);
		
		JLabel addTitle_Label = new JLabel("정보 추가");
		addTitle_Label.setBounds(152, 10, 241, 36);
		addTitle_Label.setHorizontalAlignment(SwingConstants.CENTER);
		addTitle_Label.setFont(new Font("굴림", Font.PLAIN, 20));
		add_panel.add(addTitle_Label);
		
		JLabel addName_Label = new JLabel("이름");
		addName_Label.setBounds(50, 85, 172, 36);
		addName_Label.setHorizontalAlignment(SwingConstants.CENTER);
		addName_Label.setFont(new Font("굴림", Font.BOLD, 17));
		add_panel.add(addName_Label);
		
		addName_textField = new JTextField();
		addName_textField.setBounds(193, 85, 216, 40);
		addName_textField.setHorizontalAlignment(SwingConstants.CENTER);
		addName_textField.setFont(new Font("굴림", Font.BOLD, 17));
		addName_textField.setColumns(10);
		add_panel.add(addName_textField);

		JLabel addNum_Label = new JLabel("학번");
		addNum_Label.setBounds(50, 170, 172, 40);
		addNum_Label.setHorizontalAlignment(SwingConstants.CENTER);
		addNum_Label.setFont(new Font("굴림", Font.BOLD, 17));
		add_panel.add(addNum_Label);
		
		//변경 모드일 경우에만 보일 학번 라벨
		editNum_textLabel = new JLabel();
		editNum_textLabel.setBounds(193, 170, 216, 40);
		editNum_textLabel.setHorizontalAlignment(SwingConstants.CENTER);
		editNum_textLabel.setFont(new Font("굴림", Font.BOLD, 17));
		add_panel.add(editNum_textLabel);
		editNum_textLabel.setVisible(false);
		
		addNum_textField = new JTextField();
		addNum_textField.setBounds(193, 170, 216, 40);
		addNum_textField.setHorizontalAlignment(SwingConstants.CENTER);
		addNum_textField.setFont(new Font("굴림", Font.BOLD, 17));
		addNum_textField.setColumns(10);
		add_panel.add(addNum_textField);

		JLabel addId_Label = new JLabel("ID");
		addId_Label.setBounds(50, 255, 172, 40);
		addId_Label.setHorizontalAlignment(SwingConstants.CENTER);
		addId_Label.setFont(new Font("굴림", Font.BOLD, 17));
		add_panel.add(addId_Label);
		
		addId_textField = new JTextField();
		addId_textField.setBounds(193, 255, 216, 40);
		addId_textField.setHorizontalAlignment(SwingConstants.CENTER);
		addId_textField.setFont(new Font("굴림", Font.BOLD, 17));
		addId_textField.setColumns(10);
		add_panel.add(addId_textField);
		
		JLabel addPw_Label = new JLabel("PW");
		addPw_Label.setBounds(50, 340, 172, 40);
		addPw_Label.setHorizontalAlignment(SwingConstants.CENTER);
		addPw_Label.setFont(new Font("굴림", Font.BOLD, 17));
		add_panel.add(addPw_Label);
	
		addPw_textField = new JTextField();
		addPw_textField.setBounds(193, 340, 216, 40);
		addPw_textField.setHorizontalAlignment(SwingConstants.CENTER);
		addPw_textField.setFont(new Font("굴림", Font.BOLD, 17));
		addPw_textField.setColumns(10);
		add_panel.add(addPw_textField);
		
		JButton add_Button2 = new JButton("추가");
		add_Button2.setBounds(422, 411, 97, 55);
		add_Button2.setForeground(Color.WHITE);
		add_Button2.setBackground(new Color(0, 102, 153));
		add_Button2.setFont(new Font("굴림", Font.BOLD, 17));
		add_panel.add(add_Button2);
		
		JButton addBack_Button = new JButton("←");
		addBack_Button.setBounds(12, 10, 45, 36);
		addBack_Button.setBackground(Color.LIGHT_GRAY);
		addBack_Button.setFont(new Font("굴림", Font.PLAIN, 12));
		add_panel.add(addBack_Button);
		add_panel.setVisible(false);
		
		//검색 버튼을 선택한 경우
		search_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(search_Button.getText().equals("검색")) {
					if(search_textField.getText().trim().length() > 0){
							UserSearch = search_textField.getText().trim();	//입력한 내용을 앞뒤 공백 제거하고 UserSearch 변수에 넣어줌
							
						switch(table_comboBox.getSelectedIndex()) {
						
						case SEARCH_NAME: //이름 검색모드
							//[서버] - 이름 검색
							break;
							
						case SEARCH_NUM: //학번 검색모드
							//[서버] - 학번 검색
							break;
							
						case SEARCH_ID: //아이디 검색모드
							//[서버] - 아이디 검색
							break;
							
						case SEARCH_PW: //비밀번호 검색모드
							//[서버] - 비밀번호 검색
							break;
							
						case SEARCH_ALL: //전체 검색모드
							//[서버] - 전체 검색
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
			 * 학생 검색 및 출력
			 * searchMode(검색모드)에 따라
			 * 또는 keyword(검색어)와 일치하는 학생을 테이블에 출력한다.
			 * */
			public void searchUser(int searchMode, String keyWord) {
				
					switch(searchMode){
						//이름
						case SEARCH_NAME:
							//[서버] - 이름검색
						break;

						//아이디
						case SEARCH_ID:
							//[서버] - 아이디검색
						break;
						
						//전체
						case SEARCH_ALL:
							//[서버] - 전체검색
						break;
						
						//검색 안함
						case SEARCH_NONE:
							search_textField.setText(null);		//검색 내용을 비운다.
							table_comboBox.setSelectedIndex(0);	//테이블 인덱스 위치를 맨 위로 옮긴다.
						break;
					}
					
					if(stud_table.getRowCount() > 0 ) {
						stud_table.setRowSelectionInterval(0, 0);
					}
				}
			});
		
		//테이블에서 학생을 지정하고 삭제 버튼 선택했을 경우
		delete_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(stud_table.getSelectedRowCount() > 0) {
					
					if(JOptionPane.showConfirmDialog(stud_panel, "해당 학생의 정보를 삭제하시겠습니까?", "삭제 확인", 0) == 0) {
						for(int i : stud_table.getSelectedRows()) {	//선택한 학생의 row값을 int로 반환
							UserNum = String.valueOf(stud_table.getValueAt(i, 0));
							UserName = String.valueOf(stud_table.getValueAt(i, 1));
							UserId = String.valueOf(stud_table.getValueAt(i, 2));
							UserPw = String.valueOf(stud_table.getValueAt(i, 3));
							
							System.out.println(UserNum + "/" + UserName + "/" + UserId + "/" + UserPw);
							
							//[서버] - 학생 삭제 기능 구현
							//userSql.deleteUser(String.valueOf(model.getValueAt(i, 0)));
							
							
							if(DelResult.equals("1")) {
								JOptionPane.showMessageDialog(frame, "삭제되었습니다.");
							} else {
								JOptionPane.showMessageDialog(frame, "삭제에 실패했습니다.");
							}
						}
						
//						stud_table.fireTableDataChanged();	//테이블 데이터값 갱신

					}
				}
				else {
					JOptionPane.showMessageDialog(frame, "선택된 학생이 없습니다.");
				}
			}
			
		});
		
		//테이블에서 학생을 지정하고 변경 버튼 선택했을 경우
		edit_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				switch(stud_table.getSelectedRowCount()) {
				
				case 0:
					JOptionPane.showMessageDialog(frame, "선택된 학생이 없습니다.");
					break;
				
				case 1:
					addTitle_Label.setText("정보 변경");
					addNum_textField.setVisible(false);
					editNum_textLabel.setVisible(true);
					
					//지정한 학생의 데이터값을 불러온다.
					UserNum = String.valueOf(stud_table.getValueAt(stud_table.getSelectedRow(), 0));
					UserName = String.valueOf(stud_table.getValueAt(stud_table.getSelectedRow(), 1));
					UserId = String.valueOf(stud_table.getValueAt(stud_table.getSelectedRow(), 2));
					UserPw = String.valueOf(stud_table.getValueAt(stud_table.getSelectedRow(), 3));
					
					stud_panel.setVisible(false);
					add_panel.setVisible(true);
					
					editNum_textLabel.setText(UserNum);
					addName_textField.setText(UserName);
					addId_textField.setText(UserId);
					addPw_textField.setText(UserPw);
					add_Button2.setText("변경");
					break;
				
				default:
					JOptionPane.showMessageDialog(frame, "하나의 학생만 선택해주세요.");
					break;
				}
			}
		});
		
		//추가 버튼 선택했을 경우
		add_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				add_panel.setVisible(true);
				stud_panel.setVisible(false);
				addTitle_Label.setText("정보 추가");
				
				try {
					addNum_textField.setText(null);
					addName_textField.setText(null);
					addId_textField.setText(null);
					addPw_textField.setText(null);
				} catch (NullPointerException e1) { }
				add_Button2.setText("추가");
			}
		});
		
		addBack_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				add_panel.setVisible(false);
				stud_panel.setVisible(true);
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
				stud_panel.setVisible(true);
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
				stud_panel.setVisible(false);
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
				if(enter_title_Label.getText().equals("천안아산역") || enter_title_Label.getText().equals("천안터미널") || enter_title_Label.getText().equals("온양역")) {
					enter_panel.setVisible(false);
					bus_panel.setVisible(true);
					
				} else {
					enter_panel.setVisible(false);
					hall_panel.setVisible(true);
					
				}
			}
		});
		
		Onyang_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				enter_title_Label.setText("온양역");
				busButton();
			}
		});
		
		Cheonan_Asan_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				enter_title_Label.setText("천안아산역");
				busButton();
			}
		});
		
		Terminal_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				enter_title_Label.setText("천안터미널");
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
		
		//건물/셔틀의 출입기록 검색 버튼
		enterSearch_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//패널을 같이 쓰기 때문에 라벨 텍스트를 가져와 셔틀 패널인지 검사한다.
				//셔틀 패널인 경우
				if(enter_title_Label.getText().equals("아산역") || enter_title_Label.getText().equals("천안역")) {
					if(enterSearch_Button.getText().equals("검색")) {
						if(enterSearch_textField.getText().trim().length() > 0){
								UserSearch = enterSearch_textField.getText().trim();
								
							switch(enter_comboBox.getSelectedIndex()) {
							
							case 0: //학번 검색모드
								//[서버] - 학번 검색 구현
								break;
								
							case 1: //날짜 검색모드
								//[서버] - 날짜 검색 구현
								break;
								
							case 2: //출입 검색모드
								//[서버] - 출입 검색 구현
								break;
								
							case 3: //전체 검색모드
								//[서버] - 전체 검색 구현
								break;
							}
							enterSearch_Button.setText("취소");
						} else {
							JOptionPane.showMessageDialog(frame, "검색어를 입력해주세요.");
						}
					} else {
						enterSearch_Button.setText("검색");
						enterSearch_textField.setText(null);	//검색 내용을 비운다.
						enter_comboBox.setSelectedIndex(0);		//테이블 인덱스 위치를 맨 위로 옮긴다.
					}
					if(stud_table.getRowCount() > 0 ) {
						stud_table.setRowSelectionInterval(0, 0);
					}
				}
				
				//건물 패널인 경우
				else {
					if(enterSearch_Button.getText().equals("검색")) {
						if(enterSearch_textField.getText().trim().length() > 0){
								UserSearch = enterSearch_textField.getText().trim();
								
							switch(enter_comboBox.getSelectedIndex()) {
							
							case 0: //학번 검색모드
								//[서버] - 학번 검색 구현
								break;
								
							case 1: //날짜 검색모드
								//[서버] - 날짜 검색 구현
								break;
								
							case 2: //출입 검색모드
								//[서버] - 출입 검색 구현
								break;
								
							case 3: //전체 검색모드
								//[서버] - 전체 검색 구현
								break;
							}
							enterSearch_Button.setText("취소");
						} else {
							JOptionPane.showMessageDialog(frame, "검색어를 입력해주세요.");
						}
					} else {
						enterSearch_Button.setText("검색");
						enterSearch_textField.setText(null);	//검색 내용을 비운다.
						enter_comboBox.setSelectedIndex(0);		//테이블 인덱스 위치를 맨 위로 옮긴다.
					}
					if(stud_table.getRowCount() > 0 ) {
						stud_table.setRowSelectionInterval(0, 0);
					}
				}
				
			}
		}); 
	}
	
	public void busButton() {
		bus_panel.setVisible(false);
		enter_panel.setVisible(true);
	}
	
	//건물 선택 페이지에서 출입기록 페이지를 보여주는 메소드
	public void hallButton() {			
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
	
	public void addEditUser() {
		if(addId_textField.getText().trim().length() == 0){
			JOptionPane.showMessageDialog(dialog, "아이디를 입력해주세요.");
			addId_textField.requestFocus();
		}
		else if(addId_textField.getText().trim().length() > 20) {
			JOptionPane.showMessageDialog(dialog, "아이디는 20자 이내로 입력해야 합니다.");
			addId_textField.requestFocus();
		}
		else if(addName_textField.getText().trim().length() == 0 ) {
			JOptionPane.showMessageDialog(dialog, "이름을 입력해주세요.");
			addName_textField.requestFocus();
		}
		else if(addName_textField.getText().trim().length() > 20) {
			JOptionPane.showMessageDialog(dialog, "이름은 20자 이내로 입력해야 합니다.");
			addName_textField.requestFocus();
		}
		else if(addPw_textField.getText().trim().length() == 0 ) {
			JOptionPane.showMessageDialog(dialog, "비밀번호를 입력해주세요.");
			addPw_textField.requestFocus();
		}
		else if(addPw_textField.getText().trim().length() > 20 ) {
			JOptionPane.showMessageDialog(dialog, "비밀번호는 20자 이내로 입력해야 합니다.");
			addPw_textField.requestFocus();
		}
		
		//학생 추가, 변경
		else {
//			if(userSql.addUser(dialogMode, Integer.valueOf(addNum_textField.getText()), addName_textField.getText().trim(),
//					addId_textField.getText().trim(), addPw_textField.getText().trim())) {
			
			//변수에 저장하는 부분
			UserNum = addNum_textField.getText().trim();
			UserName = addName_textField.getText().trim();
			UserId = addId_textField.getText().trim();
			UserPw = addPw_textField.getText().trim();
			
			//[서버] - 학생 추가, 변경 기능 구현
			/*
			 * 
			 * */
			
			if(AddResult.equals("1")) {
					JOptionPane.showMessageDialog(dialog, "추가 완료");
					add_panel.setVisible(false);
					stud_panel.setVisible(true);
			} else if(EditResult.equals("1")){
					JOptionPane.showMessageDialog(dialog, "변경 완료");
					add_panel.setVisible(false);
					stud_panel.setVisible(true);
			} else if(AddResult.equals("0")) {
					JOptionPane.showMessageDialog(dialog, "추가 실패");
			} else if(EditResult.equals("0")) {
					JOptionPane.showMessageDialog(dialog, "변경 실패");
			}

			AddResult = "0";
			EditResult = "0";
		}
		
	}
}