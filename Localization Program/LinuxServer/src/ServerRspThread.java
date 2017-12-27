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

public class ServerRspThread implements Runnable {
	private int req_port;
	private ServerSocket req_server;
	private Socket req_s;
	private BufferedReader req_in;
	private PrintWriter req_out;
	private double signal_sum;	
	
	private MainWindow parent;
	boolean end_flag;
	private boolean APMode;
	private Process process;
	private String command;
	private String tmp;
	public ServerRspThread(int port) {
		req_port=port;
		APMode=true;
		process = null;
	}

	@Override
	public void run() {
		
		try {
			req_server = new ServerSocket(req_port);
			while (true) {
				System.out.println("开始监听!");		
				req_s = req_server.accept();//开始监听
				System.out.println("Request server accepted!");
				req_in = new BufferedReader(new InputStreamReader(
					req_s.getInputStream()));
				req_out = new PrintWriter(req_s.getOutputStream());
			
			
				String line = req_in.readLine();
				System.out.println("line= " + line);
				if(line.equals("ChangeAPState")){
					//在这里加入改变AP状态的代码
					if(APMode==true){
						APMode=false;
						command="sudo ifconfig wlan0 down";
					    process = Runtime.getRuntime().exec(command);
						command="sudo iw phy phy0 set antenna 1 1";
					    process = Runtime.getRuntime().exec(command);
						command="sudo ifconfig wlan0 up";
					    process = Runtime.getRuntime().exec(command);
//						command="sudo iwconfig wlan0 essid \"IWCTAP5\"";
//					    process = Runtime.getRuntime().exec(command);
					    tmp="1";
					    System.out.println("AP改变状态为1 1");	
					}else{
						APMode=true;
						command="sudo ifconfig wlan0 down";
					    process = Runtime.getRuntime().exec(command);
						command="sudo iw phy phy0 set antenna 3 3";
					    process = Runtime.getRuntime().exec(command);
						command="sudo ifconfig wlan0 up";
					    process = Runtime.getRuntime().exec(command);
//						command="sudo iwconfig wlan0 essid \"IWCTAP5\"";
//					    process = Runtime.getRuntime().exec(command);
					    tmp="0";
					    System.out.println("AP改变状态为3 3");	
					}

					
				}
				//String tmp="APSTATE";//在这里返回AP的状态
				req_out.println(tmp);
				req_out.flush();	
						
				req_out.close();
				req_in.close();
				req_s.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}

}
