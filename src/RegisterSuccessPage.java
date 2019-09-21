import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

//7

public class RegisterSuccessPage extends JPanel {
	
	HomePage pswitch;

	/**
	 * Create the panel.
	 */
	public RegisterSuccessPage(HomePage ob) {
		pswitch=ob;
		
		setSize(594,391);
		setLayout(null);
		
		JLabel SuccessLabel = new JLabel("You have been successfully");
		SuccessLabel.setFont(new Font("Georgia", Font.BOLD, 16));
		SuccessLabel.setBounds(72, 95, 262, 21);
		add(SuccessLabel);
		
		JLabel lblRegisteredAsA = new JLabel("registered as a library member.");
		lblRegisteredAsA.setFont(new Font("Georgia", Font.BOLD, 16));
		lblRegisteredAsA.setBounds(72, 127, 281, 21);
		add(lblRegisteredAsA);
		
		JLabel lblYouMayNow = new JLabel("You may now log in from the Homepage.");
		lblYouMayNow.setFont(new Font("Georgia", Font.BOLD, 16));
		lblYouMayNow.setBounds(72, 159, 332, 21);
		add(lblYouMayNow);
		
		JButton btnHome = new JButton("Home");
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pswitch.ChangePanels("1");
			}
		});
		btnHome.setBounds(438, 265, 89, 23);
		add(btnHome);

	}

}
