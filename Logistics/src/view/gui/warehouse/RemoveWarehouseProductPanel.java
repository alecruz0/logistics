package view.gui.warehouse;

import java.util.List;

import javax.swing.JOptionPane;

import controller.Logistics;
import model.Data;
import model.Database;
import model.Product;
import model.Warehouse;
import utilities.Utilities;
import view.AbstractWarehouseProductPanel;

/**
 * Panel used to remove a product to a warehouse.
 * @author Manuel Cruz
 * @version 1.0
 */
public class RemoveWarehouseProductPanel extends AbstractWarehouseProductPanel 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Explicit constructor for this panel.
	 * @param manager - manager that manages this panel
	 */
	public RemoveWarehouseProductPanel(Logistics manager) 
	{
		super(manager);
	}

	@Override
	public void reset() 
	{
		productComboBox.removeAllItems();
		
		Data data = Database.getInstance().select(warehouseIdentity);
		
		// check for null
		if (data == null)
		{
			String message = "RemoveWarehouseProductPanel - reset() - null ptr "
					+ "invalid Warehouse: " + warehouseIdentity;
			Utilities.getInstance().error(message);
		}
		
		// check instance
		if (!(data instanceof Warehouse))
		{
			String message = "RemoveWarehouseProductPanel - reset() - invalid instance "
					+ "invalid Warehouse: " + warehouseIdentity;
			Utilities.getInstance().error(message);
		}
		
		Warehouse warehouse = (Warehouse) data;
		List<Integer> productIdentities = warehouse.getProducts();
		
		for (int id : productIdentities)
		{
			data = Database.getInstance().select(id);
			productComboBox.addItem(data);
		}
		
		quantityTextField.setText("");
	}
	
	@Override
	public void setData(int id)
	{
		Data data = Database.getInstance().select(id);
		
		if (data == null)
		{
			String message = "RemoveWarehouseProductPanel - setData(int id) - null ptr "
					+ "invalid Warehouse: " + warehouseIdentity;
			Utilities.getInstance().error(message);
		}
		
		if (!(data instanceof Warehouse))
		{
			String message = "RemoveWarehouseProductPanel - setData(int id) - invalid instance "
					+ "invalid Warehouse: " + warehouseIdentity;
			Utilities.getInstance().error(message);
		}
		
		Warehouse warehouse = (Warehouse) data;
		String title = "Remove to " + warehouse.getName();
		titleLabel.setText(title);
		warehouseIdentity = id;
		reset();
	}
	
	/**
	 * Checks whether the given string is a valid quantity or not.
	 * @param text - string to check for
	 * @return true if valid quantity. false otherwise.
	 */
	private boolean validQuantity(String text)
	{
		if (text.isBlank()) // no blanks
			return false;
		
		try
		{
			int quantity = Integer.parseInt(text);
			
			Data data = Database.getInstance().select(warehouseIdentity);
			
			// check for null
			if (data == null)
			{
				String message = "RemoveWarehouseProductPanel - validQuantity(String text) "
						+ "- null ptr invalid warehouse: " + warehouseIdentity;
				Utilities.getInstance().error(message);
			}
			
			// check instance
			if (!(data instanceof Warehouse))
			{
				String message = "RemoveWarehouseProductPanel - validQuantity(String text) "
						+ "- invalid instance invalid warehouse: " + warehouseIdentity;
				Utilities.getInstance().error(message);
			}
			
			Warehouse warehouse = (Warehouse) data;
			Object object = productComboBox.getSelectedItem();
			
			// check for null
			if (object == null)
			{
				String message = "RemoveWarehouseProductPanel - validQuantity(String text) "
						+ "- null ptr";
				Utilities.getInstance().error(message);
			}
			
			// check instance
			if (!(object instanceof Product))
			{
				String message = "RemoveWarehouseProductPanel - validQuantity(String text) "
						+ "- invalid instance";
				Utilities.getInstance().error(message);
			}
			
			Product product = (Product) productComboBox.getSelectedItem();
			
			return quantity <= warehouse.getProductQuantity(product.getId()) &&
				   quantity >= 0;
		}
		catch (NumberFormatException nfe)
		{
			return false;
		}
	}
	
	/**
	 * Checks whether the product to be removed is  valid or not
	 * @return true if the remove is valid. false otherwise.
	 */
	private boolean validRemove()
	{
		// check product
		boolean validProduct = productComboBox.getItemCount() > 0;
		if (!validProduct)
		{
			String message = "Please makes sure the product is selected";
			String title = "Product Error";
			showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		// checks quantity
		boolean validQuantity = validQuantity(quantityTextField.getText().strip());
		if (!validQuantity)
		{
			String message = "Please make sure it is only positive\n"
					+ "numbers and does not go beyond product quantity in warehouse";
			String title = "Product Error";
			showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		return true;
	}
	
	/**
	 * Removes product from the selected warehouse.
	 * @return true if it was successfully removed. false otherwise.
	 */
	private boolean removeProduct()
	{
		Product product = (Product) productComboBox.getSelectedItem();
		int quantity = Integer.parseInt(quantityTextField.getText().strip());
		
		Warehouse warehouse = (Warehouse) Database.getInstance().select(warehouseIdentity);
		return warehouse.remove(product.getId(), quantity);
	}

	@Override
	public void confirmButtonAction() 
	{
		if (validRemove())
		{
			if (removeProduct())
			{
				String message = "Product has been successfully remove";
				String title = "Add Product";
				showMessage(message, title, JOptionPane.INFORMATION_MESSAGE);
				reset();
			}
			else
			{
				String message = "Product failed to be removed";
				String title = "Remove Product Error";
				showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
