package model;

import utilities.Utilities;

/**
 * Class to hold the data of a company.
 * 
 * @author Manuel Cruz
 * @version 1.0
 * 
 */
public class Company implements Data
{
	/** Constant to represent the company name */
	public static final int COMPANY_NAME = 0;
	
	/** Constant to represent the company date */
	public static final int COMPANY_DATE = 1;
	
	/** Constant to reprensent the company ID */
	public static final int COMPANY_ID = 2;
	
	/** type of sorting used for company. Default is name */
	private static int sorting = COMPANY_NAME;
	
	/** Company name */
	private String name;
	
	/** Company date created / added */
	private Date dateCreated;
	
	/** Company ID */
	private int id;
	
	/**
	 * Explicit constructor.
	 * 
	 * @param name - Name of the company.
	 * @param dateCreated - date the product was created.
	 * @param id - ID of the company.
	 */
	public Company(String name, Date dateCreated, int id)
	{
		setName(name);
		setDate(dateCreated);
		setId(id);
	}
	
	/**
	 * Sets the name of the company.
	 * 
	 * @param name - name of the company.
	 */
	public void setName(String name)
	{
		this.name = Utilities.getInstance().validString(name);
	}
	
	/**
	 * Sets the date of the company. If date is null the 
	 * current date will be used.
	 * 
	 * @param date - date of the company.
	 */
	public void setDate(Date date)
	{
		if (date == null)
			dateCreated = new Date();
		else
			dateCreated = date;
	}
	
	/**
	 * Sets the id of the company.
	 * 
	 * @param id - id of the company
	 */
	public void setId(int id)
	{
		this.id = id;
	}
	
	/**
	 * Gets the name of the company.
	 * 
	 * @return name of the company.
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Gets the date this company was added / created.
	 * 
	 * @return date of the company
	 */
	public Date getDate()
	{
		return dateCreated;
	}
	
	@Override
	public int getId()
	{
		return id;
	}
	
	@Override
	public String toString()
	{
		String string = "";
		
		string += name + ", " + Integer.toHexString(id);
		
		return string;
	}

	@Override
	public Object[] rowValues() 
	{
		Object[] rowDetails = {getName(), getDate(), Integer.toHexString(id)};
		return rowDetails;
	}

	@Override
	public boolean update(int type, Object change) 
	{
		if (type == COMPANY_NAME && change instanceof String)
			setName((String) change);
		else if (type == COMPANY_DATE && change instanceof Date)
			setDate((Date) change);
		else if (type == COMPANY_ID && change instanceof Integer)
			setId((int) change);
		else
			return false;
		
		return true;
	}
	
	@Override
	public int compareTo(Data data) 
	{
		int value = 0;
		
		if (!(data instanceof Company))
			return value;
		
		Company other = (Company) data;
		
		if (sorting == Company.COMPANY_NAME)
		{
			value = getName().compareTo(other.getName());
			if (value == 0) value = getDate().compareTo(other.getDate());
			if (value == 0) value = Integer.compare(getId(), other.getId());
		}
		else if (sorting == Company.COMPANY_DATE)
		{
			value = getDate().compareTo(other.getDate());
			if (value == 0) value = getName().compareTo(other.getName());
			if (value == 0) value = Integer.compare(getId(), other.getId());
		}
		else
		{
			value = Integer.compare(getId(), other.getId());
			if (value == 0) value = getName().compareTo(other.getName());
			if (value == 0) value = getDate().compareTo(other.getDate());
		}
		
		return value;
	}

	/**
	 * Sets the sorting type for company
	 * 
	 * @param type - type of sorting
	 */
	public static void setSorting(int type)
	{
		boolean validType = type == COMPANY_NAME || 
						    type == COMPANY_DATE ||
						    type == COMPANY_ID;
		
		if (validType)
			sorting = type;
	}
	
	/**
	 * Gets the header of the company
	 * @return an array containing the header
	 */
	public static String[] getHeader()
	{
		String[] header = {"Name", "Date", "ID"};
		return header;
	}
}
