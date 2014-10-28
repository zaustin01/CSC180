package labs.three.converters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleDateFormatConverter implements Converter<Date> {

	private SimpleDateFormat forParsing;
	private SimpleDateFormat forFormatting;
	private final SimpleDateFormat defaultFormat = new SimpleDateFormat("d MMMM yyyy");
	
	public SimpleDateFormatConverter(){
		forParsing = forFormatting = defaultFormat;
	}
	
	public SimpleDateFormatConverter(SimpleDateFormat sdf){
		if (sdf == null){
			sdf = defaultFormat;
		}
		forParsing = forFormatting = sdf;
	}
	
	public SimpleDateFormatConverter(SimpleDateFormat forParsing, SimpleDateFormat forFormatting){
		if (forFormatting == null)
			forFormatting = defaultFormat;
		if (forParsing == null)
			forParsing = defaultFormat;
		
		this.forParsing = forParsing;
		this.forFormatting = forFormatting;
	}
	
	@Override
	public Date parse(String fromString) {
		Date parsedDate = null;
		try{
			parsedDate = forParsing.parse(fromString);
		} catch (ParseException e){
			
		}
		return parsedDate;
	}

	@Override
	public String format(Date fromT) {
		return forFormatting.format(fromT);
	}

}
