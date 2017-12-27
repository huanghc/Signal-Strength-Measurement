package com.example.adr_client;



import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Random;
import java.util.Vector;

import android.net.wifi.WifiConfiguration;



import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity {
	
	private SuperWiFi rss_scan =null;
	Vector<String> RSSList = null;
	private String testlist=null;
	public static  int testID=0;
	private Socket dataExch_s=null;
	private InputStream downData_in;
	private OutputStream dataExch_out;
	private MainActivity ma=this;
	private StepCalculater StepCal;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final EditText ipText = (EditText)findViewById(R.id.ipText);
       // final EditText connectedText =  (EditText)findViewById(R.id.connected);
        final Button changactivity = (Button)findViewById(R.id.button1);
        final Button cleanlist = (Button)findViewById(R.id.button2);
        final Button upload = (Button)findViewById(R.id.button3);
        final Button stepwrite = (Button)findViewById(R.id.button4);
        final Button stepstop = (Button)findViewById(R.id.button5);
        //mBtAdapter = BluetoothAdapter.getDefaultAdapter();
		//clients = new HashMap<String, MClient>();
		//connectedclients= new HashMap<String, MClient>();
		
		rss_scan=new SuperWiFi(this);
		testlist="";
		testID=0;
		StepCal=new StepCalculater(this);
        

        
        
//        changactivity.setOnClickListener(new Button.OnClickListener(){
//    		public void onClick(View v) { 
//				//Toast.makeText(getApplicationContext(), "请确认已打开无线热点或已连接局域网！",Toast.LENGTH_SHORT).show();
//    			testID=testID+1;
//    			rss_scan.ScanRss();
//    			while(rss_scan.isscan()){
//    			}
//    			RSSList=rss_scan.getRSSlist();
//
//    	        final EditText ipText = (EditText)findViewById(R.id.ipText);
//    	        testlist=testlist+"testID:"+testID+"\n"+RSSList.toString()+"\n";
//    	        ipText.setText(testlist);//文本框里显示网络中手机ip   
//	
//    		}
//        });
        
        cleanlist.setOnClickListener(new Button.OnClickListener(){
    		public void onClick(View v) { 
				//Toast.makeText(getApplicationContext(), "请确认已打开无线热点或已连接局域网！",Toast.LENGTH_SHORT).show();
    	        
    	        testlist="";
    	        ipText.setText(testlist);//文本框里显示网络中手机ip   
    	        testID=0;
    			
    			
    		}
        });
        
        upload.setOnClickListener(new Button.OnClickListener(){
    		public void onClick(View v) { 
    			UptoServer apm=new UptoServer(ma,StepCal);
    			new Thread(apm, "AP Management Thread").start();
    		}
        });
        
        stepwrite.setOnClickListener(new Button.OnClickListener(){
    		public void onClick(View v) { 
    			StepCal.startstep();
    			
    			
    		}
        });
        stepstop.setOnClickListener(new Button.OnClickListener(){
    		public void onClick(View v) { 
    			StepCal.stopstep();
    			
    			
    		}
        });
      
        
        
    }
	
	public void startstepcounter(){
		StepCal.startstep();
	}
	
	public int endsetpcounter(){
		StepCal.stopstep();
		return StepCal.getsteps();
	}
	

}
