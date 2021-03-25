package com.example.app16.ui.main;

import java.util.Arrays;

public class SMA {

	private double[] smaResult;

	public SMA calculate(double[] prices, int period) throws Exception {

		this.smaResult = new double[prices.length];

		for (int i = 0; i <= prices.length - period; i++) {
			this.smaResult[(period + i - 1)] = FormatNumber
					.round((Arrays.stream(Arrays.copyOfRange(prices, i, (i + period))).sum()) / period);
		}

		return this;
	}

	public double[] getSMA() {
		return this.smaResult;
	}

}
