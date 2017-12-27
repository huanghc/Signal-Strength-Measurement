import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SmartphoneThread implements Runnable {

	private Socket req_s;
	private BufferedReader req_in;
	private PrintWriter req_out;
	boolean end_flag;
	private APmanager apm;
	private int myID;
	private Map<String, Integer> APreslist=new ConcurrentHashMap<String, Integer>();
	private int WAIT_FOR_AP_CHANGE=30000;//30000
//	private boolean dotestflag;
//	private String APname;
//	private int APState;
	private ServerRspThread SRT;
	
	public SmartphoneThread(Socket req_s, APmanager apm, ServerRspThread SRT) {
		//this.apm=apm;
		this.req_s=req_s;
		this.SRT=SRT;
		try {
			req_in = new BufferedReader(new InputStreamReader(req_s.getInputStream()));
			req_out = new PrintWriter(req_s.getOutputStream());
			this.apm=apm;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//dotestflag=false;
	}

	@Override
	public void run() {
		String line;
		try {
			line = req_in.readLine();

			System.out.println("line= " + line);
			String[] req_head = line.split(" ");
			if (req_head[0].equals("Request1")){
				int APNum=req_head.length-1;
				for(int i=1;i<=APNum;i++){
					APreslist.put(req_head[i], -1);
				}			
			//֪ͨAP�߳������AP�����Լ�Ҳ��Ϊ��������,�õ�һ�������Լ���ID
			    myID=apm.addtest(req_head, this);
			    System.out.println("��ȡID" + myID);
		}
			
			
//			System.out.println("dotestflag=" + dotestflag);
//			while(dotestflag==false){}
//			if(dotestflag==true){
//				System.out.println("dotestflag=" + dotestflag);
//				//���ռ�����֪ͨ����һ���֪�ֻ�
//				//���ֻ�ͨ�Ÿ�֪��ʼ����
//				System.out.println("�յ�����ָ��"+myID);
//				if (APreslist.get(APname).equals(-1)){
//					APreslist.put(APname, 10+APState);
//				}else{
//					APreslist.put(APname, 20+APState);//APreslist.get(APname)+10
//				}
//				System.out.println("STATE"+(APState));
//				
//				Set<String> set =APreslist.keySet();//       
//				Iterator<String> it=set.iterator();//       
//				boolean flag=true;
//				while(it.hasNext()){//          
//					String s= (String) it.next();// 
//					if(!(APreslist.get(s)>=10 &&APreslist.get(s)<20)){
//						flag=false;
//					}
//				}
//				if (flag){
//					//֪ͨ�ֻ���ʼ��һ�β��ԣ�AP״̬ΪAPreslist.get(s)-10
//					System.out.println("֪ͨ�ֻ����е�һ�β���");
//					String ans = "DoTest1";
//					set =APreslist.keySet();//       
//					it=set.iterator();//       
//					
//					while(it.hasNext()){//          
//						String s= (String) it.next();
//						ans=ans+" "+s+" "+(APreslist.get(s)-10);
//					}
//					ans=ans+"\n";
//					try {
//						Thread.sleep(WAIT_FOR_AP_CHANGE);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					req_out.println(ans);
//					req_out.flush();
//					System.out.println("Server sends back: " + ans);
//				}
//				
//				
//				flag=true;
//				set =APreslist.keySet();//       
//				it=set.iterator();//  
//				while(it.hasNext()){//          
//					String s= (String) it.next();// 
//					if(APreslist.get(s)<20){
//						flag=false;
//					}
//				}
//				if(flag){
//					//֪ͨ�ֻ���ʼ�ڶ��β��ԣ�AP״̬ΪAPreslist.get(s)-20
//					System.out.println("֪ͨ�ֻ����еڶ��β���");
//					String ans = "DoTest2";
//					set =APreslist.keySet();//       
//					it=set.iterator();//       
//					
//					while(it.hasNext()){//          
//						String s= (String) it.next();
//						ans=ans+" "+s+" "+(APreslist.get(s)-20);
//					}
//					ans=ans+"\n";
//					try {
//						Thread.sleep(WAIT_FOR_AP_CHANGE);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					
//					req_out.println(ans);
//					req_out.flush();
//					System.out.println("Server sends back: " + ans);
//					//�ڶ��β���������󣬹ر�socket
////					try {
////						req_in.close();
////						req_out.close();
////						req_s.close();
////					} catch (IOException e) {
////						// TODO Auto-generated catch block
////						e.printStackTrace();
////					}
//
//				}
//				
//				
//				
//			}
//			dotestflag=false;
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	
	
	
//	public void dotest(String APname, int APState){
//		this.APname=APname;
//		this.APState=APState;
//		dotestflag=true;
//		System.out.println("?????????????!!!!!!!!!!!!!!!!1" + dotestflag);
//		
//	}
	
	
	public void dotest(String APname, int APState){
		//���ռ�����֪ͨ����һ���֪�ֻ�
		//���ֻ�ͨ�Ÿ�֪��ʼ����
		System.out.println("�յ�����ָ��"+myID);
		if (APreslist.get(APname).equals(-1)){
			APreslist.put(APname, 10+APState);
		}else{
			APreslist.put(APname, 20+APState);//APreslist.get(APname)+10
		}
		System.out.println("STATE"+(APState));
		
		Set<String> set =APreslist.keySet();//       
		Iterator<String> it=set.iterator();//       
		boolean flag=true;
		while(it.hasNext()){//          
			String s= (String) it.next();// 
			if(!(APreslist.get(s)>=10 &&APreslist.get(s)<20)){
				flag=false;
			}
		}
		if (flag){
			//֪ͨ�ֻ���ʼ��һ�β��ԣ�AP״̬ΪAPreslist.get(s)-10
			System.out.println("֪ͨ�ֻ����е�һ�β���");
			String ans = "DoTest1";
			set =APreslist.keySet();//       
			it=set.iterator();//       
			
			while(it.hasNext()){//          
				String s= (String) it.next();
				ans=ans+" "+s+" "+(APreslist.get(s)-10);
			}
			ans=ans+"\n";
			try {
				if(SRT.getfirstphoneflag()){
					Thread.sleep(WAIT_FOR_AP_CHANGE);
					SRT.setfirstphoneflag();	
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			req_out.println(ans);
			req_out.flush();
			System.out.println("Server sends back: " + ans);
		}
		
		
		flag=true;
		set =APreslist.keySet();//       
		it=set.iterator();//  
		while(it.hasNext()){//          
			String s= (String) it.next();// 
			if(APreslist.get(s)<20){
				flag=false;
			}
		}
		if(flag){
			//֪ͨ�ֻ���ʼ�ڶ��β��ԣ�AP״̬ΪAPreslist.get(s)-20
			System.out.println("֪ͨ�ֻ����еڶ��β���");
			String ans = "DoTest2";
			set =APreslist.keySet();//       
			it=set.iterator();//       
			
			while(it.hasNext()){//          
				String s= (String) it.next();
				ans=ans+" "+s+" "+(APreslist.get(s)-20);
			}
			ans=ans+"\n";
			try {
				if(SRT.getfirstphoneflag2()){
					Thread.sleep(WAIT_FOR_AP_CHANGE);
					SRT.setfirstphoneflag2();	
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			req_out.println(ans);
			req_out.flush();
			System.out.println("Server sends back: " + ans);
			//�ڶ��β���������󣬹ر�socket
//			try {
//				req_in.close();
//				req_out.close();
//				req_s.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}

		}
		
	}
	
}

