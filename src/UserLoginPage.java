import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;



//5

public class UserLoginPage extends JPanel {
	
    private JTextField username;
	
	UserMainPage ob;
	HomePage pswitch;
	private JPasswordField passwordField;

	/**
	 * Create the panel.
	 */
	public UserLoginPage(HomePage ob) {
		
		pswitch=ob;
		
		setSize(594,391);
		setLayout(null);
		
		JLabel lblUserLogin = new JLabel("User Login");
		lblUserLogin.setFont(new Font("Georgia", Font.BOLD, 18));
		lblUserLogin.setBounds(218, 44, 111, 34);
		add(lblUserLogin);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(105, 128, 79, 20);
		add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(105, 174, 79, 20);
		add(lblPassword);
		
		username = new JTextField();
		username.setBounds(218, 128, 142, 20);
		add(username);
		username.setColumns(10);
		
		JLabel lblDobddmmyyyy = new JLabel("DOB (DD-MM-YYYY)");
		lblDobddmmyyyy.setBounds(218, 205, 111, 14);
		add(lblDobddmmyyyy);
		
		JLabel NoUserLabel = new JLabel("");
		NoUserLabel.setBounds(142, 238, 242, 26);
		add(NoUserLabel);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String uname=username.getText();
				String psword=passwordField.getText();
				String mes;
				
				
				try {
					Class.forName("oracle.jdbc.driver.OracleDriver"); 
		            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","root"); 
		            
		            PreparedStatement stmt = con.prepareStatement("select member_id,DOB,name from member where member_id=? and to_char(DOB,'DD-MM-YYYY')=?");
		            stmt.setString(1, uname);
		            stmt.setString(2, psword);
		            ResultSet rs=stmt.executeQuery();
		            if(rs.next())
		            {
		            	pswitch.ChangePanels("6");
		            	pswitch.UserBorrowReturn(rs.getString(1),rs.getString(3));
		            	
						
		            }
		            else
		            {
		            	NoUserLabel.setText("Username or password is incorrect");
		            	username.setText("");
						passwordField.setText("");
		            }
		            con.close();
				}
				catch(Exception exc)
				{
					System.out.println("Exception");
					exc.printStackTrace();
				}
			
		}});
		btnLogin.setBounds(172, 275, 89, 23);
		add(btnLogin);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(218, 174, 142, 20);
		add(passwordField);
		
		JButton btnHome = new JButton("Home");
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				username.setText("");
				passwordField.setText("");
				pswitch.ChangePanels("1");
			}
		});
		btnHome.setBounds(441, 292, 89, 23);
		add(btnHome);

	}

}
