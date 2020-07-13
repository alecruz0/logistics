package view;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import controller.Logistics;
import utilities.ComponentFactory;

/**
 * Abstract class to represent a panel managed by the logistics class.
 * @author Manuel Cruz
 * @version 1.0
 */
public abstract class Panel extends JPanel 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** Title of the panel */
	protected JLabel titleLabel;
	
	/** Layout of the panel */
	protected SpringLayout layout;
	
	/** Manager that manages this panel */
	protected Logistics manager;
	
	/**
	 * Explicit constructor for a panel.
	 * @param manager - Manager that manages this panel
	 */
	protected Panel(Logistics manager)
	{
		this.manager = manager;
		setup();
	}
	
	/**
	 * Sets up the panel
	 */
	protected void setup()
	{
		createComponents();
		addComponents();
		layoutComponents();
		panelSettings();
	}

	/**
	 * Creates the components of this panel
	 */
	protected void createComponents()
	{
		createLabels();
	}
	
	/**
	 * Adds the components to this panel
	 */
	protected void addComponents()
	{
		add(titleLabel);
	}
	
	
	/**
	 * Lays out the components of this panel
	 */
	protected void layoutComponents()
	{
		layout = new SpringLayout();
		layoutLabels();
	}
	
	/**
	 * Sets the settings of this panel
	 */
	protected void panelSettings()
	{
		this.setBackground(Color.BLACK);
		this.setLayout(layout);
	}
	
	/**
	 * Creates the labels used in this panel
	 */
	protected void createLabels()
	{
		ComponentFactory factory = ComponentFactory.getInstance();
		
		titleLabel = factory.createLabel(ComponentFactory.TITLE, "Title");
	}
	
	/**
	 * Lays out the labels of this panel
	 */
	protected void layoutLabels()
	{
		// title label
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, titleLabel, 0,      SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH,   titleLabel, 50, SpringLayout.NORTH,   this);
	}
	
	/**
	 * Shows a message to the user.
	 * @param message - message to show
	 * @param title - title of the message
	 * @param type - type of message
	 */
	protected void showMessage(String message, String title, int type)
	{
		JOptionPane.showMessageDialog(manager.getFrame(), message, title, type);
	}
	
	/**
	 * Sets the data to this panel based on the id given. If an Invalid data 
	 * is given then the program will fail.
	 * @param id - id of the data to set to this panel
	 */
	public void setData(int id)
	{
		// for now does nothing and may do nothing for certain panels
	}
	
	/**
	 * Resets this panel when it is about to be shown to the frame
	 */
	public abstract void reset();
}
