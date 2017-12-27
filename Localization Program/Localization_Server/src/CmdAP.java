import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class CmdAP implements Runnable {
	private int req_port;
	private BufferedReader req_in;
	private PrintWriter req_out;	
	private String IP;
	boolean end_flag;
	private Socket dataExch_s = null;
	private String line;
	private boolean runningflag; 
	public CmdAP(String ip, int port) {
		this.IP=ip;
		req_port = port;
		runningflag=false;
	}

	@Override
	public void run() {
		runningflag=true;
		try {
			 System.out.println("!!!!!!!!!开始尝试连接AP"+IP);
		dataExch_s= new Socket(IP, req_port);
		req_in = new BufferedReader(new InputStreamReader(dataExch_s.getInputStream()));
		req_out = new PrintWriter(dataExch_s.getOutputStream());
		System.out.println("!!!!!!!!!成功连接AP"+IP);
        String tmp="ChangeAPState";
        req_out.println(tmp);
	    req_out.flush();
	    
	    
	    System.out.println("开始接收回复");	
		
			line = req_in.readLine();
		    req_out.close();
		    req_in.close();
		    dataExch_s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("收到回复结果" + line);
		runningflag=false;

	}

public boolean isrunning(){
	return  runningflag;
}

public int getresult(){
	if(line.equals("0")){
		return 0;
	}else{
		return 1;
	}
	
}



}
