package com.example.adr_client;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;



// 记录任务（一个包）的类
public class APRSS {

	
	private String APname;
	//private int RSS1, RSS2;
	//private int APState0, APState1;
	private Integer[] RSSA0=new Integer[100];
	private Integer[] RSSA1=new Integer[100];
	private int RSSA0counter;
	private int RSSA1counter;
	
	public APRSS(String nm){
		APname=nm;
		RSSA0counter=0;
		RSSA1counter=0;
	}
	
//	public void setRSS1(int RSS1, int APState1){
//		this.RSS1=RSS1;
//		this.APState1=APState1;
//	}
	
	public void setRSS(int APState, int RSS){
		if (APState==0){
			RSSA0[RSSA0counter]=RSS;
			RSSA0counter=RSSA0counter+1;
		}else{
			RSSA1[RSSA1counter]=RSS;
			RSSA1counter=RSSA1counter+1;
		}
		
	}
	
	public int getcounter(int i){
		if(i==0){
			return RSSA0counter;
		}else{
			return RSSA1counter;
		}
	}
	
	public Integer[] getRSS(int i){
		if(i==0){
			return RSSA0;
		}else{
			return RSSA1;
		}
	}
	

//	public void setRSS2(int RSS2, int APState2){
//		this.RSS2=RSS2;
//		this.APState2=APState2;
//	}
//	
//	public int getRSS(int i){
//		if(i==1){
//			return RSS1;
//		}else{
//			return RSS2;
//		}
//	}
//	
//	public int getAPState(int i){
//		if(i==1){
//			return APState1;
//		}else{
//			return APState2;
//		}
//	}
//	
	
}

