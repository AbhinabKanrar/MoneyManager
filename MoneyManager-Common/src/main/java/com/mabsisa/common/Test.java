package com.mabsisa.common;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Test {

	public static void main(String[] args) {
		DateFormat dateFormat = new SimpleDateFormat("MMyyyy");
		Date date = new Date();
		System.out.println(dateFormat.format(date));
		
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 0);
		System.out.println(dateFormat.format(cal.getTime()));
		System.out.println();
		
	}

}
