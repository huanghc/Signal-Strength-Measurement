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
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;


public class APmanager implements Runnable {

	private Map<String, TerminalNum> APlist = new ConcurrentHashMap<String, TerminalNum>();
	private int smartID;
	private int WAITTIME=30000;
	private Map<String, String> APIP= new ConcurrentHashMap<String, String>();
	private CmdAP changeAP;
	public APmanager(Map<String, TerminalNum> APlist) {
		this.APlist=APlist;
		for(int i=1;i<=5;i++){
			this.APlist.put("IWCTAP"+i,new TerminalNum());
			//APIP.put("IWCTAP"+i, "192.168.1.104");
		}
		APIP.clear();
		APIP.put("IWCTAP1", "192.168.1.101");
		APIP.put("IWCTAP2", "192.168.1.106");
		APIP.put("IWCTAP3", "192.168.1.103");
		APIP.put("IWCTAP4", "192.168.1.102");
		APIP.put("IWCTAP5", "192.168.1.108");
		smartID=0;
	}

	@Override
	public void run() {
		int APState=0;
		while(true){
			//遍历APlist
			Set<String> set =APlist.keySet();       
			Iterator<String> it=set.iterator();    
			
			
			while(it.hasNext()){           
				String s= (String) it.next();  
				if(!APlist.get(s).isempty()){
					//测试时恢复这部分的注释！！
					//开启令该AP翻转的线程,该线程需返回AP的状态值
					if(s.equals("IWCTAP1")||s.equals("IWCTAP2")||s.equals("IWCTAP3")||s.equals("IWCTAP4")||s.equals("IWCTAP5")){
					System.out.println("在"+s+"   "+APIP.get(s));	
					changeAP=new CmdAP(APIP.get(s),6105);
					new Thread(changeAP, "AP State Change Thread").start();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					while(changeAP.isrunning()){}
					APState=changeAP.getresult();
					}
					
					
					
					
					//APState=1-APState;
					//通知列表中该ID的手机通信线程可以开始下一步测试，并告知其AP的状态值
					//会造成一个手机SmartphoneThread会收到多次通知，
					//注意要求SmartphoneThread收集齐所有AP准备好的通知后再通知手机开始测量。
					APlist.get(s).cmd2test(s,APState);
					APlist.get(s).refresh();

				}
			}
			
			
			//等待5秒
			//System.out.println("轮询AP列表");
			
			
			try {
				Thread.sleep(WAITTIME);
				//APlist.get("hehe").cmd2test(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
	}
	
	public int addtest(String[] req_head, SmartphoneThread spt){
		smartID=smartID+1;
		//System.out.println("收到改变AP请求"+req_head[1]+req_head.length);
		for(int i=1;i<=req_head.length-1;i++){
			//System.out.println("!!!!!!!!!!!"+req_head[i]);
			APlist.get(req_head[i]).addterminal(smartID, 0,spt);
			System.out.println("在"+req_head[i]+"名下添加ID"+smartID);	
		}	
		return smartID;
	}


	
}
