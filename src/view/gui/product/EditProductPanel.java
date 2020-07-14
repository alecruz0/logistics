package view.gui.product;

import java.util.List;

import javax.swing.JOptionPane;

import controller.Logistics;
import model.Company;
import model.Data;
import model.Database;
import model.Date;
import model.Product;
import utilities.Utilities;

/**
 * Panel used to edit a product in the warehouse
 * @author Manuel Cruz
 * @version 1.0
 */
public class EditProductPanel extends AbstractProductPanel 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** id of product being edit */
	private int productIdentity;

	/**
	 * Explicit constructor for this panel
	 * @param manager - manager that manages this panel
	 */
	public EditProductPanel(Logistics manager) 
	{
		super(manager);
	}
	
	@Override
	protected void createLabels()
	{
		super.createLabels();
		
		titleLabel.setText("Edit Product");
	}
	
	/**
	 * Checks whether the weights have changed.
	 * @param s1 - first weight to check
	 * @param s2 - second weight to check
	 * @return true if both weights are different. False otherwise.
	 */
	private boolean differentWeights(String s1, String s2)
	{
		double w1 = 0, w2 = 0;
		try
		{
			w1 = Double.parseDouble(s1);
			w2 = Double.parseDouble(s2);
		}
		catch (NumberFormatException nfe)
		{
			return true;
		}
		
		return w1 != w2;
	}
	
	/**
	 * Checks whetherthere has been a change in the product being editted.
	 * @return true if there was a change. false otherwise.
	 */
	private boolean change()
	{
		Data data = Database.getInstance().select(productIdentity);
		
		// program must fail if null
		if (data == null)
		{
			String message = "EditProductPanel - change() - null ptr error "
					+ "reading product: " + productIdentity;
			Utilities.getInstance().error(message);
		}
		
		// makes sure it is productor else failure
		if (!(data instanceof Product))
		{
			String message = "EditProductPanel - change() - invalid instance "
					+ "error reading product: " + productIdentity;
			Utilities.getInstance().error(message);
		}
		
		Product product = (Product) data;
		
		// checks for change in the name
		boolean name = !nameTextField.getText().strip().equals(product.getName());
		if (name) return true;

		// checks for change in the company
		boolean company = ((Company) companyComboBox.getSelectedItem()).getId() != product.getCompany();
		if (company) return true;
		
		// checks for change in the weight
		boolean weight = differentWeights(product.getWeight() + "", weightTextField.getText().strip());
		if (weight) return true;
		
		// checks for change in the date
		boolean date = !dateTextField.getText().strip().equals(product.getDate().toString());
		if (date) return true;
		
		// checks for change in the identity
		boolean identity = !idTextField.getText().strip().equals(Integer.toHexString(product.getId()));
		if (identity) return true;
		
		return false;
	}
	
	/**
	 * Makes sure the changes that were made are valid.
	 * @return true if all the changes are valid. false otherwise.
	 */
	private boolean validChange()
	{
		// checks change in name
		boolean name = !nameTextField.getText().isBlank();
		if (!name)
		{
			String message = "Make sure it is not empty";
			String title = "Name Error";
			showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		// checks change in company
		boolean company = companyComboBox.getItemCount() > 0;
		if (!company)
		{
			String message = "Make sure a company is selected. If not add companies";
			String title = "Company Error";
			showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		// checks change in weight
		boolean weight = validWeight(weightTextField.getText().strip());
		if (!weight)
		{
			String message = "Make sure it is only numbers and positive values";
			String title = "Weight Error";
			showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		// checks change in date
		boolean date = Date.validDate(dateTextField.getText().strip());
		if (!date)
		{
			String message = "Make sure it is in the format of mm/dd/yyyy";
			String title = "Date Error";
			showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		// checks change in identity
		boolean identity = validIdentity(idTextField.getText().strip());
		if (!identity)
		{
			String message = "Make sure this ID is unique.\n"
					   + "Length of ID must be 7 characters\n"
					   + "Make sure the first 3 digits are 200\n"
					   + "Make sure the characters are numbers (0-9)\n"
					   + "and/or letters (a-f)";
			String title = "Identity Error";
			showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		return true;
	}
	
	/**
	 * Checks whether the given string is a valid identity or not.
	 * Makes sure the first 3 digits are the id for product
	 * @param text - string to check for
	 * @return true if it is valid string. False otherwise.
	 */
	private boolean validIdentity(String text)
	{
		if (text.isBlank()) // no blanks
			return false;
		
		if (text.strip().length() != 7) // must be 7 chars
			return false;
		
		try
		{
			int identity = Integer.parseInt(text, 0x10);
			
			if ((identity & Database.DATA_TYPE_MASK) != Database.PRODUCT)
				return false;
			
			List<Data> products = Database.getInstance().selectAll(Database.PRODUCT);
			for (Data data : products)
			{
				if (data.getId() == identity && data.getId() != productIdentity)
					return false;
			}
			return true;
		}
		catch (NumberFormatException nfe) // invalid int
		{
			return false;
		}
	}
	
	/**
	 * Checks whether the given string is a valid string or not.
	 * @param text - string to check
	 * @return true if it is a valid weight. false otherwise.
	 */
	private boolean validWeight(String text)
	{
		try
		{
			double value = Double.parseDouble(text);
			return value >= 0;
		}
		catch (NumberFormatException nfe)
		{
			return false;
		}
	}
	
	/**
	 * Updates the change made to this product.
	 * @return true if all changes are successful. False otherwise.
	 */
	private boolean updateChange()
	{
		Database dtbs = Database.getInstance();
		
		//  updates name
		boolean name = dtbs.update(productIdentity, 
								   Product.PRODUCT_NAME, 
								   nameTextField.getText().strip());
		// checks for failure
		if (!name)
		{
			String message = "Failure to change the name";
			String title = "Name Error";
			showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		// updates company
		boolean company = dtbs.update(productIdentity, 
									  Product.PRODUCT_COMPANY, 
									  ((Company) companyComboBox.getSelectedItem()).getId());
		// checks for failure
		if (!company)
		{
			String message = "Failure to change the company";
			String title = "Company Error";
			showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		// updates weight
		boolean weight = dtbs.update(productIdentity, 
									 Product.PRODUCT_WEIGHT, 
									 Double.parseDouble(weightTextField.getText().strip()));
		// checks for failure
		if (!weight)
		{
			String message = "Failure to change the weight";
			String title = "Weight Error";
			showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		// updates date
		boolean date = dtbs.update(productIdentity, 
								   Product.PRODUCT_DATE, 
								   new Date(dateTextField.getText().strip()));
		// checks for failure
		if (!date)
		{
			String message = "Failure to change the date";
			String title = "Date Error";
			showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		// updates identity
		boolean identity = dtbs.update(productIdentity, 
									   Product.PRODUCT_ID, 
									   Integer.parseInt(idTextField.getText().strip(), 0x10));
		// checks for identity
		if (!identity)
		{
			String message = "Failure to change the ID";
			String title = "ID Error";
			showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		// since id was changed, change this one too
		productIdentity = Integer.parseInt(idTextField.getText().strip(), 0x10);
		
		return true;
	}

	@Override
	protected void confirmButtonAction() 
	{
		if (change()) // check for change
		{
			if (validChange()) // check is a valid change
			{
				if (updateChange()) // set changes
				{
					// show success message
					String message = "Product has been edited";
					String title = "Edit Product";
					showMessage(message, title, JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
		else
		{
			String message = "There is no change";
			String title = "Edit Product";
			showMessage(message, title, JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	@Override
	public void setData(int id)
	{
		Data data = Database.getInstance().select(id);
		
		if (!(data instanceof Product))
		{
			String message = "EditProductPanel - setData(int id) - invalid instance "
					+ "error reading product: " + id;
			Utilities.getInstance().error(message);
		}
		
		Product product = (Product) data;
		
		reset();
		nameTextField.setText(product.getName());
		companyComboBox.setSelectedItem(Database.getInstance().select(product.getCompany()));
		weightTextField.setText(product.getWeight() + "");
		dateTextField.setText(product.getDate().toString());
		idTextField.setText(Integer.toHexString(product.getId()));
		
		productIdentity = id;
	}
}
