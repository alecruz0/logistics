package utilities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.JTableHeader;

/**
 * This class uses the factory design pattern. That way all of the components for panels are create here.
 * @author Manuel Cruz
 * @version 1.0
 *
 */
public class ComponentFactory 
{
	/** Constant that represents text for a title */
	public static final int TITLE = 0;
	
	/** Constant that represents text for a subtitle */
	public static final int SUBTITLE = 1;
	
	/** Constant that represents text for any other default text */
	public static final int DEFAULT = 2;
	
	/** Constant that represents text for a text field */
	public static final int TEXT = 3;
	
	/** Constant that represents text as password for a text field */
	public static final int PASSWORD = 4;
	
	/** Constant font for title text */
	private static final Font TITLE_FONT = new Font("Serif", Font.BOLD + Font.ITALIC, 50);
	
	/** Constant font for subtitle text */
	private static final Font SUBTITLE_FONT = new Font("Arial", Font.BOLD, 20);
	
	/** Constant font for default text */
	private static final Font DEFAULT_FONT = new Font("Arial", Font.BOLD, 18);
	
	/** Instance of component factory */
	private static ComponentFactory instance;
	
	/**
	 * Explicit constructor for component factory
	 */
	private ComponentFactory()
	{
		super();
	}
	
	/**
	 * Create a label to represent text.
	 * @param type - the type if label
	 * @param text - the text that this label will contain
	 * @return a label with the corresponded text and type
	 */
	public JLabel createLabel(int type, String text)
	{
		// check text
		text = Utilities.getInstance().validString(text);
		
		JLabel label = new JLabel(text);
		label.setBackground(Color.BLACK);
		label.setForeground(Color.WHITE);
		
		// sets the font
		if (type == TITLE)
			label.setFont(TITLE_FONT);
		else if (type == SUBTITLE)
			label.setFont(SUBTITLE_FONT);
		else if (type == DEFAULT)
			label.setFont(DEFAULT_FONT);
		else
		{
			String message = "ComponentFactory - createLabel"
					+ "(int type, String text) - Invalid type label";
			Utilities.getInstance().error(message);
		}
		
		return label;
	}
	
	/**
	 * Creates a textfield based on the given type.
	 * @param type - type  of textfield. Either text or password
	 * @return textfield based on type
	 */
	public JTextField createTextField(int type)
	{
		
		JTextField textfield = null;
		
		// sets type of textfield
		if (type == TEXT)
			textfield = new JTextField(12);
		else if (type == PASSWORD)
			textfield = new JPasswordField(12);
		else
		{
			String message = "ComponentFactory - createTextField"
					+ "(int type) - Invalid type textfield";
			Utilities.getInstance().error(message);
		}
		
		textfield.setFont(SUBTITLE_FONT);
		
		return textfield;
	}
	
	/**
	 * Creates a button with the given text.
	 * @param text - text the button will contain
	 * @return a button with the given text
	 */
	public JButton createButton(String text)
	{
		// checks text
		text = Utilities.getInstance().validString(text);
		
		JButton button = new JButton(text);
		button.setFont(SUBTITLE_FONT);
		button.setPreferredSize(new Dimension(125, 35));
		return button;
	}
	
	/**
	 * Creates a checkbox with the given text.
	 * @param text - text the box will contain
	 * @return a checkbox with the given text
	 */
	public JCheckBox createCheckBox(String text)
	{
		// checks text
		text = Utilities.getInstance().validString(text);
		
		JCheckBox checkbox = new JCheckBox(text);
		checkbox.setBackground(Color.BLACK);
		checkbox.setForeground(Color.WHITE);
		checkbox.setFont(SUBTITLE_FONT);
		return checkbox;
	}
	
	/**
	 * Creates an empty combo box.
	 * @return empty combo box
	 */
	public JComboBox<Object> createComboBox()
	{
		JComboBox<Object> comboBox = new JComboBox<Object>();
		comboBox.setFont(SUBTITLE_FONT);
		comboBox.setOpaque(true);
		comboBox.setBackground(Color.BLACK);
		comboBox.setForeground(Color.BLACK);
		return comboBox;
	}
	
	/**
	 * Creates an empty table with its header formated.
	 * @return empty table
	 */
	public JTable createTable()
	{
		// set table
		JTable table = new JTable();
		table.setFont(SUBTITLE_FONT);
		table.setRowHeight(40);
		table.setBackground(Color.DARK_GRAY);
		table.setForeground(Color.WHITE);
		table.setGridColor(Color.WHITE);
		table.setDefaultEditor(Object.class, null);
		
		// set header
		JTableHeader header = table.getTableHeader();
		header.setFont(DEFAULT_FONT);
		header.setPreferredSize(new Dimension(header.getPreferredSize().width, 50));
		
		return table;
	}
	
	/**
	 * Creates a scrollable panel
	 * @return scrollable panel
	 */
	public JScrollPane createScrollPane()
	{
		JScrollPane panel = new JScrollPane();
		return panel;
	}
	
	/**
	 * Creates a menu item for a menu tab.
	 * @param name - name of the menu item
	 * @return menu item
	 */
	public JMenuItem createMenuItem(String name)
	{
		// check text
		name = Utilities.getInstance().validString(name);
		
		JMenuItem menuItem = new JMenuItem(name);
		return menuItem;
	}
	
	/**
	 * Creates a menu tab for a menubar.
	 * @param name - name of the menu tab
	 * @return menu tab
	 */
	public JMenu createMenuTab(String name)
	{
		// check text
		name = Utilities.getInstance().validString(name);
		
		JMenu menu = new JMenu(name);
		return menu;
	}
	
	/**
	 * Singleton for single Component Factory
	 * @return component factory
	 */
	public static ComponentFactory getInstance()
	{
		if (instance == null)
			instance = new ComponentFactory();
		return instance;
	}
}
