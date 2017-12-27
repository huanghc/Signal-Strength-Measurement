package com.example.adr_client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;

public class SendResult implements Runnable{
	
	private Map<String, APRSS> Resultlist=new ConcurrentHashMap<String, APRSS>();
	static final String TAG = "SendResult";
	private BufferedReader req_in;
	private PrintWriter req_out;
	private Socket dataExch_s = null;
	
	static SendResult wifi = null;
	static Object sync = new Object();
	static int TESTTIME=1;
	
	private String ServerIP;
	private int StepNum;

	public SendResult(String ServerIP, Map<String, APRSS> Resultlist, int StepNum)
	{
		this.Resultlist=Resultlist;	
		this.ServerIP=ServerIP;
		this.StepNum=StepNum;
	}
	
	@SuppressLint("NewApi")
	public void run() {
		// TODO Auto-generated method stub
		try {
			dataExch_s= new Socket(ServerIP, 6102);
			req_in = new BufferedReader(new InputStreamReader(dataExch_s.getInputStream()));
			req_out = new PrintWriter(dataExch_s.getOutputStream());


		Log.i("","成功与服务器建立连接");
		//APNAME: AP1 STATE1: 0/1 RSS: RSS1 RSS2 ... STATE2: 1/0 RSS: RSS1 RSS2 ... APNAME
		String tmp="";
		Set<String> set =Resultlist.keySet();//       
		Iterator<String> it=set.iterator();//       
		while(it.hasNext()){//           
			String s= (String) it.next();// 
			if(!tmp.isEmpty()){
			tmp=tmp+" ";	
			}
			tmp=tmp+"APNAME: "+s+" STATE1: "+0+" RSS:";
			for(int p=0;p<=Resultlist.get(s).getcounter(0)-1;p++){
				tmp=tmp+" "+Resultlist.get(s).getRSS(0)[p];
			}
			tmp=tmp+" STATE2: "+1+" RSS:";
			for(int p=0;p<=Resultlist.get(s).getcounter(1)-1;p++){
				tmp=tmp+" "+Resultlist.get(s).getRSS(1)[p];
			}
			
//			tmp=tmp+" "+s+" "+Resultlist.get(s).getAPState(1)+" "+Resultlist.get(s).getRSS(1)
//					+" "+Resultlist.get(s).getAPState(2)+" "+Resultlist.get(s).getRSS(2);
		}
		tmp=tmp+" Step "+StepNum;
		req_out.println(tmp);
		req_out.flush();
		Log.i("","已向服务器上传测量结果");
		req_out.close();
		req_in.close();
		dataExch_s.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
