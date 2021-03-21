package com.example.app16.ui.main;

public class MovingAverageConvergenceDivergence {

	private double[] macd;

	public MovingAverageConvergenceDivergence calculate(double[] prices, int fastPeriod, int slowPeriod) throws Exception {

		this.macd = new double[prices.length];

		ExponentialMovingAverage emaShort = new ExponentialMovingAverage();
		emaShort.calculate(prices, fastPeriod);

		ExponentialMovingAverage emaLong = new ExponentialMovingAverage();
		emaLong.calculate(prices, slowPeriod);

		for (int i = slowPeriod - 1; i < prices.length; i++) {
			this.macd[i] = NumberFormatter.round(emaShort.getEMA()[i] - emaLong.getEMA()[i]);
		}

		return this;
	}

	public double[] getMACD() {
		return this.macd;
	}
}
