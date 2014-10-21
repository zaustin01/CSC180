package mafia;

import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

public class SDFConverterTest {

	Converter<Date> dates = new SimpleDateFormatConverter();
	
	@Test
	public void testFormat(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2015);
		cal.set(Calendar.MONTH, 9);
		cal.set(Calendar.DATE, 21);
		Date d = cal.getTime();
		Assert.assertEquals("21 October 2015", dates.format(d));
	}
	
	@Test
	public void testParse(){
		Calendar cal =Calendar.getInstance();
		cal.set(Calendar.YEAR, 2015);
		cal.set(Calendar.MONTH, 9);
		cal.set(Calendar.DATE, 21);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date d = cal.getTime();
		Assert.assertEquals(d, dates.parse("21 October 2015"));
	}

}
