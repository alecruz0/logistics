package model;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Date class that resembles the Gregorian calendar.
 * 
 * @author Manuel Cruz
 * @version 1.0
 */
public class Date
{
	/** Month of date */
	private int month;
	
	/** Day of date */
	private int day;
	
	/** Year of date */
	private int year;
	
	/**
	 * Simple date constructor. Gives back the current date.
	 */
	public Date()
	{
		try
		{
			setMonth(currentMonth());
			setYear(currentYear());
			setDay(currentDay());
		}
		catch (DateException de) { }
	}
	
	/**
	 * Explicit constructor of date. If a wrong date is 
	 * added, then it just create the current date.
	 * 
	 * @param month - month of date
	 * @param day - day of date
	 * @param year - year of date
	 */
	public Date(int month, int day, int year)
	{
		try
		{
			setMonth(month);
			setYear(year);
			setDay(day);
		}
		catch (DateException de)
		{
			this.month = currentMonth();
			this.year  = currentYear();
			this.day   = currentDay();
		}
	}
	
	/**
	 * Constructor of date with values containing numerical 
	 * values of dates. If one of the string fails to be a 
	 * value the current date will be created.
	 * 
	 * @param month - month of date
	 * @param day - day of date
	 * @param year - year of date
	 */
	public Date(final String month, final String day, final String year)
	{
		this(month + "/" + day + "/" + year);
	}
	
	/**
	 * Constructor of a date with the format mm/dd/yyyy. If 
	 * wrong format given then the current date is created.
	 * 
	 * @param string - string that represents the date.
	 */
	public Date(final String string)
	{
		String[] array = string.split("/");
		int month, day, year;
		try
		{
			month = Integer.parseInt(array[0]);
			day = Integer.parseInt(array[1]);
			year = Integer.parseInt(array[2]);
		}		
		catch (NumberFormatException nfe)
		{
			
			month = currentMonth();
			day = currentDay();
			year = currentYear();
		}
		
		try
		{
			setMonth(month);
			setYear(year);
			setDay(day);			
		}
		catch (DateException de) 
		{
		}
	}
	
	/**
	 * Maximum days in the month of this date.
	 * 
	 * @return maximum number of days in the month.
	 */
	private int maximumDays()
	{
		// February
		if (month == 2)
		{   // leaps have 29 days in february
			if (isLeapYear())
				return 29;
			return 28;
		}
		
		// january, march, may, july, august, october, december
		boolean day31 = month == 1 || month == 3 || month == 5 || 
				        month == 7 || month == 8 || month == 10 ||
				        month == 12;
		
		if (day31)
			return 31;
		
		return 30; // rest of months
	}
	
	/**
	 * Checks if this number is a leap year or not. True if it is. False otherwise.
	 * 
	 * @return true if leap. False otherwise.
	 */
	private boolean isLeapYear()
	{
		if (year % 4 == 0)
		{	
			if (year % 100 == 0)
			{
				if (year % 400 == 0)
					return true;
				return false;
			}
			return true;
		}
		return false;
	}
	
	private int currentYear()
	{
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	private int currentDay()
	{
		return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
	}

	private int currentMonth()
	{
		return Calendar.getInstance().get(Calendar.MONTH) + 1;
	}

	/**
	 * Sets the year of this date.
	 * 
	 * @param year - year of this date.
	 * @throws DateException - if the year is before the creation of the 
	 * gregorian calendar (1582)
	 */
	public void setYear(int year) throws DateException
	{
		if (year < 1582)
			throw new DateException("Year cannot be before 1582");
		
		this.year = year;
	}
	
	/**
	 * Sets the day of this date.
	 * 
	 * @param day - day of this date
	 * @throws DateException - if the day is below 1 or if the day is beyond
	 * the month's maximum days.
	 */
	public void setDay(int day) throws DateException
	{
		if (day < 1)
			throw new DateException("Day cannot be below 1");
		
		int monthMaxDays = maximumDays();
		
		if (day > monthMaxDays)
			throw new DateException(month + " only has " + monthMaxDays);
		
		this.day = day;
	}
	
	/**
	 * Sets the month of this date.
	 * @param month - month to be setted
	 * @throws DateException - if the month is below 1 or above 12.
	 */
	public void setMonth(int month) throws DateException
	{
		if (month < 1)
			throw new DateException("Year cannot be below 1");
		
		if (month > 12)
			throw new DateException("Year cannot be beyond 12");
		
		this.month = month;
	}
	
	@Override
	public boolean equals(Object other)
	{
		boolean instance = other instanceof Date;
		if (instance)
		{
			Date another = (Date) other;
			boolean year = this.year == another.year;
			boolean month = this.month == another.month;
			boolean day = this.day == another.day;
			return year && month && day;
		}
		return false;
	}
	
	@Override
	public String toString()
	{
		String string = "";
		
		if (month < 10)
			string += "0";
		
		string += this.month + "/";
		
		if (day < 10)
			string += "0";
		
		string += this.day + "/";
		string += this.year;
		
		return string;
	}

	/**
	 * Compares this date to another date.
	 * @param date - date to be compared to
	 * @return 1 if this is greater than the other 
	 * date. 0 if it is equals to the other date. 
	 * -1 if this is less than the other date.
	 */
	public int compareTo(Date date) 
	{
		int value = 0;
		if (this.year > date.year)
			value = 1;
		else if (this.year < date.year)
			value = -1;
		else
		{
			if (this.month > date.month)
				value = 1;
			else if (this.month < date.month)
				value = -1;
			else
			{
				if (this.day > date.day)
					value = 1;
				else if (this.day < date.day)
					value = -1;
				else
					value = 0;
			}
		}
		return value;
	}
	
	/**
	 * Check if the given string is a valid date.
	 * @param string - string to validate
	 * @return true if it is a valid. False otherwise.
	 */
	public static boolean validDate(String string)
	{
		if (string == null)
			return false;
		
		if (string.isBlank())
			return false;
		
		String regex = "^(1[0-2]|0[1-9])/(3[01]|[12][0-9]|0[1-9])/[0-9]{4}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(string.strip());
		return matcher.matches();
	}
}
