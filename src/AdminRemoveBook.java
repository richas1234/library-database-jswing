import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import java.awt.Font;

//13

public class AdminRemoveBook extends JPanel {
	private JTextField AccField;
	HomePage pswitch;

	/**
	 * Create the panel.
	 */
	int acc_no;
	String ISBN;
	public AdminRemoveBook(HomePage ob) {
		pswitch=ob;
		
		setLayout(null);
		setSize(594,391);
		
		JLabel lblEnterAccno = new JLabel("Enter acc_no");
		lblEnterAccno.setFont(new Font("Georgia", Font.PLAIN, 18));
		lblEnterAccno.setBounds(95, 87, 127, 24);
		add(lblEnterAccno);
		
		AccField = new JTextField();
		AccField.setBounds(142, 138, 272, 20);
		add(AccField);
		AccField.setColumns(10);
		

		JLabel ErrorLabel = new JLabel("");
		ErrorLabel.setBounds(64, 246, 299, 31);
		add(ErrorLabel);
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String s=AccField.getText();
				int j=0;
				boolean a=checkNo(s);
				if(a==false)
				{
					ErrorLabel.setText("Not a valid acc_no. Try again.");
					AccField.setText("");
				}
				else
				{
					int noc;//no. of copies
					acc_no=Integer.parseInt(s);
					try {
						Class.forName("oracle.jdbc.driver.OracleDriver"); 
			            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","root"); 
			            
			            PreparedStatement stmt = con.prepareStatement("select acc_no,ISBN,avail from book_id where acc_no=?");
			            PreparedStatement stmt1;
			            
			            
			            
			            stmt.setInt(1, acc_no);
			            ResultSet rs=stmt.executeQuery();
			            ResultSet rs1;
			            if(rs.next())
			            {
			            	ISBN=rs.getString(2);
			            	char avail=rs.getString(3).charAt(0);
			            	if(avail=='F')//book is currently being borrowed and cannot be removed
			            	{
			            		ErrorLabel.setText("The required is currently being borrowed, cannot be removed.");
			            		AccField.setText("");
			            	}
			            	else
			            	{
			            		stmt1=con.prepareStatement("delete from book_id where acc_no=?");
			            		stmt1.setInt(1,acc_no);
			            		j=stmt1.executeUpdate();
			            		stmt1=con.prepareStatement("select no_of_copies from book where ISBN=?");
			            		stmt1.setString(1, ISBN);
			            		rs1=stmt1.executeQuery();
			            		if(rs1.next())
			            		{
			            			noc=rs1.getInt(1);
			            			if(noc==1)//after deletion number of copies would become zero, so remove the tuple from book
			            			{
			            				stmt1=con.prepareStatement("delete from book where ISBN=?");
			            				stmt1.setString(1,ISBN);
			            				j=stmt1.executeUpdate();			            			
			            			}
			            			else
			            			{
			            				noc=noc-1;
			            				stmt1=con.prepareStatement("update book set no_of_copies=? where ISBN=?");
			            				stmt1.setInt(1, noc);
			            				stmt1.setString(2,ISBN);
			            				j=stmt1.executeUpdate();			            			
			            			}
			            		}
			            		pswitch.ChangePanels("14");
			            	}
			            	
							
			            }
			            else
			            {
			            	ErrorLabel.setText("The given acc_no does not exist. Try again.");
			            	AccField.setText("");
			            }
			            AccField.setText("");
			            con.close();
					}
					catch(Exception exc)
					{
						System.out.println("Exception");
						exc.printStackTrace();
					}
					
				}
			}
		});
		btnRemove.setBounds(214, 198, 89, 23);
		add(btnRemove);
		
		JButton btnHome = new JButton("Home");
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pswitch.ChangePanels("1");
			}
		});
		btnHome.setBounds(447, 289, 89, 23);
		add(btnHome);
		

	}
	public boolean checkNo(String a)
	{
		if(a.length()!=5)
			return false;
		for(int i=0;i<5;i++)
		{
			if(!Character.isDigit(a.charAt(i)))
				return false;
		}
		return true;
	}
}
