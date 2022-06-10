package cma_package;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

/**
 * Student Name: Kaiyan Chen, Simul Bista, Jaydenn(Ching-Ting) Chang
 * Student ID: N01489178, N01489966, N01511476
 * Section: ITC-5201-RIA
 */

/*************************************************************************************************
 *  ITC-5201-RIA – Assignment 2                                                                                                                                *

 *  I declare that this assignment is my own work in accordance with Humber Academic Policy. *

 *  No part of this assignment has been copied manually or electronically from any other source *

 *  (including web sites) or distributed to other students/social media.                                                       *

 *  Name: Kaiyan Chen Student ID: N01489178 Date: 6/10/2022 *
 *  Name: Simul Bista Student ID: N01489966 Date: 6/10/2022 *
 *  Name: Jaydenn(Ching-Ting) Chang Student ID: N01511476 Date: 6/10/2022 *

 * *************************************************************************************************/


public class CustomerMgntApp extends JFrame {

    // Jaydenn
    // Frame setting
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 400;

    // Jaydenn
    // user input variables
    private String cusID;
    private String cusName;
    private String cusPhone;
    private String cusEmail;
    private String cusPosCode;
    private String cusSearchID;


    // Jaydenn
    // Search GUI elements
    // search part (upper panel)
    private JLabel searchTitleLabel;
    private JLabel searchResultTitleLabel;
    private JLabel searchInputIdLabel;
    private JTextField searchInputIdTextField;
    JLabel searchMsgLabel;
    private JButton searchButton;
    private JButton displayAllButton;
    // search result part (lower panel)
    private DefaultTableModel tableModel;
    private JTable searchResultTable;


    // Jaydenn
    // Search Base Panel
    JPanel searchPanel;

    // Search Input Panel (upper panel)
    JPanel searchInputPanel;

    // Search Result Panel (lower panel)
    JPanel searchResultPanel;



    // Jaydenn
    // to store String[] returned from Search Function
    String[] searchResult;
    // to store ArrayList<String[]> returned from DisplayAll Function
    ArrayList<String[]> customerList;


    // Kaiyan
    // Add.Update GUI elements
    private JLabel titleLabel;
    
    private JLabel idLabel;
    private JLabel nameLabel;
    private JLabel phoneLabel;
    private JLabel emailLabel;
    private JLabel postcodeLabel;
    
    private JTextField idField;
    private JTextField nameField;
    private JTextField phoneField;
    private JTextField emailField;
    private JTextField postcodeField;
    
    private JLabel errorLabel;
    
    private JButton addButton;
    private JButton updateButton;
    
    private JPanel mainPanel;
    private JPanel inputPanel;
    private JPanel inputErrorPanel;
    private JPanel buttonPanel;
    
    private ArrayList<String> error;
    


    // Jaydenn
    // constructor
    public CustomerMgntApp() {

        // setup the frame
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setTitle("Customer Management App");

        // put together the Base Panels to the frame
        // set frame to GridLayout
        setLayout(new GridLayout(1, 2));
        add(createAddUpdatePanel());
        add(createSearchPanel());
    }


    // Kaiyan
    // Create and Add Base Panel
    public JPanel createAddUpdatePanel() {
    	//create labels, textfield & panels
    	titleLabel = new JLabel("<html><br>Add/Update Customer<br><br></html>",JLabel.CENTER);
    	titleLabel.setFont(new Font("",Font.BOLD,14));
        
        idLabel = new JLabel("  Customer ID");
        nameLabel = new JLabel("  Name");
        phoneLabel = new JLabel("  Phone");
        emailLabel = new JLabel("  Email");
        postcodeLabel = new JLabel("  Postal Code");
        
        idField = new JTextField();
        nameField = new JTextField();
        phoneField = new JTextField();
        emailField = new JTextField();
        postcodeField = new JTextField();
        
        errorLabel = new JLabel("",JLabel.CENTER);
        errorLabel.setForeground(Color.RED);
        
        addButton = new JButton("Add New");
        updateButton = new JButton("Update");
        
        mainPanel = new JPanel();
        inputPanel = new JPanel();
        inputErrorPanel = new JPanel();
        buttonPanel = new JPanel();
        
        //set mainPanel to borderlayout, with title, inputErrorPanel, buttonPanel
        mainPanel.setLayout(new BorderLayout());
        
        //set inputPanel to gridlayout, with input data
        inputPanel.setLayout(new GridLayout(5,2));
        
        //set inputErrorPanel to borderlayout, with inputPanel, errorLabel
        inputErrorPanel.setLayout(new BorderLayout());
        
        //set buttonPanel to gridlayout with 2 buttons
        buttonPanel.setLayout(new GridLayout(1,2));
        
        //add labels and textfield to inputPanel
        inputPanel.add(idLabel);
        inputPanel.add(idField);
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(phoneLabel);
        inputPanel.add(phoneField);
        inputPanel.add(emailLabel);
        inputPanel.add(emailField);
        inputPanel.add(postcodeLabel);
        inputPanel.add(postcodeField);
        
        //add buttons to buttonPanel
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        
        //add inputPanel to center, errorLabel to south
        inputErrorPanel.add(inputPanel,BorderLayout.CENTER);
        inputErrorPanel.add(errorLabel,BorderLayout.SOUTH);
        
        //add titleLabel to north, inputErrorPanel to center, buttonPanel to south
        mainPanel.add(titleLabel,BorderLayout.NORTH);
        mainPanel.add(inputErrorPanel,BorderLayout.CENTER);
        mainPanel.add(buttonPanel,BorderLayout.SOUTH);
        
//        error = new ArrayList<String>(); 
        
        //create ActionListener for addButton
        ActionListener a1 = new ActionListener()

		{
			public void actionPerformed(ActionEvent ae) {
				inputDataCollector();
				boolean result = validation(cusID, cusName, cusPhone, cusEmail, cusPosCode);
				if(result) {
					
					if (WriteAndReadFiles.addNewCus(cusID, cusName, cusPhone, cusEmail, cusPosCode)==true) {
						errorLabel.setText("Customer already exists! Please use Update");
					}				
					else				
						errorLabel.setText("Success!");
					
				}
					
					
			}		};
		
		//create ActionListener for updateButton
		ActionListener a2 = new ActionListener()

		{
			public void actionPerformed(ActionEvent ae) {
				inputDataCollector();
				boolean result = validation(cusID, cusName, cusPhone, cusEmail, cusPosCode);				
				if(result) {
					
					if (WriteAndReadFiles.updateCus(cusID, cusName, cusPhone, cusEmail, cusPosCode)==false) {
						errorLabel.setText("Either customer or the file doesn't exist. Please try add!");
					}				
					else				
						errorLabel.setText("Success!");
					
				}

			}
		};
		
		//add the listener to buttons
		addButton.addActionListener(a1);
		updateButton.addActionListener(a2);

        return mainPanel;
    }
    
    //pass the input data to the variables
    public void inputDataCollector() {
    	 cusID = idField.getText();
    	 cusName = nameField.getText();
    	 cusPhone = phoneField.getText();
    	 cusEmail = emailField.getText();
    	 cusPosCode = postcodeField.getText();
    }
    
    //name validation
    public boolean isNameValid(String cusName) {
    	String regexName = "\\p{Upper}(\\p{Lower}+\\s?)";
        String patternName = "(" + regexName + "){2,3}";
        return cusName.matches(patternName);
    }
    
    //phone validation
    public boolean isPhoneValid(String cusPhone) {
        return cusPhone.matches("^\\d{10}$");
    }
    
    //email validation
    public boolean isEmailValid(String cusEmail) {
    	String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(cusEmail);
        return matcher.matches();
    }
    
    //postcode validation
    public boolean isPostcodeValid(String cusPosCode) {
    	String regex = "^(?!.*[DFIOQU])[A-VXY][0-9][A-Z] [0-9][A-Z][0-9]$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(cusPosCode);
        return matcher.matches();
    }
    
    //validation method
    public boolean validation(String cusID, String cusName, String cusPhone, String cusEmail, String cusPosCode) {
    	error = new ArrayList<String>();
    	boolean validated=true;
		String errorString="";
		
		//validation
		if(!isCusIdValid(cusID))
			error.add("Please enter a 5-digit-number ID");
		if(!isNameValid(cusName))
			error.add("Please enter a full name with capitalizing");
		if(!isPhoneValid(cusPhone))
			error.add("Please enter a 10-digit phone number");
		if(!isEmailValid(cusEmail))
			error.add("Please enter a vaild Email");
		if(!isPostcodeValid(cusPosCode))
			error.add("Please enter a vaild Postcode (e.g M4W 0B1)");	
		
		//convert arraylist to string and set errorLabel
		for (String s : error)
		{
		    errorString += s + "<br/>";
		}
		errorLabel.setText("<html>"+errorString+"</html>");
			if(!errorString.isEmpty()) {
				validated = false;
			}

		return validated;
    }

    // Jaydenn
    // Search Base Panel
    public JPanel createSearchPanel() {

//        // set layout to GridLayout
//        searchPanel = new JPanel();
//        searchPanel.setLayout(new GridLayout(2, 1));
//
//        // put together search panels to Search Base Panel
//        searchPanel.add(createSearchInputPanel());
//        searchPanel.add(createSearchResultPanel());

        // set layout to BorderLayout
        searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout());

        // put together search panels to Search Base Panel
        searchPanel.add(createSearchInputPanel(), BorderLayout.NORTH);
        searchPanel.add(createSearchResultPanel(), BorderLayout.CENTER);

        return searchPanel;
    }

    // Jaydenn
    // create the panel that let user input the Id and hit search
    // create the upper panel of the right part of the frame
    public JPanel createSearchInputPanel() {

        // create panel
        searchInputPanel = new JPanel();
        searchInputPanel.setLayout(new GridLayout(3, 1));

        JPanel searchInputSubPanel = new JPanel();

        // create elements
        searchTitleLabel = new JLabel("Search by Customer ID", SwingConstants.CENTER);
        searchTitleLabel.setFont(new Font("",Font.BOLD,14));
        
        searchInputIdLabel = new JLabel("Customer ID: ");
        searchInputIdTextField = new JTextField(10);
        searchMsgLabel = new JLabel("(hint) ID example: 1, 25, 99999", SwingConstants.CENTER);
        searchMsgLabel.setForeground(Color.RED);
        
        searchButton = new JButton("Search");
        displayAllButton = new JButton("Display All");

        // create actionListener for Search Button
        class searchButtonListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                // reset the table data to none
                tableModel.setRowCount(0);

                // save user input to variable
                searchInputDataCollector();

                // validate if ID is valid
                if (isCusIdValid(cusSearchID))
                {
                    // test use
//                    System.out.println("Valid");
                    // clear search message
                    searchMsgLabel.setText("");

                    // send customer ID to search function
                    searchResult = WriteAndReadFiles.searchCusId(cusSearchID);

                    // print the result to the search result table
                    printOneResult(searchResult);
                }
                else
                {
                    // test use
//                    System.out.println("Invalid");
                    // show warning to user
                    searchMsgLabel.setText("Input not valid! Follow format: 1, 25, 99999");
                }
            }
        }

        // create actionListener for DisplayAll Button
        class displayAllButtonListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                // reset the table data to none
                tableModel.setRowCount(0);

                // request all customer information
                customerList = WriteAndReadFiles.displayAll();

                // print the result to the search result table
                printAllResult(customerList);
            }
        }

        // attach the actionListener to Search Button
        ActionListener searchListener = new searchButtonListener();
        searchButton.addActionListener(searchListener);

        // attach the actionListener to DisplayAll Button
        ActionListener displayListener = new displayAllButtonListener();
        displayAllButton.addActionListener(displayListener);

        // add elements to the panel
        searchInputPanel.add(searchTitleLabel);
        searchInputSubPanel.add(searchInputIdLabel);
        searchInputSubPanel.add(searchInputIdTextField);
        searchInputSubPanel.add(searchButton);
        searchInputSubPanel.add(displayAllButton);
        searchInputPanel.add(searchInputSubPanel);
        searchInputPanel.add(searchMsgLabel);

        // return the aggregated panel
        return searchInputPanel;
    }

    // Jaydenn
    // collect search input from the form
    public void searchInputDataCollector() {
        cusSearchID = searchInputIdTextField.getText();
    }

    // Jaydenn
    // check if input matches pattern for customer ID
    public boolean isCusIdValid(String cusID) {
        return cusID.matches("\\d{1,5}") && !cusID.matches("^0+.*");
    }


    // Jaydenn
    // create the panel that shows the search result
    // create the lower panel of the right part of the frame
    public JPanel createSearchResultPanel() {

        // create panel
        searchResultPanel = new JPanel();
        searchResultPanel.setLayout(new BorderLayout());

        // create title
        searchResultTitleLabel = new JLabel("Search Result", SwingConstants.CENTER);

        // create table
        String[] column = new String[]{"ID", "Name", "Phone", "Email", "Postal Code"};
        tableModel = new DefaultTableModel(column, 0);
        searchResultTable = new JTable(tableModel);



        // test use
//        String[] item = {"1", "2", "3", "4", "5"};
//        tableModel.addRow(item);


        // set table to be read-only
        searchResultTable.setEnabled(false);

        // give email column a starting width
        searchResultTable.getColumn("Email").setPreferredWidth(90);

        // set columns expandable (when expanding, horizontal scrollbar will show up)
        searchResultTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // create a JScrollPane for table
        JScrollPane searchScrollPane = new JScrollPane(searchResultTable);

        // add elements to the panel
        searchResultPanel.add(searchResultTitleLabel, BorderLayout.NORTH);
        searchResultPanel.add(searchScrollPane, BorderLayout.CENTER);

        // return the aggregated panel
        return searchResultPanel;
    }


    // Jaydenn
    // add found customer data to the search result table
    public void printOneResult(String[] searchResult) {
        // test if search result is null
        if (searchResult == null) {
            searchMsgLabel.setText("The customer doesn't exist!");
        } else {
                tableModel.addRow(searchResult);
        }
    }



    // Jaydenn
    // add all customer data to the search result table
    public void printAllResult(ArrayList<String[]> allCustomerList) {
        // test if customer list is null
        if (allCustomerList == null) {
            searchMsgLabel.setText("The list is empty!");
        } else {
            // go over the String List in search result ArrayList to add data to table
            for (String[] customer : allCustomerList) {
                tableModel.addRow(customer);
            }
        }
    }
    // examples on how the argument should look like
    //    searchResultList = new ArrayList<String[]>();
    //    searchResultList.add(new String[]{"1", "2", "3", "4", "5"});
    //    searchResultList.add(new String[]{"21", "22", "23", "24", "25"});

}
