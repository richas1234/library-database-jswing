import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

//9

public class AdminMainPage extends JPanel {
	
	String emp_id;
	String emp_name;
	HomePage pswitch;
	JLabel MessageLabel;

	/**
	 * Create the panel.
	 */
	public AdminMainPage(HomePage ob) {
		pswitch=ob;
		setSize(594, 391);
		setLayout(null);
		MessageLabel = new JLabel("");
		MessageLabel.setFont(new Font("Garamond", Font.BOLD, 18));
		MessageLabel.setBounds(59, 39, 219, 28);
		add(MessageLabel);
		
		JButton btnAddABook = new JButton("Add A Book");
		btnAddABook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pswitch.ChangePanels("10");
								
			}
		});
		btnAddABook.setBounds(222, 95, 136, 41);
		add(btnAddABook);
		
		JButton btnRemoveABook = new JButton("Remove A Book");
		btnRemoveABook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pswitch.ChangePanels("13");
			}
		});
		btnRemoveABook.setBounds(222, 196, 136, 41);
		add(btnRemoveABook);
		
		JButton btnHome = new JButton("Home");
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pswitch.ChangePanels("1");
			}
		});
		btnHome.setBounds(431, 277, 89, 23);
		add(btnHome);

	}
	public void getValues(String a,String b)
	{
		emp_id=a;
		emp_name=b;
		MessageLabel.setText("Hi, "+emp_name);
	}

}
