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
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;


public class ServerRspThread implements Runnable {
	private int req_port;
	private ServerSocket req_server;
	private Socket req_s;
	private Map<String, TerminalNum> APlist =new ConcurrentHashMap<String, TerminalNum>();
	//private MainWindow parent;
	boolean end_flag;
	private Map<String, SmartphoneThread> spthrd = new ConcurrentHashMap<String, SmartphoneThread>();
	public APmanager apm;
	public boolean firstphoneflag,firstphoneflag2;
	
	
	public ServerRspThread(int port, MainWindow mw) {
		req_port = port;
		//parent = mw;
		for(int i=1;i<=5;i++){
			APlist.put("IWCTAP"+i, new TerminalNum());
		}
		firstphoneflag=true;
		firstphoneflag2=true;
	}

	public boolean getfirstphoneflag(){
		return firstphoneflag;
	}
	public void setfirstphoneflag(){
		 firstphoneflag=false;
	}
	public boolean getfirstphoneflag2(){
		return firstphoneflag2;
	}
	public void setfirstphoneflag2(){
		 firstphoneflag2=false;
	}
	
	
	
	@Override
	public void run() {
		System.out.println("APmanager开始运行");		
		apm=new APmanager(APlist);
		new Thread(apm, "AP Management Thread").start();
		
		req_server = null;
		try {
			System.out.println("Socket建立前");		
			req_server = new ServerSocket(req_port);//建立监听socket
			System.out.println("Request server created!");
			
			end_flag = false;
			while (!end_flag) {
				System.out.println("开始监听!");		
				req_s = req_server.accept();//开始监听
				System.out.println("Request server accepted!"+req_s.getInetAddress());
				//为其开一个新线程
				spthrd.put(req_s.getInetAddress().getHostAddress(),new SmartphoneThread(req_s,apm,this));
				new Thread(spthrd.get(req_s.getInetAddress().getHostAddress()), "Smartphone Thread").start();
				//spthrd.get(req_s.getInetAddress().getHostAddress()).run();
//				spthrd.put("192.168.1.1",new SmartphoneThread(req_s,apm));
//				new Thread(spthrd.get("192.168.1.1"), "Smartphone Thread").start();
//				break;
				
			}
			System.out.println("从循环中退出");
			req_s.close();
		} catch (Exception e) {
			System.out.println("Can not listen to:" + e);
		}
		System.out.println("Server ended!");
	}

	public void finish(){
		end_flag = true;
		
	}


}
