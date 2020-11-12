package GUI;

import DB.*;
import LOGIN.*;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JDialog;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JPasswordField;
import java.awt.SystemColor;
import java.awt.Choice;
import java.awt.Color;

public class UI {
	/* 
	 * 사용자가 로그인/회원가입할 때 입력한 값
	 * 학번 = UserNum
	 * 이름 = UserName
	 * 아이디 = UserID
	 * 비밀번호 = UserPW
	 * 
	 * 아이디 중복  확인 = idCheck (중복되는 아이디 없으면 1 / 없으면 0)
	 * 
	 * UI에서 필요한 값
	 * 로그인 결과 = LoginResult (성공 1 / 아이디 잘못 입력한 경우 0 / 비밀번호 잘못 입력한 경우 -1)
	 * 회원가입 결과 = SignUpResult (성공 1 / 실패 0)
	 * */
	String UserNum = null;
	String UserName = null;
	String UserId = null;
	String UserPw = null;
	String LoginResult = "0";
	String SignUpResult = "0";
	int idCheck = 0;

	int check = 0;
	
	UserSQL userSql = new UserSQL();
	LOGIN login = new LOGIN();
	USER user = new USER();
	CONNECT conn = new CONNECT();
	
	JFrame frame;
	JDialog dialog = new JDialog(frame);
	JPanel login_panel;
	JTextField id_textField;
	JTextField pw_textField;
	JButton signUpPage_Button;
	
	JPanel signUp_panel;
	JLabel signUp_Label;
	JTextField name_textField;
	JTextField id_textField_1;
	JPasswordField pwField_1;
	JPasswordField pwField_2;
	
	private JLabel login_Label;
	private JTextField num_textField;
	Choice choice;
	/**
	 * Launch the application.
	 */
	static Socket socket;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {			
				try {
					socket = new Socket("localhost", 8282);
					UI window = new UI();
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
	public UI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.setTitle("UNIV-PASS");
		frame.setBounds(100, 100, 520, 353);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//회원가입 패널
		signUp_panel = new JPanel();
		signUp_panel.setBackground(Color.WHITE);
		signUp_panel.setBounds(0, 0, 516, 323);
		frame.getContentPane().add(signUp_panel);
		signUp_panel.setLayout(null);

		signUp_Label = new JLabel("회원가입");
		signUp_Label.setBounds(258, 0, 262, 51);
		signUp_Label.setOpaque(true);
		signUp_Label.setBackground(SystemColor.inactiveCaption);
		signUp_Label.setHorizontalAlignment(SwingConstants.CENTER);
		signUp_Label.setFont(new Font("굴림", Font.BOLD, 17));
		signUp_panel.add(signUp_Label);
		
		name_textField = new JTextField("이름");
		name_textField.setBounds(59, 66, 266, 40);
		name_textField.setForeground(Color.LIGHT_GRAY);
		name_textField.setFont(new Font("굴림", Font.BOLD, 17));
		name_textField.setColumns(10);
		name_textField.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                name_textField.setText(null);
                name_textField.setForeground(Color.BLACK);
            }
        });
		signUp_panel.add(name_textField);
		
		num_textField = new JTextField("학번");
		num_textField.setBounds(59, 114, 266, 40);
		num_textField.setForeground(Color.LIGHT_GRAY);
		num_textField.setFont(new Font("굴림", Font.BOLD, 17));
		num_textField.setColumns(10);
		num_textField.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                num_textField.setText("");
                num_textField.setForeground(Color.BLACK);
            }
        });
		signUp_panel.add(num_textField);
		
		id_textField_1 = new JTextField("아이디");
		id_textField_1.setBounds(59, 162, 266, 40);
		id_textField_1.setForeground(Color.LIGHT_GRAY);
		id_textField_1.setFont(new Font("굴림", Font.BOLD, 17));
		id_textField_1.setColumns(10);
		id_textField_1.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
            	id_textField_1.setText("");
            	id_textField_1.setForeground(Color.BLACK);
            }
        });
		signUp_panel.add(id_textField_1);
		
		pwField_1 = new JPasswordField("비밀번호");
		pwField_1.setBounds(59, 210, 266, 40);
		pwField_1.setForeground(Color.LIGHT_GRAY);
		pwField_1.setFont(new Font("굴림", Font.BOLD, 17));
		pwField_1.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
            	pwField_1.setText("");
            	pwField_1.setForeground(Color.BLACK);
            }
        });
		signUp_panel.add(pwField_1);
		
		pwField_2 = new JPasswordField("비밀번호");
		pwField_2.setBounds(59, 258, 266, 40);
		pwField_2.setForeground(Color.LIGHT_GRAY);
		pwField_2.setFont(new Font("굴림", Font.BOLD, 17));
		pwField_2.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
            	pwField_2.setText("");
            	pwField_2.setForeground(Color.BLACK);
            }
        });
		signUp_panel.add(pwField_2);
		
		JButton loginPage_Button = new JButton("로그인");
		loginPage_Button.setBounds(0, 0, 262, 51);
		loginPage_Button.setFont(new Font("굴림", Font.BOLD, 17));
		loginPage_Button.setBackground(Color.LIGHT_GRAY);
		signUp_panel.add(loginPage_Button);
		
		JButton signUp_Button = new JButton("회원가입");
		signUp_Button.setBounds(362, 213, 123, 87);
		signUp_Button.setBackground(new Color(0, 102, 153));
		signUp_Button.setForeground(Color.WHITE);
		signUp_Button.setFont(new Font("굴림", Font.BOLD, 17));
		signUp_panel.add(signUp_Button);
		
		JButton idCheck_Button = new JButton("중복확인");
		idCheck_Button.setForeground(Color.WHITE);
		idCheck_Button.setFont(new Font("굴림", Font.BOLD, 17));
		idCheck_Button.setBackground(new Color(0, 102, 153));
		idCheck_Button.setBounds(362, 162, 123, 40);
		signUp_panel.add(idCheck_Button);
		signUp_panel.setVisible(false);
		
		//로그인 패널
		login_panel = new JPanel();
		login_panel.setBackground(Color.WHITE);
		login_panel.setBounds(0, 0, 516, 323);
		frame.getContentPane().add(login_panel);
		login_panel.setLayout(null);
		
		id_textField = new JTextField("아이디");
		id_textField.setBounds(125, 87, 266, 45);
		id_textField.setForeground(Color.LIGHT_GRAY);
		id_textField.setFont(new Font("굴림", Font.BOLD, 17));
		id_textField.setColumns(10);
		id_textField.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
            	id_textField.setText("");
            	id_textField.setForeground(Color.BLACK);
            }
        });
		login_panel.add(id_textField);
		
		pw_textField = new JPasswordField("비밀번호");
		pw_textField.setBounds(125, 142, 266, 45);
		pw_textField.setForeground(Color.LIGHT_GRAY);
		pw_textField.setFont(new Font("굴림", Font.BOLD, 17));
		pw_textField.setColumns(10);
		pw_textField.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
            	pw_textField.setText("");
            	pw_textField.setForeground(Color.BLACK);
            }
        });
		login_panel.add(pw_textField);
		
		signUpPage_Button = new JButton("회원가입");
		signUpPage_Button.setForeground(Color.BLACK);
		signUpPage_Button.setBackground(Color.LIGHT_GRAY);
		signUpPage_Button.setBounds(258, 0, 262, 51);
		signUpPage_Button.setFont(new Font("굴림", Font.BOLD, 17));
		login_panel.add(signUpPage_Button);
		
		JButton login_Button = new JButton("로그인");
		login_Button.setForeground(Color.WHITE);
		login_Button.setFont(new Font("굴림", Font.BOLD, 17));
		login_Button.setBackground(new Color(0, 102, 153));
		login_Button.setBounds(125, 208, 266, 45);
		login_panel.add(login_Button);
		
		login_Label = new JLabel("로그인");
		login_Label.setOpaque(true);
		login_Label.setBackground(SystemColor.inactiveCaption);
		login_Label.setHorizontalAlignment(SwingConstants.CENTER);
		login_Label.setFont(new Font("굴림", Font.BOLD, 17));
		login_Label.setBounds(0, 0, 262, 51);
		login_panel.add(login_Label);
		
		signUpPage_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				signUp_panel.setVisible(true);
				login_panel.setVisible(false);
			}
		});

		loginPage_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				login_panel.setVisible(true);
				signUp_panel.setVisible(false);
			}
		});
		
		//로그인 정보를 입력하고 로그인 버튼을 눌렀을 때
		login_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//입력된 로그인 정보를 변수에 저장하는 부분
				UserId = id_textField.getText();
				UserPw = pw_textField.getText();
				
				try {
					InputStreamReader ISR = new InputStreamReader(socket.getInputStream());

					PrintWriter print = new PrintWriter(socket.getOutputStream());
					String sendstring = UserId + "/" + UserPw;
					
					print.println(sendstring);
					print.flush();
					
					BufferedReader buffer = new BufferedReader(ISR);
					
					LoginResult = buffer.readLine();
					
				} catch (IOException e1) {}

				if(LoginResult.equals("1")) {
					JOptionPane.showMessageDialog(null, "로그인이 완료되었습니다.");
				} else if(LoginResult.equals("0")) {
					JOptionPane.showMessageDialog(null, "아이디가 존재하지 않습니다.");
					
					id_textField.setText("아이디");
					id_textField.setForeground(Color.LIGHT_GRAY);
					id_textField.addMouseListener(new MouseAdapter(){
			            @Override
			            public void mouseClicked(MouseEvent e){
			            	id_textField.setText("");
			            	id_textField.setForeground(Color.BLACK);
			            }
			        });
					
					pw_textField.setText("비밀번호");
					pw_textField.setForeground(Color.LIGHT_GRAY);
					pw_textField.addMouseListener(new MouseAdapter(){
			            @Override
			            public void mouseClicked(MouseEvent e){
			            	pw_textField.setText("");
			            	pw_textField.setForeground(Color.BLACK);
			            }
			        });
				}/* else if(LoginResult == -1) {
					JOptionPane.showMessageDialog(null, "비밀번호가 일치하지 않습니다.");
					
					pw_textField.setText("비밀번호");
					pw_textField.setForeground(Color.LIGHT_GRAY);
					pw_textField.addMouseListener(new MouseAdapter(){
			            @Override
			            public void mouseClicked(MouseEvent e){
			            	pw_textField.setText("");
			            	pw_textField.setForeground(Color.BLACK);
			            }
			        });
				}*/
			}
		});
		
		//아이디 중복 여부를 체크하는 버튼을 눌렀을 때
		idCheck_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(id_textField_1.getText().length() > 0) {  //아이디를 입력한 경우
					if(userSql.idCheck(id_textField_1.getText())) {  //아이디가 존재하는 경우
						JOptionPane.showMessageDialog(dialog, "사용할 수 없는 아이디입니다.");
						
						id_textField_1.setText(null);
						id_textField_1.requestFocus();
					} else {	//중복되는 아이디가 없는 경우
						JOptionPane.showMessageDialog(dialog, "사용 가능한 아이디입니다.");
					}
				} else {
					JOptionPane.showMessageDialog(dialog, "아이디를 입력해주세요.");
					id_textField_1.requestFocus();
				}
			}			
		});
		
		//회원가입 정보를 입력하고 회원가입 버튼을 눌렀을 때
		signUp_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				UserNum = num_textField.getText();
				UserName = name_textField.getText();
				UserId = id_textField_1.getText();
				UserPw = pwField_2.getText();
				
				try {
					BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					PrintWriter print = new PrintWriter(socket.getOutputStream());
					String sendstring = UserNum + "/" + UserName + "/" + UserId + "/" + UserPw;
					
					print.println(sendstring);
					print.flush();
					
					LoginResult = buffer.readLine();
					
				} catch (IOException e1) {}

				
				if(SignUpResult.equals("1")) {
					JOptionPane.showMessageDialog(null, "회원가입이 완료되었습니다.");
					login_panel.setVisible(true);
					signUp_panel.setVisible(false);
				} else {
					if(idCheck == 0) {	//아이디가 이미 존재할 경우
						JOptionPane.showMessageDialog(null, "동일한 아이디가 존재합니다.");
					}
				}
				
/*				PreparedStatement ps;
				Connection conn2 = conn.getDB();
				
				//비밀번호 입력창과 비밀번호확인 입력창의 내용이 다르면 비밀번호가 다르다는 메시지 출력
				if(!(pwField_1.getText().contentEquals(pwField_2.getText()))) {
					JOptionPane.showMessageDialog(null, "비밀번호 다름");
				} else {
					try {
						ps = ((Connection) conn2).prepareStatement("INSERT INTO User VALUES(?,?,?,?)");
						ps.setString(1, num_textField.getText());
						ps.setString(2, name_textField.getText());
						ps.setString(3, id_textField_1.getText());
						ps.setString(4, pwField_2.getText());
						ps.executeUpdate();
						JOptionPane.showMessageDialog(null, "회원가입이 완료되었습니다.");
						
						name_textField.setText(null);
						num_textField.setText(null);
						id_textField_1.setText(null);
						pwField_1.setText(null);
						pwField_2.setText(null);

					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}*/
			}
		});
	}
}
