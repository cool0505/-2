package GUI;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;
import javax.swing.JTable;
import java.awt.Toolkit;

/*
 * 사용자 GUI
 * 출입기록 확인, 문진표 작성
 * 문진표 작성 시, 아니요 또는 의심 증상이 하나라도 있으면 출입 불가(pass = fail)
 * */

public class UI_3 {

	JFrame frame;
	JPanel main_panel, enter_panel, write_panel_1, write_panel_2;
	JTable table;
	
	static String[][] user_contents;
	static String[] tokens2;
	static String[] tokens;
	
	//출입 = pass (가능 1, 불가능 0)
	String pass = "0";
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI_3 window = new UI_3();
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
	public UI_3() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	private void initialize() {
		
		//프레임 설정
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\User\\eclipse-workspace\\Project\\Logo.JPG"));
		frame.setTitle("UNIV-PASS");
		frame.setBounds(100, 100, 380, 664);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		/*
		 * 메인 페이지 (main_panel)
		 * 출입기록, 문진표 작성, 종료 버튼 중 선택한다.
		 * */
		main_panel = new JPanel();
		main_panel.setBounds(0, 0, 376, 636);
		frame.getContentPane().add(main_panel);
		main_panel.setLayout(null);
		
		JButton enterPage_Button = new JButton("출입기록");
		enterPage_Button.setForeground(Color.WHITE);
		enterPage_Button.setFont(new Font("굴림", Font.BOLD, 28));
		enterPage_Button.setBackground(new Color(0, 102, 153));
		enterPage_Button.setBounds(76, 185, 223, 82);
		main_panel.add(enterPage_Button);
		
		JButton writePage_Button = new JButton("문진표 작성");
		writePage_Button.setForeground(Color.WHITE);
		writePage_Button.setFont(new Font("굴림", Font.BOLD, 28));
		writePage_Button.setBackground(new Color(0, 102, 153));
		writePage_Button.setBounds(76, 358, 223, 82);
		main_panel.add(writePage_Button);
		
		JButton end_Button = new JButton("종료");
		end_Button.setFont(new Font("굴림", Font.BOLD, 15));
		end_Button.setBackground(new Color(220, 220, 220));
		end_Button.setBounds(292, 585, 72, 41);
		main_panel.add(end_Button);
		
		JLabel pass_Label = new JLabel("PASS");
		pass_Label.setFont(new Font("굴림", Font.BOLD, 25));
		pass_Label.setHorizontalAlignment(SwingConstants.CENTER);
		pass_Label.setBounds(81, 65, 214, 66);
		main_panel.add(pass_Label);
		pass_Label.setVisible(false);
		
		/*
		 * 출입기록 페이지 (enter_panel)
		 * 날짜, 장소, 출입을 날짜 순으로 보여줌
		 * */
		enter_panel = new JPanel();
		enter_panel.setBounds(0, 0, 376, 636);
		frame.getContentPane().add(enter_panel);
		enter_panel.setLayout(null);
		
		JLabel enterTitle_Label = new JLabel("출입기록");
		enterTitle_Label.setHorizontalAlignment(SwingConstants.CENTER);
		enterTitle_Label.setFont(new Font("굴림", Font.BOLD, 20));
		enterTitle_Label.setBounds(15, 16, 349, 27);
		enter_panel.add(enterTitle_Label);
		
		String[] user_headers = new String [] {"건물", "출입", "날짜"};
		String user_info = "humanities/1/Sat Dec 12 19:14:47 KST 2020/-main/1/Sat Dec 12 19:49:03 KST 2020/-";

		StringTokenizer token1 = new StringTokenizer(user_info, "-");
		String[] tokens1 = user_info.split("-");
		user_contents = new String[tokens1.length][3];
		
		for(int i = 0; i < tokens1.length; i++) {
			tokens2 = tokens1[i].split("/");
			for(int j = 0; j < 3; j++) {
				if(j == 0) {
					switch (tokens2[j]) {
					case "main":
						user_contents[i][j] = "본관";
						break;
					
					case "engineering":
						user_contents[i][j] = "공학관";
						break;
						
					case "natural_science":
						user_contents[i][j] = "자연관";
						break;
						
					case "":
						user_contents[i][j] = "인문관";
						break;
						
					case "humanities":
						user_contents[i][j] = "원화관";
						break;
					
					case "health_care":
						user_contents[i][j] = "보건관";
						break;
						
					case "library":
						user_contents[i][j] = "도서관";
						break;
						
					case "student":
						user_contents[i][j] = "학생회관";
						break;	
					
					case "sports_science":
						user_contents[i][j] = "스포츠관";
						break;
					}
					
				} else if(j == 1) {
					if(tokens2[j].equals("1")) {
						user_contents[i][j] = "PASS";
					} else if(tokens2[j].equals("0")) {
						user_contents[i][j] = "FAIL";
					}
				} else {
					user_contents[i][j] = tokens2[j];
				}
			}
		}
		
		table = new JTable(user_contents, user_headers);
		table.setRowHeight(30);
		table.setFont(new Font("굴림", Font.PLAIN, 15));
		table.setAlignmentX(0);
		table.setEnabled(true); 	//셀 편집 가능 여부
		table.getTableHeader().setReorderingAllowed(false);	//컬럼 순서 변경 여부
		table.setAutoCreateRowSorter(true);	//정렬
		table.setSize(450,450);
		table.setPreferredScrollableViewportSize(new Dimension(450, 450));
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(5, 67, 359, 559);
		enter_panel.add(scrollPane);
		frame.getContentPane().add(enter_panel);
		
		JButton enterBack_Button = new JButton("←");
		enterBack_Button.setForeground(Color.WHITE);
		enterBack_Button.setFont(new Font("굴림", Font.BOLD, 15));
		enterBack_Button.setBackground(new Color(0, 102, 153));
		enterBack_Button.setBounds(314, 7, 50, 50);
		enter_panel.add(enterBack_Button);
		enter_panel.setVisible(false);
		
		/*
		 * 문진표 작성 페이지 (1)
		 * */
		write_panel_1 = new JPanel();
		write_panel_1.setBounds(0, 0, 376, 636);
		frame.getContentPane().add(write_panel_1);
		write_panel_1.setLayout(null);

		JLabel title_1 = new JLabel("문진표");
		title_1.setHorizontalAlignment(SwingConstants.CENTER);
		title_1.setFont(new Font("굴림", Font.BOLD, 20));
		title_1.setBounds(15, 16, 349, 27);
		write_panel_1.add(title_1);

		JLabel Label = new JLabel("개인정보 수집에 동의해주세요.");
		Label.setFont(new Font("굴림", Font.PLAIN, 13));
		Label.setBounds(12, 73, 349, 27);
		write_panel_1.add(Label);

		JRadioButton yes = new JRadioButton("네");
		yes.setFont(new Font("굴림", Font.PLAIN, 13));
		yes.setBounds(22, 99, 77, 23);
		write_panel_1.add(yes);

		JRadioButton no = new JRadioButton("아니요");
		no.setFont(new Font("굴림", Font.PLAIN, 13));
		no.setBounds(132, 99, 77, 23);
		write_panel_1.add(no);
		
		ButtonGroup group = new ButtonGroup();
		group.add(yes);
		group.add(no);
		
		JLabel Label_1 = new JLabel("1. 최근 14일 이내 코로나19 확진자의 이동 동선(지역, 병원,");
		Label_1.setBounds(12, 156, 349, 27);
		Label_1.setFont(new Font("굴림", Font.PLAIN, 13));
		write_panel_1.add(Label_1);

		JLabel Label_1_1 = new JLabel("   특정장소 등)에 노출된 적이 있나요?");
		Label_1_1.setFont(new Font("굴림", Font.PLAIN, 13));
		Label_1_1.setBounds(12, 180, 349, 27);
		write_panel_1.add(Label_1_1);

		JRadioButton yes_1 = new JRadioButton("네");
		yes_1.setFont(new Font("굴림", Font.PLAIN, 13));
		yes_1.setBounds(22, 209, 77, 23);
		write_panel_1.add(yes_1);

		JRadioButton no_1 = new JRadioButton("아니요");
		no_1.setFont(new Font("굴림", Font.PLAIN, 13));
		no_1.setBounds(132, 209, 77, 23);
		write_panel_1.add(no_1);
		
		ButtonGroup group_1 = new ButtonGroup();
		group_1.add(yes_1);
		group_1.add(no_1);
		
		JLabel Label_2 = new JLabel("2. 최근 14일 이내 가까운 친구/가족 등 지인이 코로나19 확진");
		Label_2.setFont(new Font("굴림", Font.PLAIN, 13));
		Label_2.setBounds(12, 267, 349, 27);
		write_panel_1.add(Label_2);

		JLabel Label_2_1 = new JLabel("   자로 판정받은 적이 있나요?");
		Label_2_1.setFont(new Font("굴림", Font.PLAIN, 13));
		Label_2_1.setBounds(12, 291, 349, 27);
		write_panel_1.add(Label_2_1);

		JRadioButton yes_2 = new JRadioButton("네");
		yes_2.setFont(new Font("굴림", Font.PLAIN, 13));
		yes_2.setBounds(22, 321, 77, 23);
		write_panel_1.add(yes_2);

		JRadioButton no_2 = new JRadioButton("아니요");
		no_2.setFont(new Font("굴림", Font.PLAIN, 13));
		no_2.setBounds(132, 321, 77, 23);
		write_panel_1.add(no_2);
		
		ButtonGroup group_2 = new ButtonGroup();
		group_2.add(yes_2);
		group_2.add(no_2);
		
		JLabel Label_3 = new JLabel("3. 최근 14일 이내 코로나19 검사(PCR 검사)를 받은 적이 있");
		Label_3.setFont(new Font("굴림", Font.PLAIN, 13));
		Label_3.setBounds(12, 378, 349, 27);
		write_panel_1.add(Label_3);

		JLabel Label_3_1 = new JLabel("   나요?");
		Label_3_1.setFont(new Font("굴림", Font.PLAIN, 13));
		Label_3_1.setBounds(12, 402, 349, 27);
		write_panel_1.add(Label_3_1);

		JRadioButton yes_3 = new JRadioButton("네");
		yes_3.setFont(new Font("굴림", Font.PLAIN, 13));
		yes_3.setBounds(22, 432, 77, 23);
		write_panel_1.add(yes_3);

		JRadioButton no_3 = new JRadioButton("아니요");
		no_3.setFont(new Font("굴림", Font.PLAIN, 13));
		no_3.setBounds(132, 432, 77, 23);
		write_panel_1.add(no_3);
		
		ButtonGroup group_3 = new ButtonGroup();
		group_3.add(yes_3);
		group_3.add(no_3);
		
		JLabel Label_4 = new JLabel("4. 최근 48시간 이내 해열제를 복용한 적이 있나요?");
		Label_4.setFont(new Font("굴림", Font.PLAIN, 13));
		Label_4.setBounds(12, 490, 349, 27);
		write_panel_1.add(Label_4);

		JRadioButton yes_4 = new JRadioButton("네");
		yes_4.setFont(new Font("굴림", Font.PLAIN, 13));
		yes_4.setBounds(16, 518, 77, 23);
		write_panel_1.add(yes_4);
		
		JRadioButton no_4 = new JRadioButton("아니요");
		no_4.setFont(new Font("굴림", Font.PLAIN, 13));
		no_4.setBounds(132, 518, 77, 23);
		write_panel_1.add(no_4);

		ButtonGroup group_4 = new ButtonGroup();
		group_4.add(yes_4);
		group_4.add(no_4);
		
		JButton writeBack_Button_1 = new JButton("←");
		writeBack_Button_1.setBounds(249, 573, 50, 50);
		writeBack_Button_1.setForeground(Color.WHITE);
		writeBack_Button_1.setFont(new Font("굴림", Font.BOLD, 15));
		writeBack_Button_1.setBackground(new Color(0, 102, 153));
		write_panel_1.add(writeBack_Button_1);

		JButton writeNext_Button_1 = new JButton("→");
		writeNext_Button_1.setBounds(311, 573, 50, 50);
		writeNext_Button_1.setForeground(Color.WHITE);
		writeNext_Button_1.setFont(new Font("굴림", Font.BOLD, 15));
		writeNext_Button_1.setBackground(new Color(0, 102, 153));
		write_panel_1.add(writeNext_Button_1);
		write_panel_1.setVisible(false);
		
		/*
		 * 문진표 작성 페이지 (2)
		 * */
		write_panel_2 = new JPanel();
		write_panel_2.setBounds(0, 0, 373, 633);
		frame.getContentPane().add(write_panel_2);
		write_panel_2.setLayout(null);
		
		JLabel title_2 = new JLabel("문진표");
		title_2.setHorizontalAlignment(SwingConstants.CENTER);
		title_2.setFont(new Font("굴림", Font.BOLD, 20));
		title_2.setBounds(15, 16, 349, 27);
		write_panel_2.add(title_2);
		
		JLabel Label_5 = new JLabel("5. 최근 3일 내에 다음의 증상이 있다면 체크해주세요.");
		Label_5.setFont(new Font("굴림", Font.PLAIN, 13));
		Label_5.setBounds(12, 73, 349, 27);
		write_panel_2.add(Label_5);

		JRadioButton RButton_1 = new JRadioButton("발열");
		RButton_1.setFont(new Font("굴림", Font.PLAIN, 13));
		RButton_1.setBounds(22, 100, 113, 23);
		write_panel_2.add(RButton_1);

		JRadioButton RButton_2 = new JRadioButton("오한");
		RButton_2.setFont(new Font("굴림", Font.PLAIN, 13));
		RButton_2.setBounds(22, 125, 113, 23);
		write_panel_2.add(RButton_2);

		JRadioButton RButton_3 = new JRadioButton("근육통");
		RButton_3.setFont(new Font("굴림", Font.PLAIN, 13));
		RButton_3.setBounds(22, 150, 113, 23);
		write_panel_2.add(RButton_3);

		JRadioButton RButton_4 = new JRadioButton("기침");
		RButton_4.setFont(new Font("굴림", Font.PLAIN, 13));
		RButton_4.setBounds(22, 175, 113, 23);
		write_panel_2.add(RButton_4);

		JRadioButton RButton_5 = new JRadioButton("가슴 답답 혹은 호흡 곤란");
		RButton_5.setFont(new Font("굴림", Font.PLAIN, 13));
		RButton_5.setBounds(22, 200, 211, 23);
		write_panel_2.add(RButton_5);

		JRadioButton RButton_6 = new JRadioButton("콧물");
		RButton_6.setFont(new Font("굴림", Font.PLAIN, 13));
		RButton_6.setBounds(22, 225, 113, 23);
		write_panel_2.add(RButton_6);

		JRadioButton RButton_7 = new JRadioButton("인후통");
		RButton_7.setFont(new Font("굴림", Font.PLAIN, 13));
		RButton_7.setBounds(22, 250, 113, 23);
		write_panel_2.add(RButton_7);

		JRadioButton RButton_8 = new JRadioButton("기타 감기 증상");
		RButton_8.setFont(new Font("굴림", Font.PLAIN, 13));
		RButton_8.setBounds(22, 277, 113, 23);
		write_panel_2.add(RButton_8);

		JRadioButton RButton_9 = new JRadioButton("메스꺼움");
		RButton_9.setFont(new Font("굴림", Font.PLAIN, 13));
		RButton_9.setBounds(22, 302, 113, 23);
		write_panel_2.add(RButton_9);

		JRadioButton RButton_10 = new JRadioButton("후각이나 미각의 변화");
		RButton_10.setFont(new Font("굴림", Font.PLAIN, 13));
		RButton_10.setBounds(22, 327, 167, 23);
		write_panel_2.add(RButton_10);

		JRadioButton RButton_11 = new JRadioButton("설사");
		RButton_11.setFont(new Font("굴림", Font.PLAIN, 13));
		RButton_11.setBounds(22, 351, 113, 23);
		write_panel_2.add(RButton_11);

		JLabel Label_6 = new JLabel("6. 상기 증상 중 하나라도 있다면 반드시 집에서 머물기 바랍");
		Label_6.setFont(new Font("굴림", Font.PLAIN, 13));
		Label_6.setBounds(12, 406, 349, 27);
		write_panel_2.add(Label_6);

		JLabel Label_6_1 = new JLabel("   니다. (손 씻기와 마스크 착용은 필수적입니다.)");
		Label_6_1.setFont(new Font("굴림", Font.PLAIN, 13));
		Label_6_1.setBounds(12, 433, 349, 27);
		write_panel_2.add(Label_6_1);

		JRadioButton RButton_12 = new JRadioButton("모두 확인하였습니다.");
		RButton_12.setFont(new Font("굴림", Font.PLAIN, 13));
		RButton_12.setBounds(22, 466, 167, 23);
		write_panel_2.add(RButton_12);
		
		JButton writeBack_Button_2 = new JButton("←");
		writeBack_Button_2.setBounds(249, 573, 50, 50);
		write_panel_2.add(writeBack_Button_2);
		writeBack_Button_2.setForeground(Color.WHITE);
		writeBack_Button_2.setFont(new Font("굴림", Font.BOLD, 15));
		writeBack_Button_2.setBackground(new Color(0, 102, 153));

		JButton writeNext_Button_2 = new JButton("→");
		writeNext_Button_2.setBounds(311, 573, 50, 50);
		write_panel_2.add(writeNext_Button_2);
		writeNext_Button_2.setForeground(Color.WHITE);
		writeNext_Button_2.setFont(new Font("굴림", Font.BOLD, 15));
		writeNext_Button_2.setBackground(new Color(0, 102, 153));
		write_panel_2.setVisible(false);
		
		enterPage_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				main_panel.setVisible(false);
				enter_panel.setVisible(true);
			}
		});
		
		writePage_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				main_panel.setVisible(false);
				write_panel_1.setVisible(true);
			}
		});
		
		enterBack_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				main_panel.setVisible(true);
				enter_panel.setVisible(false);
			}
		});
		
		writeNext_Button_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				write_panel_2.setVisible(true);
				write_panel_1.setVisible(false);
				
			}
		});

		writeBack_Button_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				main_panel.setVisible(true);
				write_panel_1.setVisible(false);
				write_panel_2.setVisible(false);
			}
		});

		writeBack_Button_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				write_panel_1.setVisible(true);
				write_panel_2.setVisible(false);
			}
		});
		
		writeNext_Button_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				write_panel_1.setVisible(false);
				write_panel_2.setVisible(false);
				main_panel.setVisible(true);
				
				//체크여부
				if(yes.isSelected() == true && no_1.isSelected() == true && no_2.isSelected() == true && no_3.isSelected() == true && no_4.isSelected() == true
					&& RButton_1.isSelected() == false && RButton_2.isSelected() == false  && RButton_3.isSelected() == false && RButton_4.isSelected() == false
					&& RButton_5.isSelected() == false && RButton_6.isSelected() == false && RButton_7.isSelected() == false && RButton_8.isSelected() == false
					&& RButton_9.isSelected() == false && RButton_10.isSelected() == false && RButton_11.isSelected() == false && RButton_12.isSelected() == true) {
					
					pass = "1";
				}

				if(pass.equals("1")) {
					pass_Label.setText("PASS");
					pass_Label.setForeground(Color.GREEN);
					pass = "0";
					pass_Label.setVisible(true);
				} else {
					pass_Label.setText("FAIL");
					pass_Label.setForeground(Color.RED);
					pass_Label.setVisible(true);
				}
			}
		});
	}
}
