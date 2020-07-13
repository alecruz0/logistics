package view.gui.company;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import controller.Logistics;
import model.Company;
import model.Data;
import model.Database;
import model.Product;
import model.Warehouse;
import utilities.Utilities;

/**
 * Panel used to remove a company from the database.
 * @author Manuel Cruz
 * @version 1.0 
 *
 */
public class RemoveCompanyPanel extends AbstractCompanyPanel 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** Company id to remove */
	private int companyIdentity;

	/**
	 * Explicit constructor for this panel.
	 * @param manager - Manager that manages this panel
	 */
	public RemoveCompanyPanel(Logistics manager) 
	{
		super(manager);
	}
	
	@Override
	protected void createLabels()
	{
		super.createLabels();
		
		titleLabel.setText("Remove Company");
	}
	
	/**
	 * Gets a list of products that contain this company.
	 * @return list of id of products
	 */
	private List<Integer> getCompanyProducts()
	{
		List<Integer> productsRemove = new ArrayList<Integer>(); // empty list
		List<Data> products = Database.getInstance().selectAll(Database.PRODUCT);
		Data company = Database.getInstance().select(companyIdentity);
		
		// check all products
		// if one matches with this company
		// then it gets added
		for (Data data : products)
		{
			Product product = (Product) data;
			if (product.getCompany() == company.getId())
				productsRemove.add(product.getId());
		}
		return productsRemove;
	}
	
	/**
	 * Removes the company selected
	 */
	private void removeCompany()
	{
		// gets the products to be removed
		List<Integer> removingProducts = getCompanyProducts();
		List<Data> warehouses = Database.getInstance().selectAll(Database.WAREHOUSE);
		
		// removes the products from the warehouse
		for (Data data : warehouses)
		{
			if (!(data instanceof Warehouse))
			{
				String message = "RemoveCompanyPanel - removeCompany() - invalid instance";
				Utilities.getInstance().error(message);
			}
			
			Warehouse warehouse = (Warehouse) data;
			
			// checks all the products to be removed
			for (int product : removingProducts)
			{
				if (warehouse.contains(product))
					warehouse.remove(product, warehouse.getProductQuantity(product));
			}
		}
		
		// removes products from database
		for (int id : removingProducts)
			Database.getInstance().delete(id);
		
		
		
		// delete company
		boolean success = Database.getInstance().delete(companyIdentity);
		if (success) // if success show success message
		{	
			String title = "Remove Company";
			String message = "Product was successfully removed";
			showMessage(message, title, JOptionPane.INFORMATION_MESSAGE);			
			backButtonAction();
		}
		else // if failure show error message
		{
			String title = "Remove Error";
			String message = "Company could not be removed";
			showMessage(message, title, JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	@Override
	protected void createTextFields()
	{
		super.createTextFields();
		
		// disable textfields
		nameTextField.setEditable(false);
		dateTextField.setEditable(false);
		idTextField.setEditable(false);
	}

	@Override
	protected void confirmButtonAction() 
	{
		String message = "Are you sure you want to remove this company?\n"
				+ "It will also remove the products associated with this "
				+ "company\n"
				+ "As well as within the warehouses";
		String title = "Remove Company";
		int value = JOptionPane.showConfirmDialog(manager.getFrame(), message, title, JOptionPane.YES_NO_OPTION);
		if (value == 0)
			removeCompany();
	}
	
	@Override
	public void setData(int id)
	{
		Data data = Database.getInstance().select(id);
		
		// program must fail if null
		if (data == null)
		{
			String message = "RemoveCompanyPanel - setData(int id) - null ptr error "
					+ "error reading company: " + id;
			Utilities.getInstance().error(message);
		}
		
		// makes sure it is a company
		if (!(data instanceof Company))
		{
			String message = "RemoveCompanyPanel - setData(int id) - invalid instance "
					+ "error reading company: " + id;
			Utilities.getInstance().error(message);
		}
		
		Company company = (Company) data;
		
		nameTextField.setText(company.getName());
		dateTextField.setText(company.getDate().toString());
		idTextField.setText(Integer.toHexString(company.getId()));
		
		companyIdentity = id;
	}
}
