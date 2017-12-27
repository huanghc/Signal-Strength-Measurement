package com.example.adr_client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class StepCalculater implements Runnable, SensorEventListener {
    /** Called when the activity is first created. */
	
	//设置LOG标签
	private Button mWriteButton, mStopButton;
	private boolean doWrite = false;
	private SensorManager sm;
	private float lowX = 0, lowY = 0, lowZ = 0;
	private final float FILTERING_VALAUE = 0.1f;

	private boolean stepcalculate;
	private int stepcounter,spacecounter;

	private boolean instep;
   
    public StepCalculater(Context context) {
    	 
        //创建一个SensorManager来获取系统的传感器服务
        sm = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        /*
         * 最常用的一个方法 注册事件
         * 参数1 ：SensorEventListener监听器
         * 参数2 ：Sensor 一个服务可能有多个Sensor实现，此处调用getDefaultSensor获取默认的Sensor
         * 参数3 ：模式 可选数据变化的刷新频率
         * */
        //  注册加速度传感器
        sm.registerListener(this, 
        		sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
        		SensorManager.SENSOR_DELAY_FASTEST);//.SENSOR_DELAY_NORMAL);
        stepcalculate=false;
        stepcounter=0;
        spacecounter=0;
        instep=false;
        //Log.i("1","1");
    }

    public void startstep(){
    	stepcounter=0;
    	spacecounter=0;
    	instep=false;
    	stepcalculate=true;
    }
    public void stopstep(){
    	stepcalculate=false;
    }
    
    


	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		//ACT.setText("onAccuracyChanged被触发");		
	}

	public void onSensorChanged(SensorEvent event) {
		String message = new String();
		if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			//图解中已经解释三个值的含义
			float X = event.values[0];
			float Y = event.values[1];
			float Z = event.values[2];
			
			//Low-Pass Filter
	        lowX = X * FILTERING_VALAUE + lowX * (1.0f - FILTERING_VALAUE);
	        lowY = Y * FILTERING_VALAUE + lowY * (1.0f - FILTERING_VALAUE);
	        lowZ = Z * FILTERING_VALAUE + lowZ * (1.0f - FILTERING_VALAUE);

	        //High-pass filter
	        float highX  = X -  lowX;
	        float highY  = Y -  lowY;
	        float highZ  = Z -  lowZ;
			double highA = Math.sqrt(highX * highX + highY * highY + highZ * highZ);
			
			if(stepcalculate){
				if(instep){
					if(highY<0){
						spacecounter=spacecounter+1;
						if(spacecounter>20){
							instep=false;
							stepcounter=stepcounter+1;
							//Log.e("!!!!!!!!!!!!!!!!","3");
						}
					}else{
						spacecounter=0;
					}				
				}else{
					if(highY>=0){
						instep=true;
						spacecounter=0;
					}
				}
//				if(highA<3){
//				stepcounter=stepcounter+1;
//			   }
			}
			

			
			
			
			//DecimalFormat df = new DecimalFormat("#,##0.000");
			
			//message = df.format(highX) + "  ";
			//message += df.format(highY) + "  ";
			//message += df.format(highZ) + "  ";
			//message += df.format(highA) + "\n";
			//message = df.format(highA) + "\n";
			if (stepcalculate) {
				write2file(""+highY+"\n");
				//writeFileSdcard("acc.txt", message);
				//Log.i("3","3");
			}
		}
	}
	
	public int getsteps(){
		return stepcounter;
	}
	
	private void write2file(String a){
		
		try {
			
			  File file = new File("/sdcard/acc.txt");
			  if (!file.exists()){
				  file.createNewFile();}
			  
			// 打开一个随机访问文件流，按读写方式   
			RandomAccessFile randomFile = new RandomAccessFile("/sdcard/acc.txt", "rw"); 
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

	public void run() {
		// TODO Auto-generated method stub
		
	}

	
	
	
	
}