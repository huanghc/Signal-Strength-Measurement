package com.app.function;

import com.app.util.Coordinate;
import com.app.util.Map;

public class Function_step {
	
	public Coordinate doFunction(Coordinate current, int step, String command, Map[] maps){
		//�ڴ˿�ʼ���ַ������д���
		int x = maps[0].getX_number();
		int y = maps[1].getY_number();
		double[][] possibility = new double[x][y];
		//�Ը���������г�ʼ��
		for (int i = 0; i < x;++i){
			for (int j = 0; j < y; ++j){
				possibility[i][j] = 1;
			}
		}
		//���һ��Ȩֵ����
		double[][] weight = new double[x][y];
		for (int i = 0; i < x;++i){
			for (int j = 0; j < y; ++j){
				weight[i][j] = 1;
			}
		}
		weight = weight_init(weight, current, step);
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
		//��������м�Ȩ
		for (int i = 0; i < x; ++i){
			for (int j = 0; j < y; ++j){
				possibility[i][j] = possibility[i][j] * weight[i][j];
			}
		}
		Coordinate coordinate = new Coordinate();
		//�ҵ����ʾ����и������Ԫ�ص�λ��
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
	
	private double[][] weight_init(double [][]input, Coordinate current, int step){
		int a = input.length;
		int b = input[0].length;
		if ((current.getX() >= a) | (current.getY() >= b)){
			System.out.println("Your current location is out of range");
		}
		int X = current.getX();
		int Y = current.getY();
		int x_low = X-step > 0 ? X-step : 0;
		int x_high = X+step < a-1 ? X+step : a-1;
		int y_low = Y-step > 0 ? Y-step : 0;
		int y_high = Y+step < b-1 ? Y+step : b-1;
		for (int i = x_low; i <= x_high; ++i){
			for (int j = y_low; j <= y_high; ++j){
				input[i][j] = 1.2;
			}
		}
		return input;
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
