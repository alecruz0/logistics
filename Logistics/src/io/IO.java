package io;


import java.io.IOException;
import java.util.List;

import model.Company;
import model.Product;
import model.User;
import model.Warehouse;

/**
 * IO Class to control input and output of data.
 * @author Manuel Cruz
 * @version 1.0
 */
public class IO 
{	
	/** Singleton instance */
	private static IO instance;
	
	/** input */
	private Input input;
	
	/** output */
	private Output output;
	
	/**
	 * Default constructor for IO.
	 */
	private IO()
	{
		super();
		input = new Input();
		output = new Output();
	}
	
	/**
	 * Output of companies to store in files.
	 * @param companies - list of companies to store
	 */
	public void outputCompanies(List<Company> companies)
	{
		try 
		{
			output.companies(companies);
		} 
		catch (IOException e) 
		{
			System.err.println("IO(outputCompanies) - IOException: Failure in storing companies from files");
			System.exit(-1);
		}
	}
	
	/**
	 * Output warehouses to store in files.
	 * @param warehouses - list of warehouses to store.
	 */
	public void outputWarehouses(List<Warehouse> warehouses)
	{
		try 
		{
			output.warehouses(warehouses);
		} 
		catch (IOException e) 
		{
			System.err.println("IO(outputWarehouses) - IOException: Failure in storing warehouses from files");
			System.exit(-1);
		}
	}
	
	/**
	 * Output products to store in files.
	 * @param products - list of products to store.
	 */
	public void outputProducts(List<Product> products)
	{
		try 
		{
			output.products(products);
		} 
		catch (IOException e) 
		{
			System.err.println("IO(outputProducts) - IOException: Failure in storing products from files");
			System.exit(-1);
		}
	}
	
	/**
	 * Output users to store in files.
	 * @param users - list of users to store.
	 */
	public void outputUsers(List<User> users)
	{
		try 
		{
			output.users(users);
		} 
		catch (IOException e) 
		{
			System.err.println("IO(outputUsers) - IOException: Failure in storing users from files");
			System.exit(-1);
		}
	}
	
	/**
	 * Input companies from files.
	 * @return A list of companies.
	 */
	public List<Company> inputCompanies()
	{
		try 
		{
			return input.companies("../data/companies.u");
		}
		catch (NumberFormatException nfe)
		{
			System.err.println("IO(inputCompanies) - Number Format Exception: String conversion to number");
			return null;
		}
		catch (IOException ioe) 
		{
			System.err.println("IO(inputCompanies) - IOException: IO Reading file");
			return null;
		}
	}
	
	/**
	 * Input products from files.
	 * @return A list of Products.
	 */
	public List<Product> inputProducts()
	{
		try 
		{
			return input.products("../data/products.u");
		} 
		catch (NumberFormatException e) 
		{
			System.err.println("IO(inputProducts) - Number Format Exception: String conversion to number");
			return null;
		} 
		catch (IOException e) 
		{
			System.err.println("IO(inputProducts) - IOException: IO Reading file");
			return null;
		}
	}
	
	/**
	 * Input users from files.
	 * @return A list of users.
	 */
	public List<User> inputUsers()
	{
		try 
		{
			return input.users("../data/users.u");
		} 
		catch (NumberFormatException e) 
		{
			System.err.println("IO(inputUsers) - Number Format Exception: String conversion to number");
			return null;
		} 
		catch (IOException e) 
		{
			System.err.println("IO(inputUsers) - IOException: IO Reading file");
			return null;
		}
	}
	
	/**
	 * Input warehouses from files.
	 * @return A list of warehouses.
	 */
	public List<Warehouse> inputWarehouses()
	{
		try 
		{
			return input.warehouses("../data/warehouses.u");
		} 
		catch (NumberFormatException e) 
		{
			System.err.println("IO(inputWarehouses) - Number Format Exception: String conversion to number");
			return null;
		} 
		catch (IOException e) 
		{
			System.err.println("IO(inputWarehouses) - IOException: IO Reading file");
			return null;
		}
	}
	
	/**
	 * Singleton for single instance.
	 * @return unique instance.
	 */
	public static IO getInstance()
	{
		if (instance == null)
			instance = new IO();
		return instance;
	}
}
