package view.gui.product;

import java.util.List;

import javax.swing.JOptionPane;

import controller.Logistics;
import model.Data;
import model.Database;
import model.Product;
import model.Warehouse;
import utilities.Utilities;

/**
 * Panel used to remove a product from the database.
 * @author Manuel Cruz
 * @version 1.0
 */
public class RemoveProductPanel extends AbstractProductPanel 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Product id to remove */
	private int productIdentity;
	
	/**
	 * Explicit constructor for this panel.
	 * @param manager - Manager that mamanges this panel.
	 */
	public RemoveProductPanel(Logistics manager) 
	{
		super(manager);
	}
	
	@Override
	protected void createLabels()
	{
		super.createLabels();
		
		titleLabel.setText("Remove Product");
	}
	
	@Override
	protected void createBoxes()
	{
		super.createBoxes();
		companyComboBox.setEnabled(false);
	}
	
	@Override
	protected void createTextFields()
	{
		super.createTextFields();
		
		// disable textfields
		nameTextField.setEditable(false);
		dateTextField.setEditable(false);
		weightTextField.setEditable(false);
		idTextField.setEditable(false);
	}
	
	/**
	 * Removes the product selected
	 */
	private void removeProduct()
	{
		// removes product from warehouse containing this product
		List<Data> warehouses = Database.getInstance().selectAll(Database.WAREHOUSE);
		for (Data data : warehouses)
		{
			if (!(data instanceof Warehouse))
			{
				String message = "RemoveProductPanel - removeProduct() - invalid instance";
				Utilities.getInstance().error(message);
			}
			
			Warehouse warehouse = (Warehouse) data;
			if (warehouse.contains(productIdentity))
				warehouse.remove(productIdentity, warehouse.getProductQuantity(productIdentity));
		}
		
		// remove product
		boolean success = Database.getInstance().delete(productIdentity);
		if (success) // if success show success message
		{
			String title = "Remove Product";
			String message = "Product was successfully removed";
			showMessage(message, title, JOptionPane.INFORMATION_MESSAGE);
			backButtonAction();
		}
		else // if failure show error message
		{
			String title = "Remove Error";
			String message = "Product could not be removed";
			showMessage(message, title, JOptionPane.INFORMATION_MESSAGE);
		}
	}

	@Override
	protected void confirmButtonAction() 
	{
		String message = "Are you sure you want to remove this product?\n"
				+ "It will also remove products from warehouses that contain this product";
		String title = "Remove Product";
		int value = JOptionPane.showConfirmDialog(manager.getFrame(), message, title, JOptionPane.YES_NO_OPTION);
		if (value == 0)
			removeProduct();
	}

	@Override
	public void setData(int id)
	{
		Data data = Database.getInstance().select(id);
		
		// program must fail if null
		if (data == null)
		{
			String message = "RemoveProductPanel - setData(int id) - null ptr error "
					+ "reading product: " + id;
			Utilities.getInstance().error(message);
		}
		
		// makes sure it is a product
		if (!(data instanceof Product))
		{
			String message = "RemoveProductPanel - setData(int id) - invalid instance "
					+ "error reading product: " + id;
			Utilities.getInstance().error(message);
		}
		
		Product product = (Product) data;
		
		nameTextField.setText(product.getName());
		companyComboBox.setSelectedItem(Database.getInstance().select(product.getCompany()));
		weightTextField.setText(product.getWeight() + "");
		dateTextField.setText(product.getDate().toString());
		idTextField.setText(Integer.toHexString(product.getId()));
		
		productIdentity = id;
	}
}
