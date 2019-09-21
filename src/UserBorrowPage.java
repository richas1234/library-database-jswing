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
//15

public class UserBorrowPage extends JPanel {

	HomePage pswitch;
	private JTextField AccField;
	String mem_id;
	int acc_no;
	/**
	 * Create the panel.
	 */
	public UserBorrowPage(HomePage ob) {
		pswitch=ob;
		setLayout(null);
		setSize(594,391);
		
		JLabel lblEnterAccno = new JLabel("Enter acc_no:");
		lblEnterAccno.setFont(new Font("Georgia", Font.BOLD, 18));
		lblEnterAccno.setBounds(75, 69, 163, 23);
		add(lblEnterAccno);
		
		AccField = new JTextField();
		AccField.setBounds(134, 114, 300, 20);
		add(AccField);
		AccField.setColumns(10);

		JLabel ErrorLabel = new JLabel("");
		ErrorLabel.setBounds(61, 179, 462, 40);
		add(ErrorLabel);
		
		JButton btnBorrow = new JButton("Borrow");
		btnBorrow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				boolean a=checkNo(AccField.getText());
				if(a==false)
				{
					ErrorLabel.setText("Not a valid acc_no. Try Again.");
					AccField.setText("");
				}
				else
				{
					acc_no=Integer.parseInt(AccField.getText());
					try {
						int j=0;
						Class.forName("oracle.jdbc.driver.OracleDriver"); 
			            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","root"); 
			            
			            PreparedStatement stmt = con.prepareStatement("select avail from book_id where acc_no=?");
			            stmt.setInt(1, acc_no);
			            ResultSet rs=stmt.executeQuery();
			            
			            if(rs.next())
			            {
			            	char avail=rs.getString(1).charAt(0);
			            	if(avail=='F')
			            	{
			            		ErrorLabel.setText("Sorry, the book is already borrowed. Try again.");
			            		AccField.setText("");
			            	}
			            	else//book is present and available
			            	{
			            		//insert into borrow, due_date=sysdate+14,update book_id tuple to 'F'
			            		PreparedStatement stmt1=con.prepareStatement("insert into borrow_status values(?,?,sysdate,sysdate+14)");
			            		stmt1.setString(1, mem_id);
			            		stmt1.setInt(2, acc_no);
			            		j=stmt1.executeUpdate();
			            		
			            		stmt1=con.prepareStatement("update book_id set avail='F' where acc_no=?");
			            		stmt1.setInt(1, acc_no);
			            		j=stmt1.executeUpdate(); 
			            		
			            		AccField.setText("");
			            		pswitch.ChangePanels("16");		            		
			            		
			            	}
			            	
							
			            }
			            else
			            {
			            	ErrorLabel.setText("Sorry, that acc_no does not exist. Try Again.");
			            	AccField.setText("");
			            }
			            
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
		btnBorrow.setBounds(222, 230, 89, 23);
		add(btnBorrow);
		
		JButton btnSearchForBook = new JButton("Search For Book");
		btnSearchForBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SearchResults ob3=new SearchResults();
				ob3.setVisible(true);
			}
		});
		btnSearchForBook.setBounds(286, 287, 133, 23);
		add(btnSearchForBook);
		
		JButton btnHome = new JButton("Home");
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pswitch.ChangePanels("1");
			}
		});
		btnHome.setBounds(466, 287, 89, 23);
		add(btnHome);
		
	}
	public void getValues(String a)
	{
		mem_id=a;
		System.out.println(mem_id);
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
