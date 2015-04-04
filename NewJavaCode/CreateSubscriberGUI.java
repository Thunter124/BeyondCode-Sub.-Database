import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jgoodies.forms.factories.DefaultComponentFactory;
import javax.swing.JCheckBox;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class CreateSubscriberGUI extends JFrame {

	private static final long serialVersionUID = 3805890009759996908L;
	
	private JTextField tfName;
	private JTextField tfStreetAddress;
	private JTextField tfCityState;
	private JTextField tfZipCode;
	private JTextField tfStartingCopies;
	private JTextField tfNumAddresses;
	
	private JButton btnApply;
	private JCheckBox cbSameAddress;
	
	private boolean validTextFields = false;
	private Subscriber sub = null;
	Main main;
	private CreateSubscriberGUI _this;
	
	public CreateSubscriberGUI(Main main) {
		initWindow();
		initButtons();
		initLabels();
		initTextFields();
		initMisc();
		this.main = main;
		_this = this;
	}

	private void initMisc(){
		//init check box for same address
				cbSameAddress = new JCheckBox("");
				cbSameAddress.addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent arg0) {
						if(cbSameAddress.isEnabled() && cbSameAddress.isSelected()){
							if(!(tfName.getText().equals("") 
									|| tfCityState.getText().equals("")
									|| tfZipCode.getText().equals("")
									|| tfStartingCopies.getText().equals("")
									|| tfStreetAddress.getText().equals("")
									|| tfNumAddresses.getText().equals(""))){
								btnApply.setEnabled(true);
							}
								
						}else{
							btnApply.setEnabled(false);
						}
					}
				});
				cbSameAddress.setEnabled(false);
				cbSameAddress.setBounds(26, 189, 97, 14);
				getContentPane().add(cbSameAddress);	
				

	}

	private void initWindow(){
	//basic window attributes	
		setTitle("Subscriber Billing Information");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		this.setSize(450, 300);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();	//set window in center of screen, regardless of screen resolution
		this.setLocation(dim.width/2 - this.getWidth()/2, dim.height/2 - this.getHeight()/2);
	}
	
	private void initButtons(){
		JButton btnNext = new JButton("Next");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!(tfName.getText().equals("") || tfStreetAddress.getText().equals("") || tfZipCode.getText().equals("") || 
						tfCityState.getText().equals("") || tfStartingCopies.equals("") || tfNumAddresses.equals(""))){
					
					int numAddresses = 0;
					validTextFields = false;
					try{
						numAddresses = Integer.parseInt(tfNumAddresses.getText());
						validTextFields = true;
					}catch(NumberFormatException e){
						JOptionPane.showMessageDialog(null, "The number of subscriber addresses must be a number, and greater"
								+ " than zero!", "Error!", JOptionPane.WARNING_MESSAGE);
						validTextFields = false;
					}
					int startingCopies = 0;
					try{
						startingCopies = Integer.parseInt(tfStartingCopies.getText());
					}catch(NumberFormatException e){
						JOptionPane.showMessageDialog(null, "The number of starting copies must be a number, and greater"
								+ " than or equal to zero!", "Error!", JOptionPane.WARNING_MESSAGE);
						validTextFields = false;
					}
					
					String billingName = tfName.getText();
					String streetAddress = tfStreetAddress.getText();
					String zipCode = tfZipCode.getText();
					String cityState = tfCityState.getText();
					
					if(!cbSameAddress.isEnabled() || !cbSameAddress.isSelected() || numAddresses != 1){
						
						if(validTextFields){
							Subscriber newSub = new Subscriber(billingName, streetAddress + " " + cityState + " " +  zipCode, startingCopies);
							CreateAddressGUI addressGUI = new CreateAddressGUI(1, numAddresses, newSub, _this);
							addressGUI.setVisible(true);
						
						}
					}	
				}else{
					JOptionPane.showMessageDialog(null, "No fields may be blank!", "Error!", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnNext.setBounds(117, 228, 89, 23);
		getContentPane().add(btnNext);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
			}
		});
		btnCancel.setBounds(216, 228, 89, 23);
		getContentPane().add(btnCancel);
		

		btnApply = new JButton("Apply");
		btnApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				main.passNewSubscriber(sub);
				dispose();
			}
		});
		btnApply.setEnabled(false);
		btnApply.setBounds(335, 228, 89, 23);
		getContentPane().add(btnApply);
	}
	
	private void initLabels(){

		JLabel lblName = DefaultComponentFactory.getInstance().createLabel(
				"Name:");
		lblName.setBounds(26, 28, 50, 14);
		getContentPane().add(lblName);

		JLabel lblStreetAddress = DefaultComponentFactory.getInstance()
				.createLabel("Street Address:");
		lblStreetAddress.setBounds(250, 28, 92, 14);
		getContentPane().add(lblStreetAddress);

		JLabel lblState = DefaultComponentFactory.getInstance().createLabel(
				"City, State:");
		lblState.setBounds(250, 73, 92, 14);
		getContentPane().add(lblState);

		JLabel lblZipCode = DefaultComponentFactory.getInstance().createLabel(
				"Zip Code:");
		lblZipCode.setBounds(250, 122, 92, 14);
		getContentPane().add(lblZipCode);

		JLabel lblStartingCopies = DefaultComponentFactory.getInstance()
				.createLabel("Starting Copies:");
		lblStartingCopies.setBounds(26, 73, 92, 14);
		getContentPane().add(lblStartingCopies);

		JLabel lblNumberOfAddresses = DefaultComponentFactory.getInstance()
				.createLabel("Number of Addresses:");
		lblNumberOfAddresses.setBounds(26, 122, 135, 14);
		getContentPane().add(lblNumberOfAddresses);
	
		JLabel lblSameShippingAddress = DefaultComponentFactory.getInstance().createLabel("Same Shipping Address?");
		lblSameShippingAddress.setBounds(26, 168, 153, 14);
		getContentPane().add(lblSameShippingAddress);
	}
	
	private void initTextFields(){
	//name text field	
		tfName = new JTextField();
		tfName.setBounds(26, 42, 135, 20);
		getContentPane().add(tfName);
		tfName.setColumns(10);
		
	//street address text field
		tfStreetAddress = new JTextField();
		tfStreetAddress.setBounds(250, 42, 153, 20);
		getContentPane().add(tfStreetAddress);
		tfStreetAddress.setColumns(10);
	
	//city, state text field
		tfCityState = new JTextField();
		tfCityState.setBounds(250, 87, 153, 20);
		getContentPane().add(tfCityState);
		tfCityState.setColumns(10);
		
	//zip code text field
		tfZipCode = new JTextField();
		tfZipCode.setBounds(250, 137, 153, 20);
		getContentPane().add(tfZipCode);
		tfZipCode.setColumns(10);

	//starting copies text field
		tfStartingCopies = new JTextField();
		tfStartingCopies.setBounds(26, 87, 40, 20);
		getContentPane().add(tfStartingCopies);
		tfStartingCopies.setColumns(10);
		
	//number of addresses text field	
		tfNumAddresses = new JTextField();
		tfNumAddresses.addKeyListener(new KeyAdapter() { //listener for text field
			@Override
			public void keyReleased(KeyEvent e) {
				int addresses;
				try{
					addresses = Integer.parseInt(tfNumAddresses.getText());  //parse the string to an int
					if(addresses == 1){
						cbSameAddress.setEnabled(true);		//if address == 1, "same address" check box is eneabled
					}else{
						cbSameAddress.setEnabled(false);	//else, not enabled
					}
				}catch(NumberFormatException a){
					cbSameAddress.setEnabled(false);		//if not an int, not enabled
				}
			}
		});
		tfNumAddresses.setBounds(26, 137, 40, 20);
		getContentPane().add(tfNumAddresses);
		tfNumAddresses.setColumns(10);
	}
	
	//called by the final CreateAddressGUI window when the subscriber's info is complete
	public void enableApplyButton(Subscriber sub){
		this.sub = sub;
		btnApply.setEnabled(true);
	}
}
