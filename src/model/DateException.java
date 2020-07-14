package model;

/**
 * Class represents a date Exception that can be thrown by the Date Class.
 * @author Manuel Cruz
 * @version 1.0
 *
 */
public class DateException extends Exception 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Simple Date Exception to ensure correct dates.
	 * @param message - message to display
	 */
	public DateException(String message)
	{
		super(message);
	}
}
