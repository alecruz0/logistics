package model;

import utilities.Utilities;

/**
 * Class to hold the data of a user
 * 
 * @author Manuel Cruz
 * @version 1.0
 *
 */
public class User implements Data
{
	/** Constant to represent the user first name */
	public final static int USER_FIRST_NAME = 0;
	
	/** Constant to represent the user last name */
	public final static int USER_LAST_NAME = 1;
	
	/** Constant to represent the user birthday */
	public final static int USER_BIRTHDAY = 2;
	
	/** Constant to represent the user administrator */
	public final static int USER_ADMINISTRATOR = 3;
	
	/** Constant to represent the user username */
	public final static int USER_USERNAME = 4;
	
	/** Constant to represent the user password */
	public final static int USER_PASSWORD = 5;
	
	/** Constant to represent the user id */
	public final static int USER_ID = 6;
	
	/** type of sorting used for user. Default is first name. */
	private static int sorting = USER_FIRST_NAME;
	
	/** User's first name */
	private String firstName;
	
	/** User's last name */
	private String lastName;
	
	/** User's date of birth */
	private Date birthday;
	
	/** User's unique id */
	private int id;
	
	/** User's administration privileges */
	private boolean administrator;
	
	/** User's username to login */
	private String username;
	
	/** User's password to login */
	private String password;
	
	/**
	 * Explicit constructor of user.
	 * 
	 * @param first -  User's first name
	 * @param last - User's last name
	 * @param birthday - User's Date of birth
	 * @param id - User's unique user id
	 */
	public User(final String first, final String last, final Date birthday, 
			    int id, boolean administrator, final String username, final String password)
	{
		setFirstName(first);
		setLastName(last);
		setBirthday(birthday);
		setId(id);
		setAdministrator(administrator);
		setUsername(username);
		setPassword(password);
	}
	
	/**
	 * Sets the first name of the user. If empty null will be assigned as string.
	 * 
	 * @param first - User's first name
	 */
	public void setFirstName(String first)
	{
		firstName = Utilities.getInstance().validString(first);
	}
	
	/**
	 * Sets the last name of the user. If empty null will be assigned as string.
	 * 
	 * @param last - User's last name
	 */
	public void setLastName(String last)
	{
		lastName = Utilities.getInstance().validString(last);
	}
	
	/**
	 * Sets the date of birth of user.
	 * 
	 * @param birthday - User's date of birth
	 */
	public void setBirthday(Date birthday)
	{
		if (birthday == null)
			birthday = new Date();
		this.birthday = birthday;
	}
	
	/**
	 * Sets the unique id of the user.
	 * 
	 * @param id - user's id
	 */
	public void setId(int id)
	{
		this.id = id;
	}
	
	/**
	 * Sets the administration of this user. If true it has full administration to 
	 * the database. False for only user with limited administration to the database.
	 * @param administrator - administration of user.
	 */
	public void setAdministrator(boolean administrator)
	{
		this.administrator = administrator;
	}
	
	/**
	 * Sets the users username to login into the database.
	 * @param username - username to use to login.
	 */
	public void setUsername(String username)
	{
		this.username = Utilities.getInstance().validString(username);
	}
	
	/**
	 * Sets the users password to login to the database.
	 * @param password - password to use to login.
	 */
	public void setPassword(String password)
	{
		this.password = Utilities.getInstance().validString(password);
	}
	
	/**
	 * Gets the first name of the user.
	 * 
	 * @return first name of user.
	 */
	public String getFirstName()
	{
		return firstName;
	}
	
	/**
	 * Gets the last name of the user.
	 * 
	 * @return last name of user.
	 */
	public String getLastName()
	{
		return lastName;
	}
	
	/**
	 * Gets the birthday of the user.
	 * 
	 * @return birthday of user.
	 */
	public Date getBirthday()
	{
		return birthday;
	}
	
	/**
	 * Gets the id of the user.
	 * 
	 * @return id of user
	 */
	public int getId()
	{
		return id;
	}
	
	/**
	 * Gets the administration use of this user.
	 * @return administration privileges
	 */
	public boolean isAdministrator()
	{
		return administrator;
	}
	
	/**
	 * Gets the username of the user.
	 * @return username of user
	 */
	public String getUsername()
	{
		return username;
	}
	
	/**
	 * Gets the password of the user.
	 * @return password of user.
	 */
	public String getPassword()
	{
		return password;
	}
	
	@Override
	public String toString()
	{
		String string = "";
		
		string += "[User -> ";
		string += "First Name: " + this.firstName + ", ";
		string += "Last Name: " + this.lastName + ", ";
		string += "Date of birth: " + this.birthday + ", ";
		string += "ID: " + this.id + ", ";
		string += "Administrator: ";
		string += administrator ? "yes" : "no";
		string += "]";
		
		return string;
	}

	@Override
	public Object[] rowValues() 
	{
		Object[] values = {getFirstName(), getLastName(), getBirthday(), getUsername(), Integer.toHexString(id)};
		return values;
	}

	@Override
	public boolean update(int type, Object change) 
	{
		if (type == USER_FIRST_NAME && change instanceof String)
			setFirstName((String) change);
		else if (type == USER_LAST_NAME && change instanceof String)
			setLastName((String) change);
		else if (type == USER_BIRTHDAY && change instanceof Date)
			setBirthday((Date) change);
		else if (type == USER_ID && change instanceof Integer)
			setId((int) change);
		else if (type == USER_ADMINISTRATOR && change instanceof Boolean)
			setAdministrator((boolean) change);
		else if (type == USER_USERNAME && change instanceof String)
			setUsername((String) change);
		else if (type == USER_PASSWORD && change instanceof String)
			setPassword((String) change);
		else
			return false;
		
		return true;
	}
	
	@Override
	public int compareTo(Data data) 
	{
		int value = 0;
		
		if (!(data instanceof User))
			return value;
		
		User other = (User) data;
		
		if (sorting == USER_FIRST_NAME)
		{
			value = getFirstName().compareTo(other.getFirstName());
			if (value == 0) value = getLastName().compareTo(other.getLastName());
			if (value == 0) value = getBirthday().compareTo(other.getBirthday());
			if (value == 0) value = Integer.compare(getId(), other.getId());
			if (value == 0) value = getUsername().compareTo(other.getUsername());
		}
		else if (sorting == USER_LAST_NAME)
		{
			
			value = getLastName().compareTo(other.getLastName());
			if (value == 0) value = getFirstName().compareTo(other.getFirstName());
			if (value == 0) value = getBirthday().compareTo(other.getBirthday());
			if (value == 0) value = Integer.compare(getId(), other.getId());
			if (value == 0) value = getUsername().compareTo(other.getUsername());
		}
		else if (sorting == USER_BIRTHDAY)
		{
			value = getBirthday().compareTo(other.getBirthday());
			if (value == 0) value = getFirstName().compareTo(other.getFirstName());
			if (value == 0) value = getLastName().compareTo(other.getLastName());
			if (value == 0) value = Integer.compare(getId(), other.getId());
			if (value == 0) value = getUsername().compareTo(other.getUsername());
		}
		else if (sorting == USER_ID)
		{
			value = Integer.compare(getId(), other.getId());
			if (value == 0) value = getFirstName().compareTo(other.getFirstName());
			if (value == 0) value = getLastName().compareTo(other.getLastName());
			if (value == 0) value = getBirthday().compareTo(other.getBirthday());
			if (value == 0) value = getUsername().compareTo(other.getUsername());
		}
		else
		{
			value = getUsername().compareTo(other.getUsername());
			if (value == 0) value = getFirstName().compareTo(other.getFirstName());
			if (value == 0) value = getLastName().compareTo(other.getLastName());
			if (value == 0) value = getBirthday().compareTo(other.getBirthday());
			if (value == 0) value = Integer.compare(getId(), other.getId());
		}
		
		return value;
	}

	/**
	 * Sets the sorting type for user.
	 * @param type - type of sorting
	 */
	public static void setSorting(int type)
	{
		boolean validType = type == USER_FIRST_NAME || type == USER_LAST_NAME ||
							type == USER_BIRTHDAY   || type == USER_ID        || 
							type == USER_USERNAME;
	
		if (validType)
			sorting = type;
	}
	
	/**
	 * Gets the header of the user
	 * @return an array containing the header
	 */
	public static String[] getHeader()
	{
		String[] header = {"First Name", "Last Name", "Birthday", "Username", "ID"};
		return header;
	}
}
