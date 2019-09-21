import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

//12

public class AdminAddSuccessPage extends JPanel {
	
	HomePage pswitch;

	/**
	 * Create the panel.
	 */
	public AdminAddSuccessPage(HomePage ob) {
		pswitch=ob;
		setLayout(null);
		setSize(594,391);
		
		JLabel lblBookSuccessfullyAdded = new JLabel("Book Successfully Added");
		lblBookSuccessfullyAdded.setFont(new Font("Georgia", Font.PLAIN, 18));
		lblBookSuccessfullyAdded.setBounds(110, 100, 240, 49);
		add(lblBookSuccessfullyAdded);
		
		JButton btnHome = new JButton("Home");
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pswitch.ChangePanels("1");
			}
		});
		btnHome.setBounds(411, 263, 89, 23);
		add(btnHome);
	}

}
