package com.example.adr_client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;

public class SuperWiFi extends MainActivity{
	
	static final String TAG = "SuperWiFi";
	
	static SuperWiFi wifi = null;
	static Object sync = new Object();
	static int TESTTIME=1;
	
	WifiManager wm = null;
	private Vector<String> scanned = null;
	boolean isScanning = false;
	private int APRSS1 = 0;
	private int APRSS2 = 0;
	private FileOutputStream out;
	private int length=0;
	private Map<String, Integer> RSSlist =new ConcurrentHashMap<String, Integer>();
	private int p;
	
	public SuperWiFi(Context context)
	{
		this.wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		this.scanned = new Vector<String>();
		
	}
	
	public void ScanRss(String[] APNAMElist){
		startScan(APNAMElist);		
	}
	
	public boolean isscan(){
		return isScanning;
		
	}
	
	
//	public Vector<String> getRSSlist(){
//		
//		return scanned;
//		
//	}
	
	public static SuperWiFi getInstance(Context context)
	{
		if(wifi==null)
		{
			synchronized(sync)
			{
				if(wifi==null)
				{
					wifi = new SuperWiFi(context);
				}
			}
		}
		
		return wifi;
	}
	
	public boolean setNetwork(String name)
	{
		//make sure wifi is enabled
		if(wm==null)
			return false;
		try
		{
			if(!wm.isWifiEnabled())//�����޸���
			{
				wm.setWifiEnabled(true);
			}
			
			//get network
			List<WifiConfiguration> wc = wm.getConfiguredNetworks();
		
			
			//set specific network
			Iterator<WifiConfiguration> it = wc.iterator();
			while(it.hasNext())
			{
				WifiConfiguration network = it.next();
				
				if(network.SSID.equals(name))//�ҵ�ָ��SSID������󣬶Ͽ������磬������
				{
					wm.disconnect();
					wm.enableNetwork(network.networkId, true);
					wm.reconnect();
					return true;
				}
			}
		}
		catch (Exception e)
		{
			Log.d(TAG, e.toString());
		}
		finally
		{	
		}
		
		return false;
		
	}
	
	public ArrayList<String> getAPHistory()
	{
		List<WifiConfiguration> networks = this.wm.getConfiguredNetworks();
		ArrayList<String> netList = new ArrayList<String>();
		for(WifiConfiguration network:networks){
			netList.add(network.SSID);
		}
		return netList;
	}
	
//	public Vector<String> getScanned()
//	{
//		if(this.isScanning==false)
//		{
//			this.isScanning = true;
//			startScan();
//		}
//		return this.scanned;
//	}
	
	private void startScan(String[] APNAMElist)//
	{
		//Log.e("!!!!!!!!!!!!!!", ""+(APNAMElist.length-1));
		for (int i=0;i<=APNAMElist.length-1;i++){
			//Log.e("!!!!!!!!!!!!!!", APNAMElist[i]);
			RSSlist.put(APNAMElist[i], 0);
		}
		length=APNAMElist.length-1;
		
		this.isScanning = true;
		Thread scanThread = new Thread(new Runnable()
		{

			public void run() {
				// TODO Auto-generated method stub
				//performScan();
				scanned.clear();
				APRSS1=0;
				APRSS2=0;
				p=1;
				
				SimpleDateFormat formatter = new SimpleDateFormat ("yyyy��MM��dd��    HH:mm:ss");       
				Date curDate = new Date(System.currentTimeMillis());//��ȡ��ǰʱ��       
				String str = formatter.format(curDate); 

				
				
				write2file("testID: "+testID+" TestTime: "+str+" BEGIN\n");
				while(p<=TESTTIME)
				{
					performScan();
//					try {
//						Thread.sleep(1000);//ÿ3000ɨ��һ�Σ�ֱ������
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
					
					p=p+1;
				}
				
				Set<String> set =RSSlist.keySet();//       
				Iterator<String> it=set.iterator();//       
				while(it.hasNext()){//           
					String s= (String) it.next();//
					RSSlist.put(s, RSSlist.get(s)/TESTTIME);
					if(s.endsWith("IWCTAP1")||s.equals("IWCTAP2")||s.endsWith("IWCTAP3")||s.equals("IWCTAP4")||s.endsWith("IWCTAP5")){
						Log.e("�������",s+" "+RSSlist.get(s)/TESTTIME);
					}
					
				}
				

				
				
//				scanned.add("hehe = " +APRSS1/TESTTIME);
//				scanned.add("MobileWiFi-yt = " +APRSS2/TESTTIME);
//				write2file("testID:"+testID+"END\n");
				
				isScanning=false;
				
			}
			
		});
		
		
		scanThread.start();
	}
	
	
	public Map<String, Integer> getRSSlist(){
		return RSSlist;
	}
	
	
	
	private void performScan()
	{
		if(wm==null)
			return;
		
		try
		{
			if(!wm.isWifiEnabled())
			{
				wm.setWifiEnabled(true);
			}

//			if(p==1){
//				for(int kk=1;kk<=4;kk++){
//					wm.startScan();
//						Thread.sleep(2000);//ÿ3000ɨ��һ�Σ�ֱ������
//					wm.getScanResults();
//				}
//			}
			
			wm.startScan();
			
			Thread.sleep(2000);			
			
			this.scanned.clear();
			List<ScanResult> sr = wm.getScanResults();
			Iterator<ScanResult> it = sr.iterator();
			while(it.hasNext())
			{
				ScanResult ap = it.next();
				if (RSSlist.containsKey(ap.SSID))//ap.SSID.equals("hehe"))//ap.SSID.equals("TP_LINK")
				{
					//this.scanned.add("SSID:"+ap.SSID+" BSSID:"+ap.BSSID+" RSS:"+ap.level+"\n");
					RSSlist.put(ap.SSID, RSSlist.get(ap.SSID)+ap.level);
					//APRSS1=APRSS1+ap.level;
					//write2file("SSID:hehe "+"BSSID:"+ap.BSSID+" "+"RSS:"+ap.level+"\n");
					//write2file("SSID:hehe "+"RSS:"+ap.level+"\n");
				}
				
//				if (ap.SSID.equals("MobileWiFi-yt"))//ap.SSID.equals("TP_LINK")
//				{
//					//this.scanned.add("SSID:"+ap.SSID+" BSSID:"+ap.BSSID+" RSS:"+ap.level+"\n");
//					APRSS2=APRSS2+ap.level;
//					write2file("SSID:MobileWiFi-yt "+"RSS:"+ap.level+"\n");
//				}
				
				
			}
			//this.isScanning=false;
		}
		catch (Exception e)
		{
			this.isScanning = false;
			this.scanned.clear();
			Log.d(TAG, e.toString());
		}
	}
	
	
	
	private void write2file(String a){
		
		try {
			
			  File file = new File("/sdcard/RSSResult.txt");
			  if (!file.exists()){
				  file.createNewFile();}
			  
			// ��һ����������ļ���������д��ʽ   
			RandomAccessFile randomFile = new RandomAccessFile("/sdcard/RSSResult.txt", "rw"); 
			// �ļ����ȣ��ֽ���   
			long fileLength = randomFile.length();   
			// ��д�ļ�ָ���Ƶ��ļ�β��
			randomFile.seek(fileLength);   
		    randomFile.writeBytes(a);   
		    Log.e("!","!!");
	        randomFile.close();   
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
	
}
