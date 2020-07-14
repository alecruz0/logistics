package view.gui.warehouse;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JButton;

import controller.Logistics;
import model.Data;
import model.Database;
import model.Product;
import model.Warehouse;
import utilities.Utilities;
import utilities.ViewFactory;
import view.gui.AbstractTablePanel;

/**
 * Shows the products of the selected warehouse.
 * @author Manuel Cruz
 * @version 1.0
 */
public class ShowWarehouseProductPanel extends AbstractTablePanel implements Comparator<Integer> 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** Constant used for sorting by product */
	private final static int PRODUCT = 0;
	
	/** Constant used for sorting by quantity */
	private final static int QUANTITY = 1;
	
	/** Warehouse selected */
	private int warehouseIdentity;
	
	/** type of sorting */
	private int sorting = PRODUCT;

	/**
	 * Explicit constructor for this panel.
	 * @param manager - manager that manages this panel
	 */
	public ShowWarehouseProductPanel(Logistics manager) 
	{
		super(manager, Database.PRODUCT);
	}
	
	@Override
	protected void setup()
	{
		createComponents();
		addComponents();
		layoutComponents();
		panelSettings();
	}
	
	@Override
	protected void createButtons()
	{
		super.createButtons();
		editButton.setText("BACK");
	}

	@Override
	protected void comboBoxAction() 
	{
		String sortingMethod = (String) sortComboBox.getSelectedItem();
		if (sortingMethod.equals("Product"))
			sorting = PRODUCT;
		else if (sortingMethod.equals("Quantity"))
			sorting = QUANTITY;
		
		reset();
	}
	
	@Override
	protected void buttonsActions(JButton button)
	{
		if (button.getText().equals("ADD"))
			addButtonAction();
		else if (button.getText().equals("BACK"))
			editButtonAction();
		else if (button.getText().equals("REMOVE"))
			removeButtonAction();
	}

	@Override
	protected void addButtonAction() 
	{
		manager.changePanel(ViewFactory.ADD + ViewFactory.WAREHOUSES + ViewFactory.PRODUCTS, warehouseIdentity);
	}

	@Override
	protected void editButtonAction() 
	{
		manager.changePanel(ViewFactory.SHOW + ViewFactory.WAREHOUSES, Logistics.NO_ID);
	}

	@Override
	protected void removeButtonAction() 
	{
		manager.changePanel(ViewFactory.REMOVE + ViewFactory.WAREHOUSES + ViewFactory.PRODUCTS, warehouseIdentity);
	}

	@Override
	protected Object[][] generateData() 
	{
		Data data = Database.getInstance().select(warehouseIdentity);
		
		if (data == null)
		{
			String message = "ShowWarehouseProductPanel - generateData() - null ptr "
					+ "invalid warehouse: " + warehouseIdentity;
			Utilities.getInstance().error(message);
		}
		
		if (!(data instanceof Warehouse))
		{
			String message = "ShowWarehouseProductPanel - generateData() - invalid instance "
					+ "invalid warehouse: " + warehouseIdentity;
			Utilities.getInstance().error(message);
		}
		
		Warehouse warehouse = (Warehouse) data;
		List<Integer> productIdentities = warehouse.getProducts();
		Collections.sort(productIdentities, this);
		int numberOfCompanies = productIdentities.size();
		int headerSize = getHeaderDetails().length;
		
		Object[][] dataArray = new Object[numberOfCompanies][headerSize];
		
		for (int i = 0; i < productIdentities.size(); i++)
		{
			data = Database.getInstance().select(productIdentities.get(i));
			
			if (data == null)
			{
				String message = "ShowWarehouseProductPanel - generateData() - null ptr "
						+ "invalid product: " + productIdentities.get(i);
				Utilities.getInstance().error(message);
			}
			
			if (!(data instanceof Product))
			{
				String message = "ShowWarehouseProductPanel - generateData() - invalid instance "
						+ "invalid product: " + productIdentities.get(i);
				Utilities.getInstance().error(message);
			}
			
			Product product = (Product) data;
			dataArray[i][0] = product.getName();
			dataArray[i][1] = warehouse.getProductQuantity(productIdentities.get(i));
		}
		
		return dataArray;
	}

	@Override
	protected String[] getHeaderDetails() 
	{
		String[] header = {"Product", "Quantity"};
		return header;
	}

	@Override
	public void setData(int id)
	{
		warehouseIdentity = id;
		updateTable();
		Data data = Database.getInstance().select(id);
		
		if (data == null)
		{
			String message = "ShowWarehouseProductPanel - setData() - null ptr "
					+ "invalid warehouse: " + warehouseIdentity;
			Utilities.getInstance().error(message);
		}
		
		if (!(data instanceof Warehouse))
		{
			String message = "ShowWarehouseProductPanel - setData() - invalid instance "
					+ "invalid warehouse: " + warehouseIdentity;
			Utilities.getInstance().error(message);
		}
		
		Warehouse warehouse = (Warehouse) data;
		titleLabel.setText(warehouse.getName());
	}
	
	@Override
	public void reset()
	{
		
	}

	@Override
	public int compare(Integer i1, Integer i2) 
	{
		Data d1 = Database.getInstance().select(i1);
		Data d2 = Database.getInstance().select(i2);
		
		if (sorting == PRODUCT)
			return d1.compareTo(d2);
		
		Warehouse warehouse = (Warehouse) Database.getInstance().select(warehouseIdentity);
		return Integer.compare(warehouse.getProductQuantity(i1), warehouse.getProductQuantity(i2));
	}
}
