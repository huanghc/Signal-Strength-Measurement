package com.example.adr_client;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;

public class UptoServer  implements Runnable{
	private Socket dataExch_s = null;
//	private InputStream downData_in;
//	private OutputStream dataExch_out;
	private BufferedReader req_in;
	private PrintWriter req_out;
	private SuperWiFi rss_scan =null;
	private Map<String, APRSS> APRSSlist =new ConcurrentHashMap<String, APRSS>();
	private Map<String, Integer> RSSlist =new ConcurrentHashMap<String, Integer>();
	private String[] APNAMElist=new String[10];
	private SendResult sr;
	private String SeverIP="192.168.137.1";
	private int TESTTIME=5;
	private Context ma;
	private StepCalculater StepCal;
	public UptoServer(Context context,StepCalculater StepCal)
	{
		rss_scan=new SuperWiFi(context);
		APRSSlist.clear();
		RSSlist.clear();
		for(int i=1;i<=10;i++){
			APNAMElist[i-1]="IWCTAP"+i;
		}
		
		ma=context;
		this.StepCal=StepCal;
	}

	public void run() {
		// TODO Auto-generated method stub
		StepCal.stopstep();
		try {
			dataExch_s= new Socket(SeverIP, 6101);
			req_in = new BufferedReader(new InputStreamReader(dataExch_s.getInputStream()));
			req_out = new PrintWriter(dataExch_s.getOutputStream());
			//dataExch_out = dataExch_s.getOutputStream();
			//downData_in=dataExch_s.getInputStream();
			
			Log.i("","成功与服务器建立连接");
			String tmp="Request1 IWCTAP1 IWCTAP2 IWCTAP3 IWCTAP4 IWCTAP5\n";
//			byte[] buf =tmp.getBytes();
//			dataExch_out.write(buf, 0, buf.length);
			req_out.println(tmp);
			req_out.flush();
			Log.i("","向Server的上传信息已发送");
			
			
			
			String buf = req_in.readLine();
			Log.i("收到来自服务器的消息",buf);
//			byte[] buff = new byte[128];
//			downData_in.read(buff);
//			Log.i("收到来自服务器的消息",buf.toString());
			
			buf=buf+" IWCTAP6 0 IWCTAP7 0 IWCTAP8 0 IWCTAP9 0 IWCTAP10 0";//这里篡改了服务器要求测试的AP
			String[] req_head = buf.split(" ");
			if(req_head[0].equals("DoTest1")){
				//开始第一次测试
				Log.i("收到来自服务器的消息","开始第一次测试");

				for(int q=1;q<=TESTTIME;q++){		
					if(q==1){
						rss_scan.ScanRss(APNAMElist);
						while(rss_scan.isscan()){
		    			}
						Log.i("放弃一个值","1");
						rss_scan.ScanRss(APNAMElist);
						while(rss_scan.isscan()){
		    			}
						Log.i("放弃一个值","2");
						rss_scan.ScanRss(APNAMElist);
						while(rss_scan.isscan()){
		    			}
						Log.i("放弃一个值","3");
						rss_scan.ScanRss(APNAMElist);
						while(rss_scan.isscan()){
		    			}
						Log.i("放弃一个值","4");
					}
					rss_scan.ScanRss(APNAMElist);
	    			while(rss_scan.isscan()){
	    			}
	    			//测试完毕，填写结果进APRSSlist
	    			RSSlist=rss_scan.getRSSlist();
	    			for(int j=0;j<=APNAMElist.length-1;j++){
	    				if(q==1){
	    					APRSSlist.put(APNAMElist[j], new APRSS(APNAMElist[j]));
	    				}
	    				for(int p=0;p<=req_head.length-1;p++){
	    					if (req_head[p].equals(APNAMElist[j])){
	    						APRSSlist.get(APNAMElist[j]).setRSS(Integer.parseInt(req_head[p+1]), RSSlist.get(APNAMElist[j]));
	    						break;
	    					}
	    				}	
	    			}
				}

    			Log.e("！！！！！！！！","已得到第一次测试结果");
			}
			
			Log.i("!!!!!!!!!!!!!!","等待第二次测试命令");
			String buff = req_in.readLine();
			
			while(!buff.split(" ")[0].equals("DoTest2")){
				buff = req_in.readLine();
			}
			buff=buff+" IWCTAP6 1 IWCTAP7 1 IWCTAP8 1 IWCTAP9 1 IWCTAP10 1";//这里篡改了服务器要求测试的AP
			Log.i("收到来自服务器的消息",buff);
			
//			downData_in.read(buf, 0, tmp.getBytes().length);
//			Log.i("收到来自服务器的消息",buf.toString());
			req_head = buff.split(" ");
			if(req_head[0].equals("DoTest2")){
				//开始第二次测试
				Log.i("收到来自服务器的消息","开始第二次测试");
				for(int q=1;q<=TESTTIME;q++){
					if(q==1){
						rss_scan.ScanRss(APNAMElist);
						while(rss_scan.isscan()){
		    			}
						Log.i("放弃一个值","1");
						rss_scan.ScanRss(APNAMElist);
						while(rss_scan.isscan()){
		    			}
						Log.i("放弃一个值","2");
						rss_scan.ScanRss(APNAMElist);
						while(rss_scan.isscan()){
		    			}
						Log.i("放弃一个值","3");
						rss_scan.ScanRss(APNAMElist);
						while(rss_scan.isscan()){
		    			}
						Log.i("放弃一个值","4");
					}
					rss_scan.ScanRss(APNAMElist);
	    			while(rss_scan.isscan()){
	    			}
	    			//测试完毕，填写结果进APRSSlist
	    			RSSlist=rss_scan.getRSSlist();
	    			for(int j=0;j<=APNAMElist.length-1;j++){
	    				for(int p=0;p<=req_head.length-1;p++){
	    					if (req_head[p].equals(APNAMElist[j])){
	    						APRSSlist.get(APNAMElist[j]).setRSS(Integer.parseInt(req_head[p+1]),RSSlist.get(APNAMElist[j]));
	    						break;
	    					}
	    				}	
	    			}
				}
    			Log.e("！！！！！！！！","已得到第二次测试结果");
				//所有测试结果已填入APRSSlist
    			
    			
    			sr=new SendResult(SeverIP, APRSSlist,StepCal.getsteps());
    			new Thread(sr, "Sent redult to server Thread").start();
    			StepCal.startstep();
			}
			
	    req_out.close();
		req_in.close();
		dataExch_s.close();
		Log.i("","已关闭向服务器的Socket");
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	
}
