package model;

/**
 * Data interface to interpret all data in database.
 * 
 * @author Manuel Cruz
 * @version 1.0
 *
 */
public interface Data extends Comparable<Data> 
{
	/**
	 * Gets the unique id of this data
	 * @return id of the data
	 */
	public abstract int getId();
	
	/**
	 * Gets the row values to be displayed at a table
	 * @return values to be displayed of the data
	 */
	public abstract Object[] rowValues();
	
	/**
	 * Updates an attibutte of the data.
	 * 
	 * @param type - type of attributte to change
	 * @param change - new change to that attributte
	 * @return true if it was successfully changed. False otherwise.
	 */
	public abstract boolean update(int type, Object change);
}
