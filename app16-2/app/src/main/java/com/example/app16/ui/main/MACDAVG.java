package com.example.app16.ui.main;

public class MACDAVG
{
    private double[] macdavg;

    public MACDAVG calculate(double[] prices, int period, int fastPeriod, int slowPeriod) throws Exception {

        this.macdavg = new double[prices.length];

        MACD macd = new MACD();
        macd.calculate(prices, fastPeriod,slowPeriod);

        EMA ema= new EMA();
        ema.calculate(macd.getMACD(), period);



        for (int i = period - 1; i < prices.length; i++) {
            this.macdavg[i] = NumberFormatter.round(ema.getEMA()[i]);
        }

        return this;
    }

    public double[] getMACDAVG() {
        return this.macdavg;
    }
}
