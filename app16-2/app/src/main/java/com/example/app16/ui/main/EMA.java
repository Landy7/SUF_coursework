package com.example.app16.ui.main;

import java.util.Arrays;

public class EMA {

	private double[] emaResult;

	public EMA calculate(double[] prices, int period) throws Exception {

		double alpha = 2d / (period + 1);

		double[] sams = new double[prices.length];
		this.emaResult = new double[prices.length];

		SMA sma = new SMA();

		for (int i = (period - 1); i < prices.length; i++) {
			double[] part = Arrays.copyOfRange(prices, 0, i + 1);
			double[] smaResults = sma.calculate(part, period).getSMA();
			sams[i] = smaResults[smaResults.length - 1];

			if (i == (period - 1)) {
				this.emaResult[i] = sams[i];
			} else if (i > (period - 1)) {
				this.emaResult[i] = (prices[i] - emaResult[i - 1]) * alpha
						+ this.emaResult[i - 1];
			}

			this.emaResult[i] = FormatNumber.round(this.emaResult[i]);
		}

		return this;
	}

	public double[] getEMA() {
		return this.emaResult;
	}
}
