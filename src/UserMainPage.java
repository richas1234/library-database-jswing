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

//6

public class UserMainPage extends JPanel {
	
	String mem_id;
	String mem_name;
	HomePage pswitch;
	JLabel MessageLabel;

	/**
	 * Create the panel.
	 */
	public UserMainPage(HomePage ob) {
		
		pswitch=ob;
		setSize(594, 391);
		setLayout(null);
		MessageLabel = new JLabel("");
		MessageLabel.setFont(new Font("Garamond", Font.BOLD, 18));
		MessageLabel.setBounds(59, 39, 219, 28);
		add(MessageLabel);
		
		JLabel ErrorLabel = new JLabel("");
		ErrorLabel.setBounds(59, 288, 377, 28);
		add(ErrorLabel);
		
		JButton btnBorrowABook = new JButton("Borrow A Book");
		btnBorrowABook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					int j=0;
					char s=mem_id.charAt(0);
					Class.forName("oracle.jdbc.driver.OracleDriver"); 
		            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","root"); 
		            
		            PreparedStatement stmt = con.prepareStatement("select count(*) from borrow_status where member_id=?");
		            stmt.setString(1, mem_id);
		            ResultSet rs=stmt.executeQuery();
		            
		            if(rs.next())
		            {
		            	int r=rs.getInt(1);
		            	if((s=='S'&&r==3)||(s=='T'&&r==5))
		            	{
		            		ErrorLabel.setText("You have reached your borrowing limit.");
		            		
		            	}
		            	else
		            	{
		            		pswitch.ChangePanels("15");
		    				pswitch.UserGetMemID(mem_id);
		            		
		            	}
		            }
		            else
		            {
		            	pswitch.ChangePanels("15");
						pswitch.UserGetMemID(mem_id);
		            }
		            con.close();
				}
				catch(Exception exc)
				{
					System.out.println("Exception");
					exc.printStackTrace();
				}
				
								
			}
		});
		btnBorrowABook.setBounds(222, 95, 136, 41);
		add(btnBorrowABook);
		
		JButton btnReturnABook = new JButton("Return A Book");
		btnReturnABook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pswitch.ChangePanels("17");
				pswitch.UserGetMemID1(mem_id);
				
			}
		});
		btnReturnABook.setBounds(222, 196, 136, 41);
		add(btnReturnABook);
		
		JButton btnHome = new JButton("Home");
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pswitch.ChangePanels("1");
			}
		});
		btnHome.setBounds(443, 255, 89, 23);
		add(btnHome);
		
		


	}
	public void getValues(String a,String b)
	{
		mem_id=a;
		mem_name=b;
		MessageLabel.setText("Hi, "+mem_name);
	}
}
