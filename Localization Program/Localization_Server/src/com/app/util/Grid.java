package com.app.util;

public class Grid {
	public double[] rate = new double[101];

	
	public double[] getRate() {
		return rate;
	}

	public void setRate(double[] rate) {
		this.rate = rate;
	}
	
	public void setGrid(double[] data){
		if (data.length != 151){
			System.out.println("Grid set failed!");
		} else {
			rate = new double[151];
			for (int i = 0; i < 151; ++i){
				this.rate[i] = data[150-i];
			}
		}
	}
}
