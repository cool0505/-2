package server;

// DB에서 불러온 데이터를 설정하는 클래스
public class user {

	
	String studentnum;
	String name;
	String id;
	String pw;
	
	// 학번
	public String getStudentNum() {
		return studentnum;
	}
	public void setStudentNum(String studentnum) {
		this.studentnum = studentnum;
	}
	
	// 이름
	public String getName() {
		
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	// 아이디
	public String getID() {
		return id;
	}
	public void setID(String id) {
		this.id = id;
	}
	
	// 비밀번호
	public String getPW() {
		return pw;
	}
	public void setPW(String pw) {
		this.pw = pw;
	}
}
