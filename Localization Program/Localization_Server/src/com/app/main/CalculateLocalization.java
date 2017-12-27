package com.app.main;

import java.io.File;

import com.app.function.Function;
import com.app.function.Function_step;
import com.app.util.Coordinate;
import com.app.util.Map;

public class CalculateLocalization {
	/**
	 * @param args
	 */
	private  Map[] maps = new Map[2];
	private  Function function;
	private  Function_step function_step;
	
	public CalculateLocalization(){
		// TODO Auto-generated method stub
//				Map hehe_0 = new Map();
//				File hehe_0_file = new File("E:/eclipse³ÌÐò/LocalizationServer/hehe_0_input.txt");
//				hehe_0.initiate(hehe_0_file);
//				Map hehe_1 = new Map();
//				File hehe_1_file = new File("E:/eclipse³ÌÐò/LocalizationServer/hehe_1_input.txt");
//				hehe_1.initiate(hehe_1_file);
//				maps[0] = hehe_0; maps[1] = hehe_1;
				function = new Function();
				function_step= new Function_step();
		
		Map[] maps = new Map[10];
		int a = 1;
		int b = 0;
		for (int i = 0; i < 10; ++i){
			Map map = new Map();
			File map_file = new File("E:/eclipse³ÌÐò/LocalizationServer_for_test/IWCTAP" + a + "_" + b +".txt");
			map.initiate(map_file);
			maps[i] = map;
			b = b + 1;
			if (b == 2){
				a = a + 1;
				b = 0;
			}
		}
	}
	public Coordinate GetPosition(String command) {
		
		//command = "APNAME: hehe STATE1: 0 RSS: -41 -41 STATE2: 1 RSS: -41 -41";
		Coordinate coordinate = function.doFunction(command, maps);
		System.out.println(coordinate.getX() + " " + coordinate.getY());
		return coordinate;
	}
	public Coordinate GetPosition_step(Coordinate current, int step, String command) {
		
		//command = "APNAME: hehe STATE1: 0 RSS: -41 -41 STATE2: 1 RSS: -41 -41";
		Coordinate coordinate = function_step.doFunction(current, step, command, maps);
		//doFunction(Coordinate current, int step, String command, Map[] maps){
		System.out.println(coordinate.getX() + " " + coordinate.getY());
		return coordinate;
	}
}
