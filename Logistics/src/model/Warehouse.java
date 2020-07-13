package model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utilities.Utilities;

/**
 * Class to hold the data of a warehouse.
 * 
 * @author Ale Cruz
 * @version 1.0
 */
public class Warehouse implements Data
{
	/** Constant to represent the warehouse name */
	public final static int WAREHOUSE_NAME = 0;
	
	/** Constant to represent the warehouse capacity */
	public final static int WAREHOUSE_CAPACITY = 1;
	
	/** Constant to represent the warehouse product count */
	public final static int WAREHOUSE_PRODUCT_COUNT = 2;
	
	/** Constant to represent the warehouse quantity */
	public final static int WAREHOUSE_QUANTITY = 3;
	
	/** Constant to represent the warehouse date */
	public final static int WAREHOUSE_DATE = 4;
	
	/** Constant to represent the warehouse id */
	public final static int WAREHOUSE_ID = 5;
	
	/** type of sorting used for warehouse. Default is name */
	private static int sorting = WAREHOUSE_NAME;
	
	/** Name of the warehouse */
	private String name;
	
	/** Capacity of the warehouse */
	private int capacity;
	
	/** products that the warehouse contain */
	private Map<Integer, Integer> products;
	
	/** date the warehouse was created / added */
	private Date date;
	
	/** Unique Identity of the warehouse */
	private int id;
	
	/**
	 * Explicit constructor of a warehouse object.
	 * 
	 * @param name - Name of warehouse
	 * @param capacity - capacity of warehouse
	 * @param dateCreated - date the warehouse was created / added
	 */
	public Warehouse(final String name, int capacity, final Date dateCreated, int id)
	{
		setName(name);
		setCapacity(capacity);
		setDate(dateCreated);
		setId(id);
		products = new HashMap<Integer, Integer>();
	}

	/**
	 * Sets the name of the warehouse. If null or empty string null will be assigned.
	 * @param name - name of warehouse.
	 */
	public void setName(String name) 
	{
		this.name = Utilities.getInstance().validString(name);
	}

	/**
	 * Sets the capacity of the warehouse. If negative 0 will  be assigned.
	 * @param capacity - capacity of the warehouse.
	 */
	public void setCapacity(int capacity) 
	{
		if (capacity < 0)
			capacity = 0;
		this.capacity = capacity;
	}

	/**
	 * Sets the date the warehouse was created.
	 * @param dateCreated - date warehouse was created.
	 */
	public void setDate(Date date) 
	{
		if (date == null)
			date = new Date();
		this.date = date;
	}

	/**
	 * Sets the id of this warehouse.
	 * @param id - if of warehouse
	 */
	public void setId(int id)
	{
		this.id = id;
	}

	/**
	 * Gets the name of the warehouse.
	 * @return name of warehouse.
	 */
	public String getName() 
	{
		return name;
	}

	/**
	 * Gets the capacity of this warehouse.
	 * @return the capacity of the warehouse.
	 */
	public int getCapacity() 
	{
		return capacity;
	}

	/**
	 * Gets the list of products in this warehouse.
	 * @return list of products in the warehouse.
	 */
	public List<Integer> getProducts() 
	{
		return new ArrayList<Integer>(products.keySet());
	}

	/**
	 * Gets the date this warehouse was created.
	 * @return date the warehouse was created.
	 */
	public Date getDate() 
	{
		return date;
	}

	@Override
	public int getId()
	{
		return id;
	}
	
	/**
	 * Gets the quantity that a specific product in this 
	 * warehouse has. If the product it is not found it returns -1;
	 * @param product - product to check for
	 * @return the quantity 
	 */
	public int getProductQuantity(int product)
	{
		if (!contains(product))
			return -1;
		return products.get(product);
	}
	
	/**
	 * Gets the quantity amount of unit of products in this warehouse.
	 * @return the quantity of the warehouse
	 */
	public int getQuantity()
	{
		List<Integer> products = getProducts();
		int quantity = 0;
		for (int id : products)
			quantity += getProductQuantity(id);
		
		return quantity;
	}

	/**
	 * Gets the number of products in the warehouse.
	 * @return number of products
	 */
	public int getProductCount()
	{
		return products.size();
	}

	/**
	 * Adds a product to the warehouse. If it has reached its capacity it will fail.<br><br>
	 * It will add a product with a count of 0.
	 * 
	 * @param product - product to be added.
	 * @return true if success. False otherwise.
	 */
	public boolean add(int product)
	{
		return add(product, 0);
	}
	
	/**
	 * Adds a product to the warehouse. If it has reached its capacity it will fail.<br><br>
	 * It adds a product starting with the given count.
	 * 
	 * @param product - product to be added.
	 * @param count - count of the given product.
	 * @return true if success. False otherwise.
	 */
	public boolean add(int product, int count)
	{
		if (full())
			return false;
		
		if (getQuantity() + count > capacity)
			return false;
		
		products.put(product, count);
		return true;
	}
	
	/**
	 * Removes quantity from the given product.
	 * <br><br>
	 * It will fail if it doesn't contain it.
	 * It will fail if the quantity is greater than the current amount.
	 * @param product - product to remove from
	 * @param quantity - quantity to remove
	 * @return true if it was successful false otherwise
	 */
	public boolean remove(int product, int quantity)
	{
		if (!contains(product))
			return false;
		
		int productCount = getProductQuantity(product);
		if (quantity > productCount)
			return false;
		
		if (quantity == productCount)
			products.remove(product);
		else
			products.put(product, productCount - quantity);
		
		return true;
	}
	
	/**
	 * Returns true if the product is stored in the warehouse. False otherwise.
	 * @param product - product to be found.
	 * @return true if found. False otherwise
	 */
	public boolean contains(int product)
	{
		if (empty())
			return false;
		
		return products.containsKey(product);
	}
	
	/**
	 * Returns true if the warehouse is full. False otherwise. A warehouse is 
	 * full if and only if the number of products stored reached its capacity.
	 * 
	 * @return true if capacity reached. False if there is still space.
	 */
	public boolean full()
	{
		return getQuantity() == capacity;
	}
	
	/**
	 * Checks if the warehouse is empty.
	 * 
	 * @return true if it is empty. False otherwise.
	 */
	public boolean empty()
	{
		return products.isEmpty();
	}
	
	@Override
	public String toString()
	{
		String string = "";
		
		string += "[Warehouse -> ";
		string += "Name: " + this.name + ", ";
		string += "Capacity: " + this.capacity + ", ";
		string += "Date Created: " + this.date.toString() + "]";
		
		return string;
	}

	@Override
	public Object[] rowValues() 
	{
		Object[] values = {getName(), getProductCount(), getCapacity(), 
				getQuantity(), getDate(), Integer.toHexString(id)};
		return values;
	}

	@Override
	public boolean update(int type, Object change) {
		if (type == WAREHOUSE_NAME && change instanceof String)
			setName((String) change);
		else if (type == WAREHOUSE_CAPACITY && change instanceof Integer)
			setCapacity((int) change);
		else if (type == WAREHOUSE_DATE && change instanceof Date)
			setDate((Date) change);
		else if (type == WAREHOUSE_ID && change instanceof Integer)
			setId((int) change);
		else
			return false;
		
		return true;
	}
	
	@Override
	public int compareTo(Data data) 
	{
		int value = 0;
		
		if (!(data instanceof Warehouse))
			return value;
		
		Warehouse other = (Warehouse) data;
		
		if (sorting == WAREHOUSE_NAME)
		{
			value = getName().compareTo(other.getName());
			if (value == 0) value = Integer.compare(getCapacity(), other.getCapacity());
			if (value == 0) value = Integer.compare(getProductCount(), other.getProductCount());
			if (value == 0) value = Integer.compare(getQuantity(), other.getQuantity());
			if (value == 0) value = getDate().compareTo(other.getDate());
			if (value == 0) value = Integer.compare(getId(), other.getId());
		}
		else if (sorting == WAREHOUSE_CAPACITY)
		{
			value = Integer.compare(getCapacity(), other.getCapacity());
			if (value == 0) value = getName().compareTo(other.getName());
			if (value == 0) value = Integer.compare(getProductCount(), other.getProductCount());
			if (value == 0) value = Integer.compare(getQuantity(), other.getQuantity());
			if (value == 0) value = getDate().compareTo(other.getDate());
			if (value == 0) value = Integer.compare(getId(), other.getId());
		}
		else if (sorting == WAREHOUSE_PRODUCT_COUNT)
		{
			value = Integer.compare(getProductCount(), other.getProductCount());
			if (value == 0) value = getName().compareTo(other.getName());
			if (value == 0) value = Integer.compare(getCapacity(), other.getCapacity());
			if (value == 0) value = Integer.compare(getQuantity(), other.getQuantity());
			if (value == 0) value = getDate().compareTo(other.getDate());
			if (value == 0) value = Integer.compare(getId(), other.getId());
		}
		else if (sorting == WAREHOUSE_QUANTITY)
		{
			value = Integer.compare(getQuantity(), other.getQuantity());
			if (value == 0) value = getName().compareTo(other.getName());
			if (value == 0) value = Integer.compare(getCapacity(), other.getCapacity());
			if (value == 0) value = Integer.compare(getProductCount(), other.getProductCount());
			if (value == 0) value = getDate().compareTo(other.getDate());
			if (value == 0) value = Integer.compare(getId(), other.getId());
		}
		else if (sorting == WAREHOUSE_DATE)
		{
			value = getDate().compareTo(other.getDate());
			if (value == 0) value = getName().compareTo(other.getName());
			if (value == 0) value = Integer.compare(getCapacity(), other.getCapacity());
			if (value == 0) value = Integer.compare(getProductCount(), other.getProductCount());
			if (value == 0) value = Integer.compare(getQuantity(), other.getQuantity());
			if (value == 0) value = Integer.compare(getId(), other.getId());
		}
		else
		{
			value = Integer.compare(getId(), other.getId());
			if (value == 0) value = getName().compareTo(other.getName());
			if (value == 0) value = Integer.compare(getCapacity(), other.getCapacity());
			if (value == 0) value = Integer.compare(getProductCount(), other.getProductCount());
			if (value == 0) value = Integer.compare(getQuantity(), other.getQuantity());
			if (value == 0) value = getDate().compareTo(other.getDate());
		}
		
		return value;
	}

	/**
	 * Sets the sorting type for the warehouse
	 * @param type - type of sorting
	 */
	public static void setSorting(int type)
	{
		boolean validType = type == WAREHOUSE_NAME          || type == WAREHOUSE_CAPACITY ||
							type == WAREHOUSE_PRODUCT_COUNT || type == WAREHOUSE_QUANTITY ||
							type == WAREHOUSE_DATE          || type == WAREHOUSE_ID;
		
		if (validType)
			sorting = type;
	}

	/**
	 * Gets the header of the warehouse
	 * @return an array containing the header
	 */
	public static String[] getHeader()
	{
		String[] header = {"Name", "Product Count", "Capacity", "Quantity", "Date", "ID"};
		return header;
	}
	
}
