package com.example.app16.ui.main;

public class MACDAVG
{
    private double[] macdavg;

    public MACDAVG calculate(double[] prices, int period, int fastPeriod, int slowPeriod) throws Exception {

        this.macdavg = new double[prices.length];

        ExponentialMovingAverage ema= new ExponentialMovingAverage();
        ema.calculate(prices, period);

        MovingAverageConvergenceDivergence macd = new MovingAverageConvergenceDivergence();
        macd.calculate(prices, fastPeriod,slowPeriod);

        for (int i = period - 1; i < prices.length; i++) {
            // this.macdavg[i] = NumberFormatter.round((ema.getEMA()macd.getMACD())[i]);
        }

        return this;
    }

    public double[] getMACDAVG() {
        return this.macdavg;
    }
}
