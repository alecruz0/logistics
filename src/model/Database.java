package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.IO;

/**
 * Database class. It contains all of the data for logistics.
 * 
 * @author Manuel Cruz
 * @Version 1.0
 */
public class Database implements Runnable
{
	
	/** Constant mask to and it with an identity to get the type */
	public final static int DATA_TYPE_MASK = 0xfff0000;
	
	/** Constant company value */
	public final static int COMPANY = 0x1000000;
	
	/** Constant product value */
	public final static int PRODUCT = 0x2000000;
	
	/** Constant user value */
	public final static int USER = 0x3000000;
	
	/** Constant warehouse value */
	public final static int WAREHOUSE = 0x4000000;
	
	/** Single instance of the database */
	private static Database instance;
	
	/** List that contains all of the users */
	private List<User> users;
	
	/** List that contains all of the warehouses */
	private List<Warehouse> warehouses;
	
	/** List that contains all of the products */
	private List<Product> products;
	
	/** List that contains all of the companies */
	private List<Company> companies;
	
	
	/**
	 * Explicit constructor of a database. 
	 */
	private Database()
	{
		super();
	}

	@Override
	public void run() 
	{
		loadCompanies();
		loadProducts();
		loadUsers();
		loadWarehouses();
	}
	
	/**
	 * Select a specific type of data by the given id. Returns 
	 * null if it can't be found.
	 * @param identity - identity of the specific data to select
	 * @return the data found
	 */
	public Data select(int identity)
	{
		
		int type = identity & DATA_TYPE_MASK;
		
		List<Data> dataList = selectAll(type);
		
		for(Data data : dataList)
		{
			if (data.getId() == identity)
				return data;
		}
		
		return null;
	}
	
	/**
	 * Selects the data in a list of the given type. 
	 * Returns null if it fails.
	 * 
	 * @param type - type of data
	 * @return a list of all the data of the given type
	 */
	public List<Data> selectAll(int type)
	{
		List<Data> list;
		
		if (type == COMPANY)
			list = new ArrayList<Data>(companies);
		else if (type == PRODUCT)
			list = new ArrayList<Data>(products);
		else if (type == USER)
			list = new ArrayList<Data>(users);
		else if (type == WAREHOUSE)
			list = new ArrayList<Data>(warehouses);
		else
			list = null;
		
		return list;
	}
	
	/**
	 * Inserts a data into the database.
	 * 
	 * @param data - data to save
	 * @return true if it was inserted successfully. False otherwise.
	 */
	public boolean insert(Data data)
	{
		if (data == null)
			return false;
		
		int type = data.getId() & DATA_TYPE_MASK;		
		
		boolean success;
		if (type == COMPANY)
			success = companies.add((Company) data);
		else if (type == PRODUCT)
			success = products.add((Product) data);
		else if (type == USER)
			success = users.add((User) data);
		else if (type == WAREHOUSE)
			success = warehouses.add((Warehouse) data);
		else
			return false;
		
		save(type);
		
		return success;
	}
	
	/**
	 * Deletes a data based on the given id.
	 * @param id - id of the data to remove
	 * @return return true if it was removed successfully. False otherwise.
	 */
	public boolean delete(int id)
	{
		int type = id & DATA_TYPE_MASK;
		List<Data> dataList = selectAll(type);
		boolean success = false;
		for (Data data : dataList)
		{
			if (data.getId() == id)
			{
				if (type == COMPANY)
					success = companies.remove((Company) data);
				else if (type == PRODUCT)
					success = products.remove((Product) data);
				else if (type == USER)
					success = users.remove((User) data);
				else if (type == WAREHOUSE)
					success = warehouses.remove((Warehouse) data);
				else
					return false;
				break;
			}
		}
		save(type);
		return success;
	}
	
	/**
	 * Update a specific data based on the given id.
	 * @param id - id of the product to update
	 * @param type - type of data
	 * @param change - change to update
	 * @return true if it was updated successfully. False otherwise.
	 */
	public boolean update(int id, int type, Object change)
	{
		if (change == null)
			return false;
		
		Data data = select(id);
		if (data == null)
			return false;
		
		return data.update(type, change);
	}
	
	/**
	 * Saves all of the data back into disk storage. 
	 */
	public void save()
	{
		saveCompanies();
		saveProducts();
		saveUsers();
		saveWarehouses();
	}
	
	/**
	 * Saves the specific data back into disk storage.
	 * If no specific type is given then all of data will be saved.
	 * @param type - Type of data to store.
	 */
	private void save(int type)
	{
		if (type == COMPANY)
			saveCompanies();
		else if (type == PRODUCT)
			saveProducts();
		else if (type == USER)
			saveUsers();
		else if (type == WAREHOUSE)
			saveWarehouses();
		else
			save();
	}
	
	/**
	 * Loads the companies of the warehouse.
	 */
	private void loadCompanies()
	{
		List<Company> companies = IO.getInstance().inputCompanies();
		if (companies == null)
		{
			System.err.println("Failure to Load Companies");
			System.exit(-1);
		}
		this.companies = companies;
	}
	
	/**
	 * Loads the products of the database.
	 */
	private void loadProducts()
	{
		List<Product> products = IO.getInstance().inputProducts();
		if (products == null)
		{
			System.err.println("Failure to Load Products");
			System.exit(-1);
		}
		this.products = products;
	}
	
	/**
	 * Loads the users of the database.
	 */
	private void loadUsers()
	{
		List<User> users = IO.getInstance().inputUsers();
		if (users == null)
		{
			System.err.println("Failure to Load Users");
			System.exit(-1);
		}
		
		this.users = users;
		
		if (this.users.isEmpty())
		{
			User defaultUser = new User("Administrator", "Administrator", new Date(), 
					0x3000000, true, "administrator", "administrator");
			this.users.add(defaultUser);
		}
	}
	
	/**
	 * Loads the warehouses of the database.
	 */
	private void loadWarehouses()
	{
		List<Warehouse> warehouses = IO.getInstance().inputWarehouses();
		if (warehouses == null)
		{
			System.err.println("Failure to Load Warehouses");
			System.exit(-1);
		}
		this.warehouses = warehouses;		
	}
	
	/**
	 * Saves all of the companies to hard disk.
	 */
	private void saveCompanies()
	{
		IO.getInstance().outputCompanies(companies);
	}
	
	/**
	 * Saves all of the products to hard disk.
	 */
	private void saveProducts()
	{
		IO.getInstance().outputProducts(products);
	}
	
	/**
	 * Saves all of the users to hard disk.
	 */
	private void saveUsers()
	{
		IO.getInstance().outputUsers(users);
	}
	
	/**
	 * Saves all of the warehouses to hard disk.
	 */
	private void saveWarehouses()
	{
		IO.getInstance().outputWarehouses(warehouses);
	}
	
	/**
	 * Check whether a given id is valid or not based on the given type.
	 * @param type - type of data to check for
	 * @param id - new id to check for
	 * @return true if its unique and valid. False otherwise.
	 */
	public boolean validID(int id)
	{
		int type = id & DATA_TYPE_MASK;
		
		List<Data> list = selectAll(type);
		if (list == null)
			return false;
		
		for(Data data : list)
		{
			if (data.getId() == id)
				return false;
		}
		return true;
	}

	/**
	 * Generates an id for the specific type of data.
	 * 
	 * @param type - type of data
	 * @return new unique id
	 */
	public static int generateID(int type)
	{
		Random random = new Random();
		boolean success = false;
		int newID = 0;
		
		while (!success)
		{
			newID = random.nextInt();
			
			if (newID == 0)
				newID++;
			
			newID %= 0x10000;
			
			success = getInstance().validID(type + newID);
		}
		
		return type + newID;
	}
	
	/**
	 * Singleton method to get single instance of database.
	 * @return Database instance. If it doesn't exists then create a new one.
	 */
	public static Database getInstance()
	{
		if (instance == null)
			instance = new Database();
		return instance;
	}
}
