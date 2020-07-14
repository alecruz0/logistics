package io;


import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import java.io.FileReader;
import java.io.IOException;

import model.Company;
import model.Date;
import model.Product;
import model.User;
import model.Warehouse;

/**
 * Input class to read data into program from files.
 * 
 * @author Manuel Cruz
 * @version 1.0
 */
class Input 
{
	/**
	 * Constructor of Input
	 */
	public Input()
	{
		super();
	}
	
	/**
	 * Reads companies from the given fileName. If there is a failure null will be returned.
	 * 
	 * @param fileName - file name of the companies.
	 * @return A list of all of the companies.
	 * @throws IOException
	 * @throws NumberFormatException
	 */
	public List<Company> companies(String fileName) throws IOException, NumberFormatException
	{
		// list of companies to get returned
		List<Company> companies = new ArrayList<Company>();
		File file = new File(fileName); // create file
		
		// check file exists
		if (!file.exists())
			return companies;
		
		// check it is not a directory
		if (file.isDirectory())
			return null;
		
		// if file is empty return an empty list
		if (file.length() == 0)
			return companies;
		
		// read every company
		BufferedReader br = new BufferedReader(new FileReader(file));
		while(true)
		{
			// read name
			String name = br.readLine();
			if (name == null)
				break;
			
			// read date created
			String date = br.readLine();
			if (date == null)
				break;
			
			// read id
			String id = br.readLine();
			if (id == null)
				break;
			
			// create company and add it to the list
			Company company = new Company(name.strip(), 
										  new Date(date.strip()), 
										  Integer.parseInt(id.strip()));
			companies.add(company);
		}
		
		br.close(); // close buffer
		
		return companies;
	}
	
	/**
	 * Reads the Products from the given fileName. If an error is found the null will be returned.
	 * @param fileName - file name where products are stored.
	 * @return a list containing all of the products.
	 * @throws IOException
	 * @throws NumberFormatException
	 */
	public List<Product> products(String fileName) throws IOException, NumberFormatException
	{
		// list of products to return
		List<Product> products = new ArrayList<Product>();	
		File file = new File(fileName); // create file
		
		// check file exists
		if (!file.exists())
			 return products;
		
		// check it is not a directory
		if (file.isDirectory())
			return null;
		
		// if file is empty then return an empty list
		if (file.length() == 0)
			return products;
		
		// create buffer
		BufferedReader br = new BufferedReader(new FileReader(file));
		while(true)
		{
			// read name
			String name = br.readLine();
			if (name == null)
				break;
			
			// read company id
			String companyid = br.readLine();
			if (companyid == null)
				break;
			
			// read weight
			String weight = br.readLine();
			if (weight == null)
				break;
			
			// read date
			String date = br.readLine();
			if (date == null)
				break;
			
			// read the product id
			String id = br.readLine();
			if (id == null)
				break;
			
			// create new product and add it to the list
			Product product = new Product(name.strip(),
										  Integer.parseInt(companyid.strip()), 
										  Integer.parseInt(id.strip()), 
										  Double.parseDouble(weight.strip()), 
										  new Date(date.strip()));
			
			products.add(product);
		}
		
		br.close(); // close buffer
		
		return products;
	}
	
	/**
	 * Read the users from the given file name. Returns null if an error occurs.
	 * @param fileName - File name where users are stored
	 * @return a list containing the users.
	 * @throws IOException
	 * @throws NumberFormatException
	 */
	public List<User> users(String fileName) throws IOException, NumberFormatException
	{
		// list of users to return
		List<User> users = new ArrayList<User>();
		File file = new File(fileName); // create file
		
		// check file exists
		if (!file.exists())
			return users;
		
		// check it is not a directory
		if (file.isDirectory())
			return null;
		
		// returns an empty list if the file is empty
		if (file.length() == 0)
			return users;
		
		// create buffer
		BufferedReader bf = new BufferedReader(new FileReader(file));
		while(true)
		{
			// read the first name
			String first = bf.readLine();
			if (first == null)
				break;
			
			// read the last name
			String last = bf.readLine();
			if (last == null)
				break;
			
			// read the date
			String date = bf.readLine();
			if (date == null)
				break;
			
			// read the given id
			String id = bf.readLine();
			if (id == null)
				break;
			
			// read the administrator
			String admin = bf.readLine();
			if (admin == null)
				break;
			
			// reads the username
			String username = bf.readLine();
			if (username == null)
				break;
			
			// read the password
			String password = bf.readLine();
			if (password == null)
				break;
			
			// create a user and add it to the list
			User user = new User(first.strip(), 
								 last.strip(), 
								 new Date(date.strip()), 
								 Integer.parseInt(id.strip()), 
								 Boolean.parseBoolean(admin.strip()), 
								 username.strip(), 
								 password.strip());
			users.add(user);
		}
		
		bf.close(); // close buffer
		
		return users;
	}
	
	/**
	 * Reads warehouses from a given file. Return null if it fails.
	 * 
	 * @param filename - filename where warehouses are stored
	 * @return a list of products
	 * @throws IOException
	 * @throws NumberFormatException
	 */
	public List<Warehouse> warehouses(String filename) throws IOException, NumberFormatException
	{
		// list of warehouses to return
		List<Warehouse> warehouses = new ArrayList<Warehouse>();
		
		// file of warehouses
		File file = new File(filename);
		
		// if it doesn't exists return empty list
		if (!file.exists())
			return warehouses;
		
		// if it is a directory return null
		if (file.isDirectory())
			return null;
		
		// if the file is empty return the empty list
		if (file.length() == 0)
			return warehouses;
		
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		while (true)
		{
			// get name
			String name = br.readLine();
			if (name == null)
				break;
			
			// get capacity
			String capacity = br.readLine();
			if (capacity == null)
				break;
			
			// get date
			String date = br.readLine();
			if (date == null)
				break;
			
			// get identity
			String identity = br.readLine();
			if (identity == null)
				break;
			
			Warehouse warehouse = new Warehouse(name.strip(), 
												Integer.parseInt(capacity.strip()), 
												new Date(date.strip()), 
												Integer.parseInt(identity.strip()));
			
			// number of products
			String productNumberString = br.readLine();
			if (productNumberString == null)
				break;
			
			int productNumber = Integer.parseInt(productNumberString);
			
			// read all of the products of the warehouse
			for (int i = 0; i < productNumber; i++)
			{
				String[] values = br.readLine().split(",");
				warehouse.add(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
			}
			
			warehouses.add(warehouse);
		}
		
		br.close();
		
		return warehouses;
	}
	
}
