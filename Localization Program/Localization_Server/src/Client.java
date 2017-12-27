public class Client {
	private String name;
	private String psw;
	private int isPU;
	private int packetnum;
	private String url;
	String fileName;
	int startPosition;
	int endPosition;
	int downloadSize;
	int downloadRate;
	
	// 需要包括： 用户名 密码 是否PU 包数
	public Client(){
		
	}
	
	public Client(String name, String psw, int isPU, int packetnum, String url) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.psw = psw;
		this.isPU = isPU;
		this.packetnum = packetnum;
		this.url=url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String geturl() {
		return url;
	}

	public void seturl(String url) {
		this.url = url;
	}

	public String getpsw() {
		return psw;
	}

	public void setpsw(String psw) {
		this.psw = psw;
	}
	
	public int getisPU() {
		return isPU;
	}

	public void setisPU(int isPU) {
		this.isPU = isPU;
	}

	public int getpacketnum() {
		return packetnum;
	}

	public void setSigIntensity(int packetnum) {
		this.packetnum = packetnum;
	}
	
	
}