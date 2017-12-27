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
import java.util.Vector;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;

public class SuperWiFi extends MainActivity{
	
	static final String TAG = "SuperWiFi";
	
	static SuperWiFi wifi = null;
	static Object sync = new Object();
	static int TESTTIME=25;
	
	WifiManager wm = null;
	private Vector<String> scanned = null;
	boolean isScanning = false;
	private int[] APRSS=new int[10];
	private FileOutputStream out;
	private int p;
	
	public SuperWiFi(Context context)
	{
		this.wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		this.scanned = new Vector<String>();
		
	}
	
	public void ScanRss(){
		startScan();		
	}
	
	public boolean isscan(){
		return isScanning;
		
	}
	
	
	public Vector<String> getRSSlist(){
		
		return scanned;
		
	}
	
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
			if(!wm.isWifiEnabled())//这里修改了
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
				
				if(network.SSID.equals(name))//找到指定SSID的网络后，断开该网络，再重连
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
	
	public Vector<String> getScanned()
	{
		if(this.isScanning==false)
		{
			this.isScanning = true;
			startScan();
		}
		return this.scanned;
	}
	
	private void startScan()//
	{
		this.isScanning = true;
		Thread scanThread = new Thread(new Runnable()
		{

			public void run() {
				// TODO Auto-generated method stub
				//performScan();
				scanned.clear();
				for(int j=1;j<=10;j++){
					APRSS[j-1]=0;
				}
				p=1;
				
				SimpleDateFormat formatter = new SimpleDateFormat ("yyyy年MM月dd日    HH:mm:ss");       
				Date curDate = new Date(System.currentTimeMillis());//获取当前时间       
				String str = formatter.format(curDate); 

				
				for(int k=1;k<=10;k++){
					write2file("RSS-IWCTAP"+k+".txt","testID: "+testID+" TestTime: "+str+" BEGIN\n");
				}
				
				while(p<=TESTTIME)
				{
					performScan();
//					try {
//						Thread.sleep(1000);//每3000扫描一次，直到出错
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
					
					p=p+1;
				}
				for(int i=1;i<=10;i++){
					scanned.add("IWCTAP"+i+"= " +APRSS[i-1]/TESTTIME+"\n");
				}
				
				
				for(int k=1;k<=10;k++){
					write2file("RSS-IWCTAP"+k+".txt","testID:"+testID+"END\n");
				}
				
				isScanning=false;
				
			}
			
		});
		
		
		scanThread.start();
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
			
			if(p==1){
				for(int kk=1;kk<=4;kk++){
					wm.startScan();
					try {
						Thread.sleep(2000);//每3000扫描一次，直到出错
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					wm.getScanResults();
				}

			}
			
			wm.startScan();

			
			try {
				Thread.sleep(3000);//每3000扫描一次，直到出错
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

			
			this.scanned.clear();
			List<ScanResult> sr = wm.getScanResults();
			Iterator<ScanResult> it = sr.iterator();
			while(it.hasNext())
			{
				ScanResult ap = it.next();
				
				for(int k=1;k<=10;k++){
					if (ap.SSID.equals("IWCTAP"+k)){
						APRSS[k-1]=APRSS[k-1]+ap.level;
						write2file("RSS-IWCTAP"+k+".txt",ap.level+"\n");
					}
					
				}			
				
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
	
	private void write2file(String filename, String a){
		
		try {
			
			  File file = new File("/sdcard/"+filename);
			  if (!file.exists()){
				  file.createNewFile();}
			  
			// 打开一个随机访问文件流，按读写方式   
			RandomAccessFile randomFile = new RandomAccessFile("/sdcard/"+filename, "rw"); 
			// 文件长度，字节数   
			long fileLength = randomFile.length();   
			// 将写文件指针移到文件尾。
			randomFile.seek(fileLength);   
		    randomFile.writeBytes(a);   
		    //Log.e("!","!!");
	        randomFile.close();   
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
	
}
