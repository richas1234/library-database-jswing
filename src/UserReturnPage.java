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

//17

public class UserReturnPage extends JPanel {
	private JTextField AccField;
	HomePage pswitch;

	/**
	 * Create the panel.
	 */
	String mem_id;
	int acc_no;
	String due_date;
	int fine;
	
	public UserReturnPage(HomePage ob) {
		
		pswitch=ob;
		setLayout(null);
		setSize(594,391);
		
		JLabel lblEnterAccnoOf = new JLabel("Enter acc_no of book to return: ");
		lblEnterAccnoOf.setFont(new Font("Georgia", Font.BOLD, 18));
		lblEnterAccnoOf.setBounds(61, 65, 310, 37);
		add(lblEnterAccnoOf);
		
		AccField = new JTextField();
		AccField.setBounds(144, 125, 295, 20);
		add(AccField);
		AccField.setColumns(10);
		

		JLabel ErrorLabel = new JLabel("");
		ErrorLabel.setBounds(110, 180, 303, 37);
		add(ErrorLabel);
		
		JButton btnReturn = new JButton("Return");
		btnReturn.addActionListener(new ActionListener() {
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
			            
			            PreparedStatement stmt = con.prepareStatement("select to_char(due_date,'YYYY-MM-DD') from borrow_status where acc_no=? and member_id=?");
			            stmt.setInt(1, acc_no);
			            stmt.setString(2, mem_id);
			            ResultSet rs=stmt.executeQuery();
			            ResultSet rs1;
			            
			            if(rs.next())
			            {//the book was borrowed by user
			            	due_date=rs.getString(1);
			            	System.out.println(due_date);
			            	stmt = con.prepareStatement("select sysdate-to_date(?,'YYYY-MM-DD') from dual");
			            	stmt.setString(1, due_date);
			            	rs1=stmt.executeQuery();
			            	if(rs1.next())
			            	{
			            		int days=rs1.getInt(1);
			            		fine=CalcFine(days);
			            		pswitch.getFine(fine,mem_id);
			            		AccField.setText("");
			            		pswitch.ChangePanels("18");			            		
			            	}
			            	//Now remove that tuple from borrow status
			            	stmt=con.prepareStatement("delete from borrow_status where member_id=? and acc_no=?");
			            	stmt.setString(1, mem_id);
			            	stmt.setInt(2, acc_no);
			            	j=stmt.executeUpdate();
			            	//set avail of book_id tuple to true
			            	stmt=con.prepareStatement("update book_id set avail='T' where acc_no=?");
			            	stmt.setInt(1, acc_no);
			            	j=stmt.executeUpdate();							
			            }
			            else
			            {
			            	ErrorLabel.setText("Sorry, the acc_no is invalid or the book is already borrowed. Try Again.");
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
		btnReturn.setBounds(210, 256, 89, 23);
		add(btnReturn);
		
		JButton btnHome = new JButton("Home");
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pswitch.ChangePanels("1");
			}
		});
		btnHome.setBounds(446, 300, 89, 23);
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
	public int CalcFine(int a)
	{
		if(a<=0)
			return 0;
		if(a<8)
			return a;
		if(a<15)
			return (7+(a-7)*2);
		if(a<31)
			return (21+(a-14)*5);
		return (101+(a-30)*10);
		
		
	}

}
