package com.example.app16.ui.main;

import java.math.BigDecimal;

public class FormatNumber {

	private FormatNumber() {
	}

	public static double round(double value) {
		return FormatNumber.round(value, 5);
	}

	public static double round(double value, int digits) {
		BigDecimal bigDecimal = new BigDecimal(value);
		bigDecimal = bigDecimal.setScale(digits, BigDecimal.ROUND_HALF_UP);
		return bigDecimal.doubleValue();
	}

}
