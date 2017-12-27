package com.app.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.app.function.Function;
import com.app.function.Function_step;
import com.app.util.Coordinate;
import com.app.util.Map;

public class Main {
	/**
	 * @param args
	 */
	private static int MapNum=8;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Map[] maps = new Map[MapNum];
		int a = 1;
		int b = 0;
		for (int i = 0; i < MapNum; ++i){
			Map map = new Map();
			File map_file = new File("E:/eclipse程序/LocalizationServer_for_test/IWCTAP" + a + "_" + b +".txt");
			map.initiate(map_file);
			maps[i] = map;
			b = b + 1;
			if (b == 2){
				a = a + 1;
				b = 0;
			}
		}
		
		
		
		
		
		Function function = new Function();
		Function_step function_step = new Function_step();
		System.out.println("OK");
		
		File[] RSS_files=new File[6];
		RSS_files[0]=new File("E:/eclipse程序/LocalizationTest/result2/192.168.137.203.txt");
		RSS_files[1]=new File("E:/eclipse程序/LocalizationTest/result2/192.168.137.157.txt");
		RSS_files[2]=new File("E:/eclipse程序/LocalizationTest/result2/192.168.137.182.txt");
		RSS_files[3]=new File("E:/eclipse程序/LocalizationTest/result2/192.168.137.74.txt");
		RSS_files[4]=new File("E:/eclipse程序/LocalizationTest/result2/192.168.137.184.txt");
		RSS_files[5]=new File("E:/eclipse程序/LocalizationTest/result2/192.168.137.178.txt");
		//File RSS_file = new File("E:/eclipse程序/LocalizationTest/result/192.168.137.178.txt");
		
		FileReader fileReader = null;
		BufferedReader bfReader = null;
		double totaldis=0;
		double totaldis_step=0;
		Coordinate previouspo=new Coordinate();
		
		
		double[] dis_con=new double[20];
		double[] dis_step_con=new double[20];
		for(int jj=0;jj<=19;jj++){
			dis_con[jj]=0;
			dis_step_con[jj]=0;
		}
		double dis_max=0;
		double dis_min=100;
		double dis_step_max=0;
		double dis_step_min=100;
		
		for(int j=0;j<=2;j++){
			try {
				fileReader = new FileReader(RSS_files[j]);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			bfReader = new BufferedReader(fileReader);
			String name_and_state="";
			double dis=0;
			double dis_step=0;
			for(int i=0;i<=10;i++){
				try {
					if(i!=0){
						name_and_state = bfReader.readLine();
						name_and_state = bfReader.readLine();
					}
					name_and_state = bfReader.readLine();
					name_and_state = bfReader.readLine();
					
					////////////////////////////////////////////
					//System.out.println(name_and_state);
					String[] tt=name_and_state.split(" ");
					name_and_state="";
					for(int jj=0;jj<=18*5-1;jj++){
						if(jj==0){

							for(int ll=1;ll<=4;ll++){
							while(tt[jj].equals("IWCTAP5")){//tt[jj].equals("IWCTAP5")||tt[jj].equals("IWCTAP4")||tt[jj].equals("IWCTAP3")||tt[jj].equals("IWCTAP2")
								jj=jj+18;
							}
							}
							
							
							name_and_state=name_and_state+tt[jj];
						}else{
							//System.out.println(tt[jj]);
							for(int ll=1;ll<=4;ll++){
								while(tt[jj].equals("IWCTAP5")){//tt[jj].equals("IWCTAP5")||tt[jj].equals("IWCTAP4")||tt[jj].equals("IWCTAP3")||tt[jj].equals("IWCTAP2")
									jj=jj+18;
								}
								}
							name_and_state=name_and_state+" "+tt[jj];
						}
					}
					//System.out.println(name_and_state);
					//name_and_state="APNAME: IWCTAP4 STATE1: 0 RSS: -12 -12 -48 -48 -58 STATE2: 1 RSS: -64 -64 -64 -64 -64 APNAME: IWCTAP2 STATE1: 0 RSS: -54 -10 -10 -44 -50 STATE2: 1 RSS: -54 -54 -54 -10 -2 APNAME: IWCTAP3 STATE1: 0 RSS: -3 0 0 0 -63 STATE2: 1 RSS: -80 -80 -80 -80 -81 APNAME: IWCTAP1 STATE1: 0 RSS: -12 -61 -62 -12 -53 STATE2: 1 RSS: -65 -65 -65 -65 -65";	
					//System.out.println(name_and_state);
					Coordinate coordinate = function.doFunction(name_and_state, maps);
					if(i!=1){
					Coordinate coordinate_step = function_step.doFunction(previouspo,Integer.parseInt(tt[tt.length-1]),name_and_state, maps);
					//System.out.println(coordinate_step.getX()+" "+coordinate_step.getY());
					previouspo.setCoordinate(coordinate_step.getX(), coordinate_step.getY());
					
					if(Math.sqrt((coordinate_step.getX()-j)*(coordinate_step.getX()-j)+(coordinate_step.getY()-i)*(coordinate_step.getY()-i))>dis_step_max){
						dis_step_max=Math.sqrt((coordinate_step.getX()-j)*(coordinate_step.getX()-j)+(coordinate_step.getY()-i)*(coordinate_step.getY()-i));
					}
					if(Math.sqrt((coordinate_step.getX()-j)*(coordinate_step.getX()-j)+(coordinate_step.getY()-i)*(coordinate_step.getY()-i))<dis_step_min){
						dis_step_min=Math.sqrt((coordinate_step.getX()-j)*(coordinate_step.getX()-j)+(coordinate_step.getY()-i)*(coordinate_step.getY()-i));
					}
					
					dis_step=dis_step+Math.sqrt((coordinate_step.getX()-j)*(coordinate_step.getX()-j)+(coordinate_step.getY()-i)*(coordinate_step.getY()-i));
					
					double tpp=Math.sqrt((coordinate_step.getX()-j)*(coordinate_step.getX()-j)+(coordinate_step.getY()-i)*(coordinate_step.getY()-i));
					for(double ll=0;ll<=9.5;ll=ll+0.5){
						if(tpp>=ll && tpp<ll+0.5){
							dis_step_con[(int) (ll*2)]=dis_step_con[(int) (ll*2)]+1;
						}
					}
					if(tpp>=10){
						dis_step_con[19]=dis_step_con[19]+1;
					}
					}
				   // System.out.println(coordinate.getX() + " " + coordinate.getY());
					
					if(Math.sqrt((coordinate.getX()-j)*(coordinate.getX()-j)+(coordinate.getY()-i)*(coordinate.getY()-i))>dis_max){
						dis_max=Math.sqrt((coordinate.getX()-j)*(coordinate.getX()-j)+(coordinate.getY()-i)*(coordinate.getY()-i));
					}
					if(Math.sqrt((coordinate.getX()-j)*(coordinate.getX()-j)+(coordinate.getY()-i)*(coordinate.getY()-i))<dis_min){
						dis_min=Math.sqrt((coordinate.getX()-j)*(coordinate.getX()-j)+(coordinate.getY()-i)*(coordinate.getY()-i));
					}
					dis=dis+Math.sqrt((coordinate.getX()-j)*(coordinate.getX()-j)+(coordinate.getY()-i)*(coordinate.getY()-i));
					double tp=Math.sqrt((coordinate.getX()-j)*(coordinate.getX()-j)+(coordinate.getY()-i)*(coordinate.getY()-i));
					for(double ll=0;ll<=9.5;ll=ll+0.5){
						if(tp>=ll && tp<ll+0.5){
							dis_con[(int) (ll*2)]=dis_con[(int) (ll*2)]+1;
						}
					}
					if(tp>=10){
						//System.out.println("!!!!!!!!");
						dis_con[19]=dis_con[19]+1;
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
			totaldis=totaldis+dis;
			totaldis_step=totaldis_step+dis_step;
			//System.out.println("!!!!!!!!!"+dis);
			//System.out.println("!!!!!!!!!"+dis_step);
		}
		//System.out.println("!!!");
		
		for(int j=3;j<=5;j++){
			try {
				fileReader = new FileReader(RSS_files[j]);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			bfReader = new BufferedReader(fileReader);
			String name_and_state="";
			double dis=0;
			double dis_step=0;
			for(int i=0;i<=12;i++){
				try {
					if(i!=0){
						name_and_state = bfReader.readLine();
						name_and_state = bfReader.readLine();
					}
					name_and_state = bfReader.readLine();
					name_and_state = bfReader.readLine();
					
					////////////////////////////////////////////
					String[] tt=name_and_state.split(" ");
					name_and_state="";
					for(int jj=0;jj<=18*5-1;jj++){
						if(jj==0){
							for(int ll=1;ll<=4;ll++){
								while(tt[jj].equals("IWCTAP5")){//tt[jj].equals("IWCTAP5")||tt[jj].equals("IWCTAP4")||tt[jj].equals("IWCTAP3")||tt[jj].equals("IWCTAP2")
									jj=jj+18;
								}
								}
							name_and_state=name_and_state+tt[jj];
						}else{
							for(int ll=1;ll<=4;ll++){
								while(tt[jj].equals("IWCTAP5")){//tt[jj].equals("IWCTAP5")||tt[jj].equals("IWCTAP4")||tt[jj].equals("IWCTAP3")||tt[jj].equals("IWCTAP2")
									jj=jj+18;
								}
								}
							name_and_state=name_and_state+" "+tt[jj];
						}
					}
					
					Coordinate coordinate = function.doFunction(name_and_state, maps);
				    //System.out.println(coordinate.getX() + " " + coordinate.getY());
					if(i!=0){
					Coordinate coordinate_step = function_step.doFunction(previouspo,Integer.parseInt(tt[tt.length-1]),name_and_state, maps);
					//System.out.println(coordinate_step.getX()+" "+coordinate_step.getY());
					previouspo.setCoordinate(coordinate_step.getX(), coordinate_step.getY());
					if(Math.sqrt((coordinate_step.getX()-j)*(coordinate_step.getX()-j)+(coordinate_step.getY()-i)*(coordinate_step.getY()-i))>dis_step_max){
						dis_step_max=Math.sqrt((coordinate_step.getX()-j)*(coordinate_step.getX()-j)+(coordinate_step.getY()-i)*(coordinate_step.getY()-i));
					}
					if(Math.sqrt((coordinate_step.getX()-j)*(coordinate_step.getX()-j)+(coordinate_step.getY()-i)*(coordinate_step.getY()-i))<dis_step_min){
						dis_step_min=Math.sqrt((coordinate_step.getX()-j)*(coordinate_step.getX()-j)+(coordinate_step.getY()-i)*(coordinate_step.getY()-i));
					}
					dis_step=dis_step+Math.sqrt((coordinate_step.getX()-j)*(coordinate_step.getX()-j)+(coordinate_step.getY()-i)*(coordinate_step.getY()-i));
					double tpp=Math.sqrt((coordinate_step.getX()-j)*(coordinate_step.getX()-j)+(coordinate_step.getY()-i)*(coordinate_step.getY()-i));
					for(double ll=0;ll<=9.5;ll=ll+0.5){
						if(tpp>=ll && tpp<ll+0.5){
							dis_step_con[(int) (ll*2)]=dis_step_con[(int) (ll*2)]+1;
						}
					}
					if(tpp>=10){
						dis_step_con[19]=dis_step_con[19]+1;
					}
					}
					if(Math.sqrt((coordinate.getX()-j)*(coordinate.getX()-j)+(coordinate.getY()-i)*(coordinate.getY()-i))>dis_max){
						dis_max=Math.sqrt((coordinate.getX()-j)*(coordinate.getX()-j)+(coordinate.getY()-i)*(coordinate.getY()-i));
					}
					if(Math.sqrt((coordinate.getX()-j)*(coordinate.getX()-j)+(coordinate.getY()-i)*(coordinate.getY()-i))<dis_min){
						dis_min=Math.sqrt((coordinate.getX()-j)*(coordinate.getX()-j)+(coordinate.getY()-i)*(coordinate.getY()-i));
					}
					dis=dis+Math.sqrt((coordinate.getX()-j)*(coordinate.getX()-j)+(coordinate.getY()-i)*(coordinate.getY()-i));
				
					double tp=Math.sqrt((coordinate.getX()-j)*(coordinate.getX()-j)+(coordinate.getY()-i)*(coordinate.getY()-i));
					for(double ll=0;ll<=9.5;ll=ll+0.5){
						if(tp>=ll && tp<ll+0.5){
							dis_con[(int) (ll*2)]=dis_con[(int) (ll*2)]+1;
						}
					}
					if(tp>=10){
						//System.out.println("!!!!!!!!");
						dis_con[19]=dis_con[19]+1;
					}
					
					
					
					
					
					
					
					
					
					
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
			totaldis=totaldis+dis;
			totaldis_step=totaldis_step+dis_step;
		}
		
		System.out.println("平均误差"+totaldis/(6*12));
		System.out.println("平均误差_Step"+totaldis_step/(6*12));
		System.out.println("最大误差"+dis_max);
		System.out.println("最小误差"+dis_min);
		System.out.println("最大误差_Step"+dis_step_max);
		System.out.println("最小误差_Step"+dis_step_min);
		System.out.println("误差分布");
		for(int o=0;o<=19;o++){
			System.out.println(""+dis_con[o]);
		}
		System.out.println("误差分布_Step");
		for(int o=0;o<=19;o++){
			System.out.println(""+dis_step_con[o]);
		}

		
		
		/////////////////////以下是十个普通AP性能的计算///////////////////////
		for(int jj=0;jj<=19;jj++){
			dis_con[jj]=0;
			dis_step_con[jj]=0;
		}
		 dis_max=0;
		 dis_min=100;
		 dis_step_max=0;
		 dis_step_min=100;
		a = 1;
		b = 0;
		maps = new Map[MapNum];
//		for (int i = 0; i < MapNum; ++i){
//			Map map = new Map();
//			File map_file = new File("E:/eclipse程序/LocalizationTest/map_10/IWCTAP" + a + "_" + b +".txt");
//			map.initiate(map_file);
//			maps[i] = map;
//			b = b + 1;
//			if (b == 2){
//				a = a + 1;
//				b = 0;
//			}
//		}
		
		Map map = new Map();
		File map_file = new File("E:/eclipse程序/LocalizationTest/map_10/IWCTAP" + 1 + "_" + 0 +".txt");
		map = new Map();
		map.initiate(map_file);
		maps[0] = map;
		map_file = new File("E:/eclipse程序/LocalizationTest/map_10/IWCTAP" + 1 + "_" + 1 +".txt");
		map = new Map();
		map.initiate(map_file);
		maps[1] = map;
		map_file = new File("E:/eclipse程序/LocalizationTest/map_10/IWCTAP" + 5 + "_" + 0 +".txt");
		map = new Map();
		map.initiate(map_file);
		maps[2] = map;
		map_file = new File("E:/eclipse程序/LocalizationTest/map_10/IWCTAP" + 5 + "_" + 1 +".txt");
		map = new Map();
		map.initiate(map_file);
		maps[3] = map;
		map_file = new File("E:/eclipse程序/LocalizationTest/map_10/IWCTAP" + 2 + "_" + 0 +".txt");
		map = new Map();
		map.initiate(map_file);
		maps[4] = map;
		map_file = new File("E:/eclipse程序/LocalizationTest/map_10/IWCTAP" + 2 + "_" + 1 +".txt");
		map = new Map();
		map.initiate(map_file);
		maps[5] = map;
		map_file = new File("E:/eclipse程序/LocalizationTest/map_10/IWCTAP" + 4 + "_" + 0 +".txt");
		map = new Map();
		map.initiate(map_file);
		maps[6] = map;
		map_file = new File("E:/eclipse程序/LocalizationTest/map_10/IWCTAP" + 4 + "_" + 1 +".txt");
		map = new Map();
		map.initiate(map_file);
		maps[7] = map;
		
		function = new Function();
		System.out.println("OK");
		
		
		RSS_files[0]=new File("E:/eclipse程序/LocalizationTest/result2/192.168.137.203.txt");
		RSS_files[1]=new File("E:/eclipse程序/LocalizationTest/result2/192.168.137.157.txt");
		RSS_files[2]=new File("E:/eclipse程序/LocalizationTest/result2/192.168.137.182.txt");
		RSS_files[3]=new File("E:/eclipse程序/LocalizationTest/result2/192.168.137.74.txt");
		RSS_files[4]=new File("E:/eclipse程序/LocalizationTest/result2/192.168.137.184.txt");
		RSS_files[5]=new File("E:/eclipse程序/LocalizationTest/result2/192.168.137.178.txt");
		//File RSS_file = new File("E:/eclipse程序/LocalizationTest/result/192.168.137.178.txt");
		
		fileReader = null;
		bfReader = null;
		totaldis=0;
		totaldis_step=0;
		
		for(int j=0;j<=2;j++){
			try {
				fileReader = new FileReader(RSS_files[j]);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			bfReader = new BufferedReader(fileReader);
			String name_and_state="";
			double dis=0;
			double dis_step=0;
			for(int i=0;i<=10;i++){
				try {
					if(i!=0){
						name_and_state = bfReader.readLine();
						name_and_state = bfReader.readLine();					
					}
//					name_and_state = bfReader.readLine();
//					name_and_state = bfReader.readLine();
					
					String tmp = bfReader.readLine();
					name_and_state = bfReader.readLine();
					String[] tmpp_name_and_state =name_and_state.split(" ");
					String[] tmpp = tmp.split(" ");
					String[] st=new String[5];
					int p=0;
					while(!tmpp[p].equals("IWCTAP6")){p=p+1;}
					st[0]=tmpp[p+4];st[1]=tmpp[p+4];st[2]=tmpp[p+4];st[3]=tmpp[p+4];st[4]=tmpp[p+4];
					p=0;
					while(!tmpp_name_and_state[p].equals("IWCTAP1")){p=p+1;}
					tmpp_name_and_state[p+12]=st[0];tmpp_name_and_state[p+13]=st[1];tmpp_name_and_state[p+14]=st[2];tmpp_name_and_state[p+15]=st[3];tmpp_name_and_state[p+15]=st[4];
					
					p=0;
					while(!tmpp[p].equals("IWCTAP7")){p=p+1;}
					st[0]=tmpp[p+4];st[1]=tmpp[p+4];st[2]=tmpp[p+4];st[3]=tmpp[p+4];st[4]=tmpp[p+4];
					p=0;
					while(!tmpp_name_and_state[p].equals("IWCTAP2")){p=p+1;}
					tmpp_name_and_state[p+12]=st[0];tmpp_name_and_state[p+13]=st[1];tmpp_name_and_state[p+14]=st[2];tmpp_name_and_state[p+15]=st[3];tmpp_name_and_state[p+15]=st[4];
					
					p=0;
					while(!tmpp[p].equals("IWCTAP8")){p=p+1;}
					st[0]=tmpp[p+4];st[1]=tmpp[p+4];st[2]=tmpp[p+4];st[3]=tmpp[p+4];st[4]=tmpp[p+4];
					p=0;
					while(!tmpp_name_and_state[p].equals("IWCTAP3")){p=p+1;}
					tmpp_name_and_state[p+12]=st[0];tmpp_name_and_state[p+13]=st[1];tmpp_name_and_state[p+14]=st[2];tmpp_name_and_state[p+15]=st[3];tmpp_name_and_state[p+15]=st[4];

					p=0;
					while(!tmpp[p].equals("IWCTAP9")){p=p+1;}
					st[0]=tmpp[p+4];st[1]=tmpp[p+4];st[2]=tmpp[p+4];st[3]=tmpp[p+4];st[4]=tmpp[p+4];
					p=0;
					while(!tmpp_name_and_state[p].equals("IWCTAP4")){p=p+1;}
					tmpp_name_and_state[p+12]=st[0];tmpp_name_and_state[p+13]=st[1];tmpp_name_and_state[p+14]=st[2];tmpp_name_and_state[p+15]=st[3];tmpp_name_and_state[p+15]=st[4];
					
					p=0;
					while(!tmpp[p].equals("IWCTAP10")){p=p+1;}
					st[0]=tmpp[p+4];st[1]=tmpp[p+4];st[2]=tmpp[p+4];st[3]=tmpp[p+4];st[4]=tmpp[p+4];
					p=0;
					while(!tmpp_name_and_state[p].equals("IWCTAP5")){p=p+1;}
					tmpp_name_and_state[p+12]=st[0];tmpp_name_and_state[p+13]=st[1];tmpp_name_and_state[p+14]=st[2];tmpp_name_and_state[p+15]=st[3];tmpp_name_and_state[p+15]=st[4];
					
					
					
					
					name_and_state="";
					for(int k=0;k<=tmpp_name_and_state.length-1;k++){
						if(k==0){
							name_and_state=name_and_state+tmpp_name_and_state[k];
						}else{
							name_and_state=name_and_state+" "+tmpp_name_and_state[k];
						}
					}
					//System.out.println("11111111111111");
					System.out.println("11"+name_and_state);
					
					////////////////////////////////////////////
					String[] tt=name_and_state.split(" ");
					name_and_state="";
					for(int jj=0;jj<=18*5-1;jj++){
						if(jj==0){
							for(int ll=1;ll<=4;ll++){
								while(tt[jj].equals("IWCTAP3")){//tt[jj].equals("IWCTAP5")||tt[jj].equals("IWCTAP4")||tt[jj].equals("IWCTAP3")||tt[jj].equals("IWCTAP2")
									jj=jj+18;
								}
								}
							name_and_state=name_and_state+tt[jj];
						}else{
							for(int ll=1;ll<=4;ll++){
								while(tt[jj].equals("IWCTAP3")){//tt[jj].equals("IWCTAP5")||tt[jj].equals("IWCTAP4")||tt[jj].equals("IWCTAP3")||tt[jj].equals("IWCTAP2")
									jj=jj+18;
									//System.out.println("11 "+jj+" "+tt.length);
									if(jj>tt.length){
										ll=5;break;
									}
								}
								}
							if(jj<=18*5-1){
							name_and_state=name_and_state+" "+tt[jj];
							System.out.println(jj+" "+tt[jj]);
							}
						}
					}
					
					System.out.println("22"+name_and_state);
					Coordinate coordinate = function.doFunction(name_and_state, maps);
					if(i!=0){
					Coordinate coordinate_step = function_step.doFunction(previouspo,Integer.parseInt(tt[tt.length-1]),name_and_state, maps);
					//System.out.println(coordinate_step.getX()+" "+coordinate_step.getY());
					previouspo.setCoordinate(coordinate_step.getX(), coordinate_step.getY());
					if(Math.sqrt((coordinate_step.getX()-j)*(coordinate_step.getX()-j)+(coordinate_step.getY()-i)*(coordinate_step.getY()-i))>dis_step_max){
						dis_step_max=Math.sqrt((coordinate_step.getX()-j)*(coordinate_step.getX()-j)+(coordinate_step.getY()-i)*(coordinate_step.getY()-i));
					}
					if(Math.sqrt((coordinate_step.getX()-j)*(coordinate_step.getX()-j)+(coordinate_step.getY()-i)*(coordinate_step.getY()-i))<dis_step_min){
						dis_step_min=Math.sqrt((coordinate_step.getX()-j)*(coordinate_step.getX()-j)+(coordinate_step.getY()-i)*(coordinate_step.getY()-i));
					}
					dis_step=dis_step+Math.sqrt((coordinate_step.getX()-j)*(coordinate_step.getX()-j)+(coordinate_step.getY()-i)*(coordinate_step.getY()-i));
					double tpp=Math.sqrt((coordinate_step.getX()-j)*(coordinate_step.getX()-j)+(coordinate_step.getY()-i)*(coordinate_step.getY()-i));
					for(double ll=0;ll<=9.5;ll=ll+0.5){
						if(tpp>=ll && tpp<ll+0.5){
							dis_step_con[(int) (ll*2)]=dis_step_con[(int) (ll*2)]+1;
						}
					}
					if(tpp>=10){
						dis_step_con[19]=dis_step_con[19]+1;
					}
					}
					//System.out.println(coordinate.getX() + " " + coordinate.getY());
					if(Math.sqrt((coordinate.getX()-j)*(coordinate.getX()-j)+(coordinate.getY()-i)*(coordinate.getY()-i))>dis_max){
						dis_max=Math.sqrt((coordinate.getX()-j)*(coordinate.getX()-j)+(coordinate.getY()-i)*(coordinate.getY()-i));
					}
					if(Math.sqrt((coordinate.getX()-j)*(coordinate.getX()-j)+(coordinate.getY()-i)*(coordinate.getY()-i))<dis_min){
						dis_min=Math.sqrt((coordinate.getX()-j)*(coordinate.getX()-j)+(coordinate.getY()-i)*(coordinate.getY()-i));
					}
					dis=dis+Math.sqrt((coordinate.getX()-j)*(coordinate.getX()-j)+(coordinate.getY()-i)*(coordinate.getY()-i));

					double tp=Math.sqrt((coordinate.getX()-j)*(coordinate.getX()-j)+(coordinate.getY()-i)*(coordinate.getY()-i));
					for(double ll=0;ll<=9.5;ll=ll+0.5){
						if(tp>=ll && tp<ll+0.5){
							dis_con[(int) (ll*2)]=dis_con[(int) (ll*2)]+1;
						}
					}
					if(tp>=10){
						//System.out.println("!!!!!!!!");
						dis_con[19]=dis_con[19]+1;
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
			totaldis=totaldis+dis;
			totaldis_step=totaldis_step+dis_step;
		}
		
		
		for(int j=3;j<=5;j++){
			try {
				fileReader = new FileReader(RSS_files[j]);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			bfReader = new BufferedReader(fileReader);
			String name_and_state="";
			double dis=0;
			double dis_step=0;
			for(int i=0;i<=12;i++){
				try {
					if(i!=0){
						name_and_state = bfReader.readLine();
						name_and_state = bfReader.readLine();
					}
					String tmp = bfReader.readLine();
					name_and_state = bfReader.readLine();
					String[] tmpp_name_and_state =name_and_state.split(" ");
					String[] tmpp = tmp.split(" ");
					String[] st=new String[5];
					int p=0;
					while(!tmpp[p].equals("IWCTAP6")){p=p+1;}
					st[0]=tmpp[p+4];st[1]=tmpp[p+4];st[2]=tmpp[p+4];st[3]=tmpp[p+4];st[4]=tmpp[p+4];
					p=0;
					while(!tmpp_name_and_state[p].equals("IWCTAP1")){p=p+1;}
					tmpp_name_and_state[p+12]=st[0];tmpp_name_and_state[p+13]=st[1];tmpp_name_and_state[p+14]=st[2];tmpp_name_and_state[p+15]=st[3];tmpp_name_and_state[p+15]=st[4];
					
					p=0;
					while(!tmpp[p].equals("IWCTAP7")){p=p+1;}
					st[0]=tmpp[p+4];st[1]=tmpp[p+4];st[2]=tmpp[p+4];st[3]=tmpp[p+4];st[4]=tmpp[p+4];
					p=0;
					while(!tmpp_name_and_state[p].equals("IWCTAP2")){p=p+1;}
					tmpp_name_and_state[p+12]=st[0];tmpp_name_and_state[p+13]=st[1];tmpp_name_and_state[p+14]=st[2];tmpp_name_and_state[p+15]=st[3];tmpp_name_and_state[p+15]=st[4];
					
					p=0;
					while(!tmpp[p].equals("IWCTAP8")){p=p+1;}
					st[0]=tmpp[p+4];st[1]=tmpp[p+4];st[2]=tmpp[p+4];st[3]=tmpp[p+4];st[4]=tmpp[p+4];
					p=0;
					while(!tmpp_name_and_state[p].equals("IWCTAP3")){p=p+1;}
					tmpp_name_and_state[p+12]=st[0];tmpp_name_and_state[p+13]=st[1];tmpp_name_and_state[p+14]=st[2];tmpp_name_and_state[p+15]=st[3];tmpp_name_and_state[p+15]=st[4];

					p=0;
					while(!tmpp[p].equals("IWCTAP9")){p=p+1;}
					st[0]=tmpp[p+4];st[1]=tmpp[p+4];st[2]=tmpp[p+4];st[3]=tmpp[p+4];st[4]=tmpp[p+4];
					p=0;
					while(!tmpp_name_and_state[p].equals("IWCTAP4")){p=p+1;}
					tmpp_name_and_state[p+12]=st[0];tmpp_name_and_state[p+13]=st[1];tmpp_name_and_state[p+14]=st[2];tmpp_name_and_state[p+15]=st[3];tmpp_name_and_state[p+15]=st[4];
					
					p=0;
					while(!tmpp[p].equals("IWCTAP10")){p=p+1;}
					st[0]=tmpp[p+4];st[1]=tmpp[p+4];st[2]=tmpp[p+4];st[3]=tmpp[p+4];st[4]=tmpp[p+4];
					p=0;
					while(!tmpp_name_and_state[p].equals("IWCTAP5")){p=p+1;}
					tmpp_name_and_state[p+12]=st[0];tmpp_name_and_state[p+13]=st[1];tmpp_name_and_state[p+14]=st[2];tmpp_name_and_state[p+15]=st[3];tmpp_name_and_state[p+15]=st[4];
					
					name_and_state="";
					for(int k=0;k<=tmpp_name_and_state.length-1;k++){
						if(k==0){
							name_and_state=name_and_state+tmpp_name_and_state[k];
						}else{
							name_and_state=name_and_state+" "+tmpp_name_and_state[k];
						}
					}
					
					
					
					////////////////////////////////////////////
					String[] tt=name_and_state.split(" ");
					name_and_state="";
					for(int jj=0;jj<=18*5-1;jj++){
						if(jj==0){
							for(int ll=1;ll<=4;ll++){
								while(tt[jj].equals("IWCTAP3")){//tt[jj].equals("IWCTAP5")||tt[jj].equals("IWCTAP4")||tt[jj].equals("IWCTAP3")||tt[jj].equals("IWCTAP2")
									jj=jj+18;
								}
								}
							name_and_state=name_and_state+tt[jj];
						}else{
							for(int ll=1;ll<=4;ll++){
								while(tt[jj].equals("IWCTAP3")){//tt[jj].equals("IWCTAP5")||tt[jj].equals("IWCTAP4")||tt[jj].equals("IWCTAP3")||tt[jj].equals("IWCTAP2")
									jj=jj+18;
									if(jj>tt.length){
										ll=5;break;
									}
								}
								}
							if(jj<=18*5-1){
							name_and_state=name_and_state+" "+tt[jj];
							System.out.println(jj+" "+tt[jj]);
							}
						}
					}
					
					//System.out.println(name_and_state);
					Coordinate coordinate = function.doFunction(name_and_state, maps);
					if(i!=0){
					Coordinate coordinate_step = function_step.doFunction(previouspo,Integer.parseInt(tt[tt.length-1]),name_and_state, maps);
					//System.out.println(coordinate_step.getX()+" "+coordinate_step.getY());
					previouspo.setCoordinate(coordinate_step.getX(), coordinate_step.getY());
					if(Math.sqrt((coordinate_step.getX()-j)*(coordinate_step.getX()-j)+(coordinate_step.getY()-i)*(coordinate_step.getY()-i))>dis_step_max){
						dis_step_max=Math.sqrt((coordinate_step.getX()-j)*(coordinate_step.getX()-j)+(coordinate_step.getY()-i)*(coordinate_step.getY()-i));
					}
					if(Math.sqrt((coordinate_step.getX()-j)*(coordinate_step.getX()-j)+(coordinate_step.getY()-i)*(coordinate_step.getY()-i))<dis_step_min){
						dis_step_min=Math.sqrt((coordinate_step.getX()-j)*(coordinate_step.getX()-j)+(coordinate_step.getY()-i)*(coordinate_step.getY()-i));
					}
					dis_step=dis_step+Math.sqrt((coordinate_step.getX()-j)*(coordinate_step.getX()-j)+(coordinate_step.getY()-i)*(coordinate_step.getY()-i));
					double tpp=Math.sqrt((coordinate_step.getX()-j)*(coordinate_step.getX()-j)+(coordinate_step.getY()-i)*(coordinate_step.getY()-i));
					for(double ll=0;ll<=9.5;ll=ll+0.5){
						if(tpp>=ll && tpp<ll+0.5){
							dis_step_con[(int) (ll*2)]=dis_step_con[(int) (ll*2)]+1;
						}
					}
					if(tpp>=10){
						dis_step_con[19]=dis_step_con[19]+1;
					}
					}
					//System.out.println(coordinate.getX() + " " + coordinate.getY());
					if(Math.sqrt((coordinate.getX()-j)*(coordinate.getX()-j)+(coordinate.getY()-i)*(coordinate.getY()-i))>dis_max){
						dis_max=Math.sqrt((coordinate.getX()-j)*(coordinate.getX()-j)+(coordinate.getY()-i)*(coordinate.getY()-i));
					}
					if(Math.sqrt((coordinate.getX()-j)*(coordinate.getX()-j)+(coordinate.getY()-i)*(coordinate.getY()-i))<dis_min){
						dis_min=Math.sqrt((coordinate.getX()-j)*(coordinate.getX()-j)+(coordinate.getY()-i)*(coordinate.getY()-i));
					}
					dis=dis+Math.sqrt((coordinate.getX()-j)*(coordinate.getX()-j)+(coordinate.getY()-i)*(coordinate.getY()-i));

					double tp=Math.sqrt((coordinate.getX()-j)*(coordinate.getX()-j)+(coordinate.getY()-i)*(coordinate.getY()-i));
					for(double ll=0;ll<=9.5;ll=ll+0.5){
						if(tp>=ll && tp<ll+0.5){
							dis_con[(int) (ll*2)]=dis_con[(int) (ll*2)]+1;
						}
					}
					if(tp>=10){
						//System.out.println("!!!!!!!!");
						dis_con[19]=dis_con[19]+1;
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
			totaldis=totaldis+dis;
			totaldis_step=totaldis_step+dis_step;
		}
		
		System.out.println("平均误差"+totaldis/(6*12));
		System.out.println("平均误差_Step"+totaldis_step/(6*12));
		System.out.println("最大误差"+dis_max);
		System.out.println("最小误差"+dis_min);
		System.out.println("最大误差_Step"+dis_step_max);
		System.out.println("最小误差_Step"+dis_step_min);
		System.out.println("误差分布");
		for(int o=0;o<=19;o++){
			System.out.println(""+dis_con[o]);
		}
		System.out.println("误差分布_Step");
		for(int o=0;o<=19;o++){
			System.out.println(""+dis_step_con[o]);
		}

	}
}
