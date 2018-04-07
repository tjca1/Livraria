package br.com.saraiva.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Locale;
import br.com.saraiva.exeception.ResourceNotFoundException;

/**
 * 
 * @author Thiago Araujo  - tjca1@hotmail.com - 07/04/2018
 *
 */

public class StringUtil {
	public static DecimalFormat df = new DecimalFormat("0.00");
	
	public static Long toLong(String value) {
		try {
			return new Long(toBigDecimal(value).longValue());
		} catch (Exception e) {
			return 0L;
		}
	}
	public static Float toFloat(String value) {
		try {
			return new Float(toBigDecimal(value).floatValue());
		} catch (Exception e) {
			return 0F;
		}
	}
	
	public static Double toDouble(String value) {
		try {
			return new Double(toBigDecimal(value).doubleValue());
		} catch (Exception e) {
			return 0D;
		}
	}
	
	public static BigDecimal toBigDecimal(String value) {
		Locale.setDefault(new Locale("pt", "BR"));
		try {
			df.setParseBigDecimal(true);
			BigDecimal bd = (BigDecimal) df.parse(value);
			return bd;
		} catch (ParseException e) {
			throw new ResourceNotFoundException(e);
		}
	}
	
	public static boolean isNull (Object ob) {
		if(null == ob) {
			return true;
		}else {
			return false;
		}
	}
	

	
	
	

}
