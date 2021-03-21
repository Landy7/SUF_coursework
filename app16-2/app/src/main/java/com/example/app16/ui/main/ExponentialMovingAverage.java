package com.example.app16.ui.main;

import java.util.Arrays;

public class ExponentialMovingAverage {

	private double[] periodEmas;

	public ExponentialMovingAverage calculate(double[] prices, int period) throws Exception {

		double smoothingConstant = 2d / (period + 1);

		double[] periodSmas = new double[prices.length];
		this.periodEmas = new double[prices.length];

		SimpleMovingAverage sma = new SimpleMovingAverage();

		for (int i = (period - 1); i < prices.length; i++) {
			double[] slice = Arrays.copyOfRange(prices, 0, i + 1);
			double[] smaResults = sma.calculate(slice, period).getSMA();
			periodSmas[i] = smaResults[smaResults.length - 1];

			if (i == (period - 1)) {
				this.periodEmas[i] = periodSmas[i];
			} else if (i > (period - 1)) {
				this.periodEmas[i] = (prices[i] - periodEmas[i - 1]) * smoothingConstant
						+ this.periodEmas[i - 1];
			}

			this.periodEmas[i] = NumberFormatter.round(this.periodEmas[i]);
		}

		return this;
	}

	public double[] getEMA() {
		return this.periodEmas;
	}
}
