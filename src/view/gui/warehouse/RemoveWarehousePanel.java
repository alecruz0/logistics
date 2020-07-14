package view.gui.warehouse;

import javax.swing.JOptionPane;

import controller.Logistics;
import model.Data;
import model.Database;
import model.Warehouse;
import utilities.Utilities;

/**
 * Panel used to remove a warehouse from the database.
 * @author Manuel Cruz
 * @version 1.0
 */
public class RemoveWarehousePanel extends AbstractWarehousePanel 
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Warehouse id to remove */
	private int warehouseIdentity;
	
	/**
	 * Explicit constructor for this panel.
	 * @param manager - Manager that manages this panel
	 */
	public RemoveWarehousePanel(Logistics manager) 
	{
		super(manager);
	}
	
	@Override
	protected void createLabels()
	{
		super.createLabels();
		
		titleLabel.setText("Remove Warehouse");
	}
	
	@Override
	protected void createTextFields()
	{
		super.createTextFields();
		
		// disable textfields
		nameTextField.setEditable(false);
		capacityTextField.setEditable(false);
		dateTextField.setEditable(false);
		idTextField.setEditable(false);
	}
	
	/**
	 * Removes the warehouse selected
	 */
	private void removeWarehouse()
	{
		// delete company
		boolean success = Database.getInstance().delete(warehouseIdentity);
		if (success) // if success show success message
		{
			String title = "Remove Warehouse";
			String message = "Warehouse was successfully removed";
			showMessage(message, title, JOptionPane.INFORMATION_MESSAGE);
			
		}
		else // if failure show error message
		{
			String title = "Remove Error";
			String message = "Warehouse could not be removed";
			showMessage(message, title, JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	protected void confirmButtonAction() 
	{
		String message = "Are you sure you want to remove this Warehouse?";
		String title = "Remove Warehouse";
		int value = JOptionPane.showConfirmDialog(manager.getFrame(), message, title, JOptionPane.YES_NO_OPTION);
		if (value == 0)
			removeWarehouse();
	}

	@Override
	public void setData(int id)
	{
		Data data = Database.getInstance().select(id);
		
		// program must fail if null
		if (data == null)
		{
			String message = "RemoveWarehousePanel - setData(int id) - null ptr error "
					+ "error reading warehouse: " + id;
			Utilities.getInstance().error(message);
		}
		
		// make sure it is a warehouse
		if (!(data instanceof Warehouse))
		{
			String message = "RemoveWarehousePanel - setData(int id) - invalid instance "
					+ "error reading warehouse: " + id;
			Utilities.getInstance().error(message);
		}
		
		Warehouse warehouse = (Warehouse) data;
		
		nameTextField.setText(warehouse.getName());
		capacityTextField.setText(warehouse.getCapacity() + "");
		dateTextField.setText(warehouse.getDate().toString());
		idTextField.setText(Integer.toHexString(warehouse.getId()));
		
		warehouseIdentity = id;
	}
}
