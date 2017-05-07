package com.mabsisa.common;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Test {

	public static void main(String[] args) {
		DateFormat dateFormat = new SimpleDateFormat("MMyyyy");
		Date date = new Date();
		System.out.println(dateFormat.format(date));
	}

}
