package com.example.adr_client;

import java.util.HashMap;
import java.util.Random;
import java.util.Vector;

import android.net.wifi.WifiConfiguration;
import com.example.adr_client.R;
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
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final EditText ipText = (EditText)findViewById(R.id.ipText);
       // final EditText connectedText =  (EditText)findViewById(R.id.connected);
        final Button changactivity = (Button)findViewById(R.id.button1);
        final Button cleanlist = (Button)findViewById(R.id.button2);
        
        //mBtAdapter = BluetoothAdapter.getDefaultAdapter();
		//clients = new HashMap<String, MClient>();
		//connectedclients= new HashMap<String, MClient>();
		
		rss_scan=new SuperWiFi(this);
		testlist="";
		testID=0;

        changactivity.setOnClickListener(new Button.OnClickListener(){
    		public void onClick(View v) { 
				//Toast.makeText(getApplicationContext(), "请确认已打开无线热点或已连接局域网！",Toast.LENGTH_SHORT).show();
    			testID=testID+1;
    			rss_scan.ScanRss();
    			while(rss_scan.isscan()){
    			}
    			RSSList=rss_scan.getRSSlist();

    	        final EditText ipText = (EditText)findViewById(R.id.ipText);
    	        testlist=testlist+"testID:"+testID+"\n"+RSSList.toString()+"\n";
    	        ipText.setText(testlist);//文本框里显示网络中手机ip   
	
    		}
        });
        
        cleanlist.setOnClickListener(new Button.OnClickListener(){
    		public void onClick(View v) { 
				//Toast.makeText(getApplicationContext(), "请确认已打开无线热点或已连接局域网！",Toast.LENGTH_SHORT).show();
    	        
    	        testlist="";
    	        ipText.setText(testlist);//文本框里显示网络中手机ip   
    	        testID=0;
    			
    			
    		}
        });
        
       
        
      
        
        
    }

}
