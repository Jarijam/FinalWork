package com.controller;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class datecal_test {

	public static void main(String[] args) {
		//현재시간을 구하는 과정인데 내가 필요한건 로그의 년월일시분초를 미리세컨화 시키는것
		long time = System.currentTimeMillis();
		System.out.println(time);
		//1638755068554
		
		
		Date conFromDate = new Date();
		conFromDate.setHours(20);
		conFromDate.setSeconds(20);
		System.out.println(conFromDate);
		SimpleDateFormat format = new SimpleDateFormat("yyyy,MM,dd HH:mm:ss");
//		String dtime = format.format(conFromDate);
//		System.out.println(dtime);
		
//		long ttl = conFromDate.parse ( "DEC, 06, 2021 10:10:10" );
		//("DEC,"+D+", "+Y+" "+HH+":"+MM+":"+SS)
//		System.out.println ( ttl );
		//Date형식으로 받을 수 있게 로그 값을 r로 찍고 찍은 행마다 add..
		String MM= null;
		String SS= null;
		long ttl = conFromDate.parse ( "DEC, 04, 2021 10:34:30" );
		MM = null;
		SS = null;
		System.out.println(ttl);
	}

}
