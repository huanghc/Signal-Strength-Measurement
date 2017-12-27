package com.app.function;

import com.app.util.Coordinate;
import com.app.util.Map;

public class Function {
	
	public Coordinate doFunction(String command, Map[] maps){
		//在此开始对字符串进行处理
		int x = maps[0].getX_number();
		int y = maps[1].getY_number();
		double[][] possibility = new double[x][y];
		//对概率数组进行初始化
		for (int i = 0; i < x;++i){
			for (int j = 0; j < y; ++j){
				possibility[i][j] = 1;
			}
		}
		String[] one = command.split("APNAME: ");
		int ap_num = one.length - 1;
		int map_num = maps.length;
		if ((ap_num * 2) != map_num){
			System.out.println("match failure!");
			return null;
		}
		for (int i = 1; i <= ap_num; ++i){
			possibility = handleLine(possibility, one[i], maps);
		}
		Coordinate coordinate = new Coordinate();
		//找到概率矩阵中概率最大元素的位置
		double tmp = possibility[0][0];
		for (int i = 0; i < x; ++i){
			for (int j = 0; j < y; ++j){
				if (possibility[i][j] > tmp){
					tmp = possibility[i][j];
					coordinate.setCoordinate(i, j);
				}
			}
		}
		return coordinate;
	}
	
	private Map getMap(Map[] maps, String ap, int state){
		Map tmp = new Map();
		for (Map map:maps){
			if ((map.getApName().equals(ap)) & (map.getApState() == state)){
				tmp = map;
			}
		}
		return tmp;
	}
	
	private double[][] handleLine(double[][] input, String line, Map[] maps){
		int a = input.length;
		int b = input[0].length;
		double[][] output = new double[a][b];
		String[] one = line.split(" STATE1: ");
		String[] two = one[1].split(" STATE2: ");
		String apName = one[0];
		for (String data:two){
			String[] out = data.split(" RSS: ");
			int state = Integer.parseInt(out[0]);
			Map currentMap = getMap(maps, apName, state);
			String[] signal = out[1].split(" ");
			for (String intensity:signal){
				int intens = Integer.parseInt(intensity);
				for (int i = 0; i < a; ++i){
					for (int j = 0; j < b; ++j){
						output[i][j] = input[i][j] * currentMap.getGrid()[i][j].getRate()[-intens];
					}
				}
			}
			input = output;
		}
		return output;		
	}
}
