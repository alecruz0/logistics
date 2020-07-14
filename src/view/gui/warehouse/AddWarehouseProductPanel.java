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
 * Panel used to add a product to a warehouse.
 * @author Manuel Cruz
 * @version 1.0
 */
public class AddWarehouseProductPanel extends AbstractWarehouseProductPanel 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Explicit constructor for this panel.
	 * @param manager - manager that manages this panel.
	 */
	public AddWarehouseProductPanel(Logistics manager) 
	{
		super(manager);
	}

	@Override
	public void reset() 
	{
		productComboBox.removeAllItems();
		List<Data> products = Database.getInstance().selectAll(Database.PRODUCT);
		for (Data data : products)
			productComboBox.addItem(data);
		quantityTextField.setText("");
	}

	@Override
	public void setData(int id)
	{
		Data data = Database.getInstance().select(id);
		
		// if null program must end
		if (data == null)
		{
			String message = "AddWarehouseProductPanel - setData(int id) - null ptr "
					+ "invalid warehouse: " + id;
			Utilities.getInstance().error(message);
		}
		
		// must be valid instance
		if (!(data instanceof Warehouse))
		{
			String message = "AddWarehouseProductPanel - setData(int id) - invalid instance "
					+ "invalid warehouse: " + id;
			Utilities.getInstance().error(message);
		}
		
		Warehouse warehouse = (Warehouse) data;
		
		String title = "Add to " + warehouse.getName();
		titleLabel.setText(title);
		warehouseIdentity = id;
		reset();
	}
	
	/**
	 * Checks whether the given string is a valid quantity
	 * @param text - string to check for
	 * @return true if the given string is valid. false otherwise.
	 */
	private boolean validQuantity(String text)
	{
		if (text.isBlank()) // no blanks
			return false;
		
		try
		{
			int quantity = Integer.parseInt(text);
			
			Data data = Database.getInstance().select(warehouseIdentity);
			
			if (data == null)
			{
				String message = "AddWarehouseProductPanel - validQuantity(String text) - "
						+ "null ptr invalid warehouse: " + warehouseIdentity;
				Utilities.getInstance().error(message);
			}
			
			if (!(data instanceof Warehouse))
			{
				String message = "AddWarehouseProductPanel - validQuantity(String text) - "
						+ "invalid instance invalid warehouse: " + warehouseIdentity;
				Utilities.getInstance().error(message);
			}
			
			Warehouse warehouse = (Warehouse) data;
			
			return warehouse.getQuantity() + quantity <= warehouse.getCapacity() &&
				   quantity >= 0;
		}
		catch (NumberFormatException nfe)
		{
			return false;
		}
	}

	/**
	 * Checks whether the product to be added is valid or not.
	 * @return true if the product to be added is valid. false otherwise.
	 */
	private boolean validAdd()
	{
		// check product
		boolean validProduct = productComboBox.getItemCount() > 0;
		if (!validProduct)
		{
			String message = "Invalid product please makes sure a product is selected.";
			String title = "Product Error";
			showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		// check quantity
		boolean validQuantity = validQuantity(quantityTextField.getText().strip());
		if (!validQuantity)
		{
			String message = "Invalid quantity please make sure it is only positive\n"
					+ "numbers and does not go beyond warehouse capacity";
			String title = "Product Error";
			showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		return true;
	}
	
	/**
	 * Addsproduct to the selected warehouse
	 * @return true if it was successfully added. false otherwise.
	 */
	private boolean addProduct()
	{
		Object object = productComboBox.getSelectedItem();
		
		// if null product
		if (object == null)
		{
			String message = "AddWarehouseProductPanel - addProduct() - null ptr "
					+ "invalid product";
			Utilities.getInstance().error(message);
		}
		
		// if invalid instance
		if (!(object instanceof Product))
		{
			String message = "AddWarehouseProductPanel - addProduct() - invalid instance "
					+ "invalid product";
			Utilities.getInstance().error(message);
		}

		Product product = (Product) object;
		int quantity = Integer.parseInt(quantityTextField.getText().strip());
		object = Database.getInstance().select(warehouseIdentity);
		
		// if null product
		if (object == null)
		{
			String message = "AddWarehouseProductPanel - addProduct() - null ptr "
					+ "invalid warehouse";
			Utilities.getInstance().error(message);
		}
		
		// if invalid instance
		if (!(object instanceof Warehouse))
		{
			String message = "AddWarehouseProductPanel - addProduct() - invalid instance "
					+ "invalid warehouse";
			Utilities.getInstance().error(message);
		}
		
		Warehouse warehouse = (Warehouse) object;
		
		return warehouse.add(product.getId(), quantity);
	}

	@Override
	public void confirmButtonAction() 
	{
		if (validAdd()) // check for valid add
		{
			if (addProduct()) // adds product to the warehouse
			{
				String message = "Product has been successfully added";
				String title = "Add Product";
				showMessage(message, title, JOptionPane.INFORMATION_MESSAGE);
				reset();
			}
			else
			{
				String message = "Product failed to be added";
				String title = "Add Product Error";
				showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
