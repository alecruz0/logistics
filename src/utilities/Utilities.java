package utilities;

import model.Database;

/**
 * Utilities to be used on Logistics.
 * 
 * @author Manuel Cruz
 * @version 1.0
 */
public class Utilities 
{
	/** Single instance to be used. */
	private static Utilities instance;
	
	/**
	 * Simple unique constructor.
	 */
	private Utilities()
	{
		super();
	}
	
	/**
	 * Check if a string is valid. It will return null 
	 * if the string is empty or it is actually null.
	 * 
	 * @param string - String to be checked.
	 * @return Returns a string if invalid.
	 */
	public String validString(String string)
	{
		if (string == null)
			return "null";
		
		if (string.trim().isEmpty())
			return "null";
		
		return string;
	}
	
	/**
	 * Takes care of an error. It saves data then it kills the program.
	 * @param message - message to show to standard error
	 */
	public void error(String message)
	{
		System.err.println(message);
		Database.getInstance().save();
		System.exit(-1);
	}
	
	/**
	 * Singleton for unique utilities instance.
	 * 
	 * @return Single unique instance.
	 */
	public static Utilities getInstance()
	{
		if (instance == null)
			instance = new Utilities();
		return instance;
	}
}
