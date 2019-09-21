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

//10

public class AdminAddPage extends JPanel {
	private JTextField ISBNField;
	String ISBN;
	int acc_no;
	boolean validISBN;
	HomePage pswitch;

	/**
	 * Create the panel.
	 */
	public AdminAddPage(HomePage ob) {
		pswitch=ob;
		setLayout(null);
		setSize(594,391);
		
		JLabel lblEnterIsbnNumber = new JLabel("Enter ISBN Number:");
		lblEnterIsbnNumber.setFont(new Font("Georgia", Font.PLAIN, 18));
		lblEnterIsbnNumber.setBounds(63, 85, 207, 20);
		add(lblEnterIsbnNumber);
		
		ISBNField = new JTextField();
		ISBNField.setBounds(146, 131, 260, 20);
		add(ISBNField);
		ISBNField.setColumns(10);
		
		JLabel lblForExistingBooks = new JLabel("For existing books, a new copy will be added.");
		lblForExistingBooks.setBounds(126, 257, 338, 14);
		add(lblForExistingBooks);
		
		JLabel ErrorLabel = new JLabel("");
		ErrorLabel.setBounds(119, 303, 243, 14);
		add(ErrorLabel);

		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ISBN=ISBNField.getText();
				validISBN=checkISBN(ISBN);
				if(validISBN==false)
				{
					ErrorLabel.setText("Not a valid ISBN. Try again.");
					ISBNField.setText("");
				}
				else
				{					
					try {
						int j=0;
						int copies;
						Class.forName("oracle.jdbc.driver.OracleDriver"); 
			            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","root"); 
			            
			            PreparedStatement stmt = con.prepareStatement("select ISBN,no_of_copies from book where ISBN=?");
			            stmt.setString(1, ISBN);
			            
			            ResultSet rs=stmt.executeQuery();
			            acc_no=GenerateAccNo();
			            if(rs.next())//Increase no. of copies if isbn is already present
			            {
			            	System.out.println("Here 3");
			            	copies=rs.getInt(2);
			            	copies=copies+1;
			            	stmt=con.prepareStatement("update book set no_of_copies=? where ISBN=?");
			            	stmt.setInt(1, copies);
			            	stmt.setString(2, ISBN);
			            	j=stmt.executeUpdate();
			            	
			            	stmt = con.prepareStatement("insert into book_id values(?,?,'T')");
				            stmt.setInt(1, acc_no);
				            stmt.setString(2, ISBN);
				            j=stmt.executeUpdate();
				            
			            	pswitch.ChangePanels("12");		
			            	
			            }
			            else//new isbn
			            {
			            	stmt = con.prepareStatement("insert into book_id values(?,?,'T')");
				            stmt.setInt(1, acc_no);
				            stmt.setString(2, ISBN);
				            j=stmt.executeUpdate();
			            	pswitch.ChangePanels("11");
			            	pswitch.AdminGetISBN(ISBN);			            	
			            }
			            
			            ISBNField.setText("");
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
		btnAdd.setBounds(212, 173, 89, 23);
		add(btnAdd);
		
		JButton btnHome = new JButton("Home");
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pswitch.ChangePanels("1");
			}
		});
		btnHome.setBounds(456, 314, 89, 23);
		add(btnHome);
		
		
	}
	
	public boolean checkISBN(String a)
	{
		if(a.length()!=13)
			return false;
		for(int i=0;i<13;i++)
		{
			if(!Character.isDigit(a.charAt(i)))
					return false;
		}
		return true;
	}

	public int GenerateAccNo()
	{
		int x=0;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); 
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","root"); 
            
            PreparedStatement stmt = con.prepareStatement("select max(acc_no) from book_id");
            
            ResultSet rs=stmt.executeQuery();
            if(rs.next())
            {            
            x=rs.getInt(1);
            x=x+1;
            }
            else
            {
            	System.out.println("Nothing in book_id table");
            }
            
            con.close();
            
		}
		catch(Exception exc)
		{
			System.out.println("Exception");
			exc.printStackTrace();
		}
		return x;
		
	}
	

}
