package com.app.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Map {
	private Grid[][] grid;
	private int x_number;
	public Grid[][] getGrid() {
		return grid;
	}

	public void setGrid(Grid[][] grid) {
		this.grid = grid;
	}
	private int y_number;
	private String apName;
	private int apState;
	public int getX_number() {
		return x_number;
	}

	public void setX_number(int x_number) {
		this.x_number = x_number;
	}

	public int getY_number() {
		return y_number;
	}

	public void setY_number(int y_number) {
		this.y_number = y_number;
	}
	private FileReader fileReader = null;
	private BufferedReader bfReader = null;
	//需要在initiate方法中加入初始化AP和STATE的方法
	public void initiate(File file){
		try {
			fileReader = new FileReader(file);
			bfReader = new BufferedReader(fileReader);
			String name_and_state = bfReader.readLine();
			String[] sub = name_and_state.split(" ");
			apName = sub[0];
			apState = Integer.parseInt(sub[1]);
			String title = bfReader.readLine();
			x_number = Integer.parseInt(title.split(" ")[0]);
			y_number = Integer.parseInt(title.split(" ")[1]);
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!"+x_number);
			grid = new Grid[x_number][y_number];
			//这里每个元素都要初始化一下，不知道为什么
			for (int i = 0; i < x_number; ++i){
				grid[i] = new Grid[y_number];
				for (int j = 0; j < y_number; ++j){
					grid[i][j] = new Grid();
				}
			}
			String line = null;
			int a = 0,b = 0;
			while ((line = bfReader.readLine())!=null){
				String[] data_str= line.split(" ");
				double[] data = new double[151];
				for(int i = 0; i < 151; ++i){
					data[i] = Double.parseDouble(data_str[i]);
				}
				grid[a][b].setGrid(data);
				b++;
				if (b == y_number){
					b = 0;
					a += 1;
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			try{
				bfReader.close();
				fileReader.close();
			} catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public String getApName() {
		return apName;
	}
	public void setApName(String apName) {
		this.apName = apName;
	}
	public int getApState() {
		return apState;
	}
	public void setApState(int apState) {
		this.apState = apState;
	}
}
