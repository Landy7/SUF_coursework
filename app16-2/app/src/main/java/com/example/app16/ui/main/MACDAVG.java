package com.example.app16.ui.main;

public class MACDAVG
{
    private double[] macdavg;

    public MACDAVG calculate(double[] prices, int period, int fastPeriod, int slowPeriod) throws Exception {

        this.macdavg = new double[prices.length];

//<<<<<<< Updated upstream
        MACD macd = new MACD();
        macd.calculate(prices, fastPeriod,slowPeriod);

        EMA ema= new EMA();
//=======
//        MovingAverageConvergenceDivergence macd = new MovingAverageConvergenceDivergence();
//        macd.calculate(prices, fastPeriod,slowPeriod);
//
//        ExponentialMovingAverage ema= new ExponentialMovingAverage();
//>>>>>>> Stashed changes
        ema.calculate(macd.getMACD(), period);



        for (int i = period - 1; i < prices.length; i++) {
//<<<<<<< Updated upstream
            this.macdavg[i] = FormatNumber.round(ema.getEMA()[i]);
//=======
//            this.macdavg[i] = NumberFormatter.round(ema.getEMA()[i]);
//>>>>>>> Stashed changes
        }

        return this;
    }

    public double[] getMACDAVG() {
        return this.macdavg;
    }
}
