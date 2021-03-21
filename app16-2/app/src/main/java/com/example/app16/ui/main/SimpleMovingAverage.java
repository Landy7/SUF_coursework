package com.example.app16.ui.main;

import android.util.Log;

import java.util.Arrays;

public class SimpleMovingAverage {

	private double[] results;

	public SimpleMovingAverage calculate(double[] prices, int period) throws Exception {

		this.results = new double[prices.length];

		for (int i = 0; i < prices.length - period; i++) {
			this.results[i] = NumberFormatter
					.round((Arrays.stream(Arrays.copyOfRange(prices, i, (i + period))).sum()) / period);
		}

		return this;
	}

	public double[] getSMA() {
		return this.results;
	}

}
