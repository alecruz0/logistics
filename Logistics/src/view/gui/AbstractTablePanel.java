package view.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collections;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableModel;

import controller.Logistics;
import model.Data;
import model.Database;
import utilities.ComponentFactory;
import utilities.Utilities;
import view.Panel;

/**
 * Abstract class that represents data toview on a table.
 * @author Manuel Cruz
 * @version 1.0
 */
public abstract class AbstractTablePanel extends Panel implements ActionListener, MouseListener 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** Type of data to show */
	protected int type;
	
	/** Sort label in panel */
	protected JLabel sortLabel;
	
	/** Table to show data */
	protected JTable table;
	
	/** Panel to scroll through table */
	protected JScrollPane tablePanel;
	
	/** Combo box of sorting */
	protected JComboBox<Object> sortComboBox;
	
	/** Buttons for actions to data */
	protected JButton addButton, editButton, removeButton;
	
	/**
	 * Explicit constructor for a table panel.
	 * @param manager - manager that manages this panel
	 * @param type - type of data to show in table
	 */
	protected AbstractTablePanel(Logistics manager, int type)
	{
		super(manager);
		setType(type);
	}
	
	@Override
	protected void createComponents()
	{
		super.createComponents();
		createTable();
		createBoxes();
		createButtons();
	}
	
	@Override
	protected void addComponents()
	{
		super.addComponents();
		add(sortLabel);
		
		add(tablePanel);
		
		add(sortComboBox);
		
		add(addButton);
		add(editButton);
		add(removeButton);
	}
	
	@Override
	protected void layoutComponents()
	{
		super.layoutComponents();
		layoutTable();
		layoutButtons();
		layoutBoxes();
	}
	
	@Override
	protected void createLabels()
	{
		super.createLabels();
		ComponentFactory factory = ComponentFactory.getInstance();
		
		// sort label
		sortLabel = factory.createLabel(ComponentFactory.DEFAULT, "Sort By:");
	}
	
	/**
	 * Creates a table to show data.
	 */
	protected void createTable()
	{
		ComponentFactory factory = ComponentFactory.getInstance();
		
		table = factory.createTable();
		table.addMouseListener(this);
		tablePanel = factory.createScrollPane();
		tablePanel.setViewportView(table);
	}
	
	/**
	 * Creates the buttons to interact with the table
	 */
	protected void createButtons()
	{
		ComponentFactory factory = ComponentFactory.getInstance();
		
		// add button
		addButton = factory.createButton("ADD");
		addButton.addActionListener(this);
		
		// edit button
		editButton = factory.createButton("EDIT");
		editButton.addActionListener(this);
		
		// remove button
		removeButton = factory.createButton("REMOVE");
		removeButton.addActionListener(this);
	}
	
	/**
	 * Creates boxes in the panel.
	 */
	protected void createBoxes()
	{
		ComponentFactory factory = ComponentFactory.getInstance();
		
		// sort combo box
		sortComboBox = factory.createComboBox();
		sortComboBox.setFont(new Font("Arial", Font.BOLD, 12));
		sortComboBox.addActionListener(this);
		
		// set the sort combo box items
		String[] headerDetails = getHeaderDetails();
		ComboBoxModel<Object> model = new DefaultComboBoxModel<Object>(headerDetails);
		sortComboBox.setModel(model);
	}
	
	@Override
	protected void layoutLabels()
	{
		super.layoutLabels();
		
		// sort label
		layout.putConstraint(SpringLayout.EAST, sortLabel, 0, SpringLayout.WEST, sortComboBox);
		layout.putConstraint(SpringLayout.SOUTH, sortLabel, 0, SpringLayout.NORTH, tablePanel);
	}
	
	/**
	 * Layouts the table panel.
	 */
	protected void layoutTable()
	{
		// scroll table panel
		layout.putConstraint(SpringLayout.NORTH, tablePanel, 50, SpringLayout.SOUTH, titleLabel);
		layout.putConstraint(SpringLayout.WEST, tablePanel, 50, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, tablePanel, -50, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, tablePanel, -50, SpringLayout.NORTH, addButton);
	}
	
	/**
	 * Lays out the buttons
	 */
	protected void layoutButtons()
	{
		// add button
		layout.putConstraint(SpringLayout.WEST, addButton, 0, SpringLayout.WEST, tablePanel);
		layout.putConstraint(SpringLayout.SOUTH, addButton, -50, SpringLayout.SOUTH, this);
		
		// remove button
		layout.putConstraint(SpringLayout.EAST, removeButton, 0, SpringLayout.EAST, tablePanel);
		layout.putConstraint(SpringLayout.SOUTH, removeButton, -50, SpringLayout.SOUTH, this);
		
		// edit button
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, editButton, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.SOUTH, editButton, -50, SpringLayout.SOUTH, this);
	}
	
	/**
	 * Lays out the boxes in this panel
	 */
	protected void layoutBoxes()
	{
		// sort combo box
		layout.putConstraint(SpringLayout.EAST, sortComboBox, 0, SpringLayout.EAST, tablePanel);
		layout.putConstraint(SpringLayout.SOUTH, sortComboBox, 0, SpringLayout.NORTH, tablePanel);
	}
	
	/**
	 * Sets the type of data to display
	 * @param type - type of data
	 */
	protected void setType(int type)
	{
		this.type = type;
	}
	
	/**
	 * Updates the content of data in the table.
	 */
	protected void updateTable()
	{
		// clear selection
		ListSelectionModel selectionModel = table.getSelectionModel();
		selectionModel.clearSelection();
		
		// generate data and get header
		Object[][] data = generateData();
		String[] headerDetails = getHeaderDetails();
		
		// set model with data and header
		DefaultTableModel newModel = new DefaultTableModel(data, headerDetails);
		table.setModel(newModel);
	}
	
	/**
	 * Generates the data for the data
	 * @return 2d array with the data.
	 */
	protected Object[][] generateData()
	{
		List<Data> data = Database.getInstance().selectAll(type);
		
		if (data == null)
		{
			String message = "AbstractTablePanel - generateData() - Null data list";
			Utilities.getInstance().error(message);
		}
		
		// sort data
		Collections.sort(data);
		
		// create 2d array of table
		int numberOfRows = data.size();
		int headerSize = getHeaderDetails().length;
		Object[][] dataRows = new Object[numberOfRows][headerSize];
		
		// add data to array
		for (int i = 0; i < numberOfRows; i++)
			dataRows[i] = data.get(i).rowValues();
		
		return dataRows;
	}

	/**
	 * Action taken by the buttons
	 * @param button - button pressed
	 */
	protected void buttonsActions(JButton button)
	{
		if (button.getText().equals("ADD"))
			addButtonAction();
		else if (button.getText().equals("EDIT"))
			editButtonAction();
		else if (button.getText().equals("REMOVE"))
			removeButtonAction();
	}

	/**
	 * Gets the data header to be displayed.
	 * @return array containing the header
	 */
	protected abstract String[] getHeaderDetails();

	/**
	 * Action taken when change in sorting combo box.
	 */
	protected abstract void comboBoxAction();
	
	/**
	 * Action taken when the add button is pressed
	 */
	protected abstract void addButtonAction();
	
	/**
	 * Action taken when the edit button is pressed
	 */
	protected abstract void editButtonAction();
	
	/**
	 * Action taken when the remove button is pressed
	 */
	protected abstract void removeButtonAction();
	
	@Override
	public void actionPerformed(ActionEvent event)
	{
		Object object = event.getSource();
		if (object instanceof JComboBox)
			comboBoxAction();
		else if (object instanceof JButton)
		{
			JButton button = (JButton) object;
			buttonsActions(button);
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent event)
	{
		if (event.getSource() instanceof JTable)
		{
			editButton.setEnabled(true);
			removeButton.setEnabled(true);
		}
	}
	
	@Override
	public void reset()
	{
		updateTable();
		editButton.setEnabled(false);
		removeButton.setEnabled(false);
	}
	
	@Override public void mousePressed(MouseEvent e) {}
	@Override public void mouseReleased(MouseEvent e) {}
	@Override public void mouseEntered(MouseEvent e) {}
	@Override public void mouseExited(MouseEvent e) {}
}
