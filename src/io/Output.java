package io;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import model.Company;
import model.Product;
import model.User;
import model.Warehouse;
import utilities.Utilities;

/**
 * Controls output of data for storage.
 * 
 * @author Manuel Cruz
 * @Version 1.0
 */
class Output 
{
	/**
	 * Simple constructor to initialize.
	 */
	public Output()
	{
		super();
		String dataDirectory = "../data";
		File directory = new File(dataDirectory);
		if (!directory.exists())
		{
			if (!directory.mkdir())
				Utilities.getInstance().error("Output() - Could not make directory of data");
		}
	}
	
	/**
	 * Stores a list of warehouses.
	 * 
	 * @param warehouses - List of warehouses
	 * @throws IOException - IOExcepton to be catch
	 */
	public void warehouses(List<Warehouse> warehouses) throws IOException
	{
		// gets the warehouse file
		File file = new File("../data/warehouses.u");
		
		// if it doesn't exists just create a new one
		if (!file.exists())
			file.createNewFile();
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		
		// for each warehouse store it
		for (Warehouse warehouse : warehouses)
		{
			bw.write(warehouse.getName() + "\n");
			bw.write(warehouse.getCapacity() + "\n");
			bw.write(warehouse.getDate() + "\n");
			bw.write(warehouse.getId() + "\n");
			List<Integer> products = warehouse.getProducts();
			bw.write(products.size() + "\n");
			
			// store each product of the warehouse
			for (int i = 0; i < products.size(); i++)
			{
				int productIdentity = products.get(i);
				bw.write(productIdentity + "," + warehouse.getProductQuantity(productIdentity) + "\n");
			}
		}
		
		bw.flush();
		bw.close();
	}
	
	/**
	 * Stores a list of products.
	 * 
	 * @param products - list of products to store.
	 * @throws IOException - IOException to catch
	 */
	public void products(List<Product> products) throws IOException
	{
		// gets products file
		File file = new File("../data/products.u");
		
		// if it doesn't exist create a new one
		if (!file.exists())
			file.createNewFile();
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		
		// store all of products
		for (Product product : products)
		{
			bw.write(product.getName() + "\n");
			bw.write(product.getCompany() + "\n");
			bw.write(product.getWeight() + "\n");
			bw.write(product.getDate() + "\n");
			bw.write(product.getId() + "\n");
		}
		
		// flush and close file
		bw.flush();
		bw.close();
	}

	/**
	 * Stores a list of companies.
	 * 
	 * @param companies - list of companies to store.
	 * @throws IOException - IOException to catch
	 */
	public void companies(List<Company> companies) throws IOException 
	{
		// gets companies file
		File file = new File("../data/companies.u");
		
		// if it doesn't exist create a new one
		if (!file.exists())
			file.createNewFile();
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		// store each company
		for (Company company : companies)
		{
			bw.write(company.getName() + "\n");
			bw.write(company.getDate() + "\n");
			bw.write(company.getId() + "\n");
		}
		
		// flush and close file
		bw.flush();
		bw.close();
	}

	/**
	 * Stores a list of users.
	 * 
	 * @param users - list of users to store
	 * @throws IOException - IOException to catch
	 */
	public void users(List<User> users) throws IOException 
	{
		// gets the users file
		File file = new File("../data/users.u");
		
		// if it doesn't exist create a new one
		if (!file.exists())
			file.createNewFile();
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		
		// store every user
		for (User user : users)
		{
			bw.write(user.getFirstName()    + "\n");
			bw.write(user.getLastName()     + "\n");
			bw.write(user.getBirthday()     + "\n");
			bw.write(user.getId()           + "\n");
			bw.write(user.isAdministrator() + "\n");
			bw.write(user.getUsername()     + "\n");
			bw.write(user.getPassword()     + "\n");
		}
		
		// flush and close file
		bw.flush();
		bw.close();
	}
}
