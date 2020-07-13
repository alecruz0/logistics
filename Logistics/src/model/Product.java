package model;
import utilities.Utilities;

/**
 * Class to hold the data of a product.
 * 
 * @author Manuel Cruz
 * @version 1.0
 *
 */
public class Product implements Data
{
	/** Constant to represent the product name */
	public final static int PRODUCT_NAME = 0;
	
	/** Constant to represent the product company */
	public final static int PRODUCT_COMPANY = 1;
	
	/** Constant to represent the product weight */
	public final static int PRODUCT_WEIGHT = 2;
	
	/** Constant to represent the product date */
	public final static int PRODUCT_DATE = 3;
	
	/** Constant to represent the product id */
	public final static int PRODUCT_ID = 4;
	
	/** type of sorting used for product. Default is name */
	private static int sorting = PRODUCT_NAME;
	
	/** Product's name */
	private String name;
	
	/** Product's company */
	private int company;
	
	/** Product's unit weight */
	private double weight;
	
	/** Product's date it was created / added */
	private Date date;
	
	/** Product's unique id */
	private int id;
	
	/**
	 * Explicit constructor for a product.
	 * 
	 * @param name - name of the product.
	 * @param company - company of the product.
	 * @param id - Unique id of the product.
	 * @param weight - weight of the product.
	 * @param dateCreated - date the product was added to the warehouse.
	 */
	public Product(String name, int company, int id, 
			       double weight, Date date)
	{
		setName(name);
		setCompany(company);
		setId(id);
		setWeight(weight);
		setDate(date);
	}
	
	/**
	 * Sets the name of the product.
	 * 
	 * @param name - name of the product.
	 */
	public void setName(String name)
	{
		this.name = Utilities.getInstance().validString(name);
	}
	
	/**
	 * Sets the id company of this product.
	 * 
	 * @param company - company of the product
	 */
	public void setCompany(int company)
	{
		this.company = company;
	}
	
	/**
	 * Sets the weight of this product. If the weight is below 0 is setted to 0.
	 * 
	 * @param weight - weight of this product.
	 */
	public void setWeight(double weight)
	{
		if (weight < 0)
			weight = 0;
		
		this.weight = weight;
	}
	
	/**
	 * Sets the id of this product.
	 * @param id - id of the product
	 */
	public void setId(int id)
	{
		this.id = id;
	}
	
	/**
	 * Sets the date the product was created.
	 * 
	 * @param date - date the product was created.
	 */
	public void setDate(Date date)
	{
		if (date == null)
			date = new Date();
		
		this.date = date;
	}
	
	/**
	 * Gets the name of the product.
	 * 
	 * @return name of product
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Gets the id company of the product.
	 * 
	 * @return company of the product
	 */
	public int getCompany()
	{
		return company;
	}
	
	/**
	 * Gets the weight of the product.
	 * 
	 * @return weight of product.
	 */
	public double getWeight()
	{
		return weight;
	}
	
	/**
	 * Gets the date this product was created.
	 * 
	 * @return date product was created.
	 */
	public Date getDate()
	{
		return date;
	}

	@Override
	public int getId()
	{
		return this.id;
	}
	
	/**
	 * Gets the value to be shown on the table for the
	 * company of this product.
	 * @return string value of the company
	 */
	private String getCompanyValue()
	{
		String string = "";
		
		Data data = Database.getInstance().select(company);
		
		if (data == null)
		{
			String message = "Product - getCompanyValue() - null ptr "
					+ "invalid company: " + company;
			Utilities.getInstance().error(message);
		}
		
		if (!(data instanceof Company))
		{
			String message = "Product - getCompanyValue() - invalid instance "
					+ "invalid company: " + company;
			Utilities.getInstance().error(message);
		}
		
		Company company = (Company) data;
		string += company.getName();
		string += ", ";
		string += Integer.toHexString(company.getId());
		
		return string;
	}
	
	@Override
	public String toString()
	{
		String string = "";
		
		string += name + ", ";
		
		Data data = Database.getInstance().select(this.company);
		if (data instanceof Company)
		{
			Company company = (Company) data;
			string += company.getName() + ", ";
		}
		else
		{
			string += "null, ";
		}
		
		string += id + ", ";
		string += weight;
		
		return string;
	}

	@Override
	public Object[] rowValues() 
	{
		Object[] values = {getName(), getCompanyValue(), getWeight(), 
				getDate(), Integer.toHexString(id)};
		return values;
	}

	@Override
	public boolean update(int type, Object change) 
	{
		if (type == PRODUCT_NAME && change instanceof String)
			setName((String) change);
		else if (type == PRODUCT_COMPANY && change instanceof Integer)
			setCompany((int) change);
		else if (type == PRODUCT_WEIGHT && change instanceof Double)
			setWeight((double) change);
		else if (type == PRODUCT_DATE && change instanceof Date)
			setDate((Date) change);
		else if (type == PRODUCT_ID && change instanceof Integer)
			setId((int) change);
		else
			return false;
		
		return true;
	}	
	
	@Override
	public int compareTo(Data data) 
	{
		int value = 0;
		
		if (!(data instanceof Product))
			return value;
		
		Product other = (Product) data;
		
		if (sorting == PRODUCT_NAME)
		{
			value = getName().compareTo(other.getName());
			if (value == 0)
			{
				Data c1 = Database.getInstance().select(getCompany());
				Data c2 = Database.getInstance().select(other.getCompany());
				Company.setSorting(Company.COMPANY_NAME);
				value = c1.compareTo(c2);
				if (value == 0) value = Integer.compare(getCompany(), other.getCompany());
			}
			
			if (value == 0) value = Double.compare(getWeight(), other.getWeight());
			if (value == 0) value = getDate().compareTo(other.getDate());
			if (value == 0) value = Integer.compare(getId(), other.getId());
		}
		else if (sorting == PRODUCT_COMPANY)
		{
			Data c1 = Database.getInstance().select(getCompany());
			Data c2 = Database.getInstance().select(other.getCompany());
			Company.setSorting(Company.COMPANY_NAME);
			value = c1.compareTo(c2);
			if (value == 0) value = Integer.compare(getCompany(), other.getCompany());
			
			if (value == 0) value = getName().compareTo(other.getName());
			if (value == 0) value = Double.compare(getWeight(), other.getWeight());
			if (value == 0) value = getDate().compareTo(other.getDate());
			if (value == 0) value = Integer.compare(getId(), other.getId());
		}
		else if (sorting == PRODUCT_WEIGHT)
		{
			value = Double.compare(getWeight(), other.getWeight());
			if (value == 0) value = getName().compareTo(other.getName());
			if (value == 0)
			{
				Data c1 = Database.getInstance().select(getCompany());
				Data c2 = Database.getInstance().select(other.getCompany());
				Company.setSorting(Company.COMPANY_NAME);
				value = c1.compareTo(c2);
				
				if (value == 0) value = Integer.compare(getCompany(), other.getCompany());
			}
			
			if (value == 0) value = getDate().compareTo(other.getDate());
			if (value == 0) value = Integer.compare(getCompany(), other.getCompany());
		}
		else if (sorting == PRODUCT_DATE)
		{
			value = getDate().compareTo(other.getDate());
			if (value == 0) value = getName().compareTo(other.getName());
			if (value == 0)
			{
				Data c1 = Database.getInstance().select(getCompany());
				Data c2 = Database.getInstance().select(other.getCompany());
				value = c1.compareTo(c2);
				
				if (value == 0) value = Integer.compare(getCompany(), other.getCompany());
			}
			
			if (value == 0) value = Double.compare(getWeight(), other.getWeight());
			if (value == 0) value = Integer.compare(getId(), other.getId());
		}
		else
		{
			value = Integer.compare(getId(), other.getId());
			if (value == 0) value = getName().compareTo(other.getName());
			if (value == 0)
			{
				Data c1 = Database.getInstance().select(getCompany());
				Data c2 = Database.getInstance().select(other.getCompany());
				Company.setSorting(Company.COMPANY_NAME);
				value = c1.compareTo(c2);
				
				if (value == 0) value = Integer.compare(getCompany(), other.getCompany());
			}
			
			if (value == 0) value = Double.compare(getWeight(), other.getWeight());
			if (value == 0) value = getDate().compareTo(other.getDate());
		}
		
		return value;
	
	}

	/**
	 * Sets the sorting type for product
	 * @param type - type of sorting
	 */
	public static void setSorting(int type)
	{
		boolean validType = type == PRODUCT_NAME   || type == PRODUCT_COMPANY ||
							type == PRODUCT_WEIGHT || type == PRODUCT_DATE    ||
							type == PRODUCT_ID;
		if (validType)
			sorting = type;
	}

	/**
	 * Gets the header of the product
	 * @return an array containing the header
	 */
	public static String[] getHeader()
	{
		String[] header = {"Name", "Company", "Weight", "Date", "ID"};
		return header;
	}
}
