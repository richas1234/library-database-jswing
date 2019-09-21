import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

//2

public class LoginPage extends JPanel {
	HomePage pswitch;

	/**
	 * Create the panel.
	 */
	CardLayout cl=new CardLayout();
	public LoginPage(HomePage ob) {
		pswitch=ob;
		setSize(594, 391);
		setLayout(null);
		
		JLabel lbllogin = new JLabel("~Login~");
		lbllogin.setFont(new Font("Georgia", Font.BOLD, 18));
		lbllogin.setBounds(237, 43, 87, 21);
		add(lbllogin);
		
		JButton btnUserLogin = new JButton("User Login");
		btnUserLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pswitch.ChangePanels("5");
				
			}
		});
		btnUserLogin.setBounds(210, 120, 127, 29);
		add(btnUserLogin);
		
		JButton btnAdminLogin = new JButton("Admin Login");
		btnAdminLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pswitch.ChangePanels("8");
			}
		});
		btnAdminLogin.setBounds(210, 186, 127, 29);
		add(btnAdminLogin);
		
		JButton btnHome = new JButton("Home");
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pswitch.ChangePanels("1");
			}
		});
		btnHome.setBounds(414, 284, 89, 23);
		add(btnHome);

	}

}
