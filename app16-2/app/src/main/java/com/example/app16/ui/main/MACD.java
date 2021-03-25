package com.example.app16.ui.main;

public class MACD {

	private double[] macdResult;

	public MACD calculate(double[] prices, int shortPeriod, int longPeriod) throws Exception {

		this.macdResult = new double[prices.length];

		EMA shortEMA = new EMA();
		shortEMA.calculate(prices, shortPeriod);

		EMA longEMA = new EMA();
		longEMA.calculate(prices, longPeriod);

		for (int i = longPeriod - 1; i < prices.length; i++) {
			this.macdResult[i] = FormatNumber.round(shortEMA.getEMA()[i] - longEMA.getEMA()[i]);
		}

		return this;
	}

	public double[] getMACD() {
		return this.macdResult;
	}
}
