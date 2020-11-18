package server;

// DB에서 불러온 데이터를 설정하는 클래스
public class user {

	
	String classof;
	String name;
	String id;
	String pw;
	
	// 학번
	public String getClassof() {
		return name;
	}
	public void setClassof(String classof) {
		this.classof = classof;
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
