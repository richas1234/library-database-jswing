import javax.swing.JPanel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
//18

public class UserReturnSuccessPage extends JPanel {

	HomePage pswitch;
	int fine;
	String mem_id;
	int track_record;
	
	JLabel lblFineToBe=new JLabel();
	JLabel RemoveLabel=new JLabel();
	JLabel RemoveLabel1=new JLabel();
	JLabel RemoveLabel2=new JLabel();
	
	/**
	 * Create the panel.
	 */
	public UserReturnSuccessPage(HomePage ob) {
		
		pswitch=ob;
		setLayout(null);
		setSize(594,391);
		
		JLabel lblTheBookHas = new JLabel("The book has been returned successfully.");
		lblTheBookHas.setFont(new Font("Georgia", Font.PLAIN, 18));
		lblTheBookHas.setBounds(47, 58, 472, 28);
		add(lblTheBookHas);
		
		lblFineToBe.setBounds(79, 110, 142, 14);
		add(lblFineToBe);
		
		RemoveLabel.setBounds(81, 151, 381, 28);
		add(RemoveLabel);
		
		
		RemoveLabel1.setBounds(79, 189, 367, 28);
		add(RemoveLabel1);
		
		RemoveLabel2.setBounds(79, 228, 383, 28);
		add(RemoveLabel2);
		
		JButton btnHome = new JButton("Home");
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pswitch.ChangePanels("1");
			}
		});
		btnHome.setBounds(430, 299, 89, 23);
		add(btnHome);
		

	}
	public void getValues(int a,String b)
	{
		fine=a;
		System.out.println("Fine: "+fine);
		mem_id=b;
		String s="Fine to be paid: "+fine+" INR";
		lblFineToBe.setText(s);
		if(fine>0)
		{
			try {
				int j=0;
				Class.forName("oracle.jdbc.driver.OracleDriver"); 
	            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","root"); 
	            
	            PreparedStatement stmt = con.prepareStatement("select track_record from member where member_id=?");
	            stmt.setString(1, mem_id);
	            ResultSet rs=stmt.executeQuery();
	            
	            if(rs.next())
	            {
	            	track_record=rs.getInt(1);
	            	if(track_record==1)
	            	{//remove member from table
	            		RemoveLabel.setText("You have returned books past due date at least 5 times");
	            		RemoveLabel1.setText("You will now be removed as library member.");
	            		RemoveLabel2.setText("Consult librarians to be whitelisted.");
	            		stmt=con.prepareStatement("delete from member where member_id=?");
	            		stmt.setString(1, mem_id);
	            		j=stmt.executeUpdate();
	            		//add member id into blacklist
	            		stmt=con.prepareStatement("insert into blacklist values(?)");
	            		stmt.setString(1, mem_id);
	            		j=stmt.executeUpdate();         		
	            		
	            	}
	            	else
	            	{
	            		track_record--;
	            		stmt=con.prepareStatement("update member set track_record=? where member_id=?");
	            		stmt.setInt(1, track_record);
	            		stmt.setString(2, mem_id);
	            		j=stmt.executeUpdate();
	            	}
	            	
					
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
}
