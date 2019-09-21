import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;

//3

public class RegisterPage extends JPanel {
	private JTextField IDField;
	private JTextField NameField;
	private JTextField DOBField;
	
	String ID;
	String Name;
	String DOB;
	
	boolean validID;
	boolean validName;
	boolean validDOB;
	
	HomePage pswitch;

	/**
	 * Create the panel.
	 */
	public RegisterPage(HomePage ob) {
		
		pswitch=ob;
		setSize(594, 391);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);
		
		JLabel lblRegisterAsA = new JLabel("Register As A Library Member");
		lblRegisterAsA.setFont(new Font("Georgia", Font.BOLD, 18));
		lblRegisterAsA.setBounds(50, 48, 339, 34);
		add(lblRegisterAsA);
		
		JLabel lblEnterStudentId = new JLabel("Enter University ID:");
		lblEnterStudentId.setBounds(58, 115, 132, 14);
		add(lblEnterStudentId);
		
		JLabel lblEnterName = new JLabel("Enter Name: ");
		lblEnterName.setBounds(58, 159, 109, 14);
		add(lblEnterName);
		
		JLabel lblEnterDateOf = new JLabel("Enter Date of Birth: ");
		lblEnterDateOf.setBounds(58, 208, 132, 14);
		add(lblEnterDateOf);
		
		IDField = new JTextField();
		IDField.setBounds(236, 112, 132, 20);
		add(IDField);
		IDField.setColumns(10);
		
		NameField = new JTextField();
		NameField.setBounds(236, 156, 132, 20);
		add(NameField);
		NameField.setColumns(10);
		
		DOBField = new JTextField();
		DOBField.setBounds(236, 205, 132, 20);
		add(DOBField);
		DOBField.setColumns(10);
		
		IDField.setText("");
		NameField.setText("");
		DOBField.setText("");
		
		JLabel lblthisWillAlso = new JLabel("(this will also be your username)");
		lblthisWillAlso.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblthisWillAlso.setBounds(392, 115, 154, 14);
		add(lblthisWillAlso);
		
		JLabel lblformatDdmmyyy = new JLabel("(Format: DD-MM-YYYY)");
		lblformatDdmmyyy.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblformatDdmmyyy.setBounds(392, 208, 154, 14);
		add(lblformatDdmmyyy);
		
		JLabel ErrorLabel = new JLabel("");
		ErrorLabel.setFont(new Font("Tahoma", Font.PLAIN, 10));
		ErrorLabel.setBounds(87, 253, 265, 14);
		add(ErrorLabel);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				ID=IDField.getText();
				Name=NameField.getText();
				DOB=DOBField.getText();
				validID=checkID(ID);
				validName=checkName(Name);
				validDOB=checkDate(DOB);
				
				if(validID==false||validName==false||validDOB==false)
				{
					ErrorLabel.setText("Entered information is invalid. Please try again.");
					IDField.setText("");
					NameField.setText("");
					DOBField.setText("");					
				}
				else
				{
					int j=0;
					try {
						Class.forName("oracle.jdbc.driver.OracleDriver"); 
			            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","root"); 
			            
			            PreparedStatement stmt = con.prepareStatement("select * from blacklist where member_id=?");
			            PreparedStatement stmt1=con.prepareStatement("select member_id from member where member_id=?");
			            stmt.setString(1, ID);
			            stmt1.setString(1, ID);
			            ResultSet rs=stmt.executeQuery();
			            ResultSet rs1=stmt1.executeQuery();
			            if(rs.next())
			            {
			            	
			            	ErrorLabel.setText("The given ID is barred from becoming a member");
							
			            }
			            else if(rs1.next())
			            {
			            	ErrorLabel.setText("The given username is already a member. Please login from the Homepage.");
			            }
			            else
			            {
			            	stmt=con.prepareStatement("insert into member values(?,?,to_date(?,'DD-MM-YYYY'),sysdate,5)");
			            	stmt.setString(1, ID);
			            	stmt.setString(2, Name);
			            	stmt.setString(3, DOB );
			            	j=stmt.executeUpdate();
			            	pswitch.ChangePanels("7");
			            	
			            }
			            con.close();
					}
					catch(Exception exc)
					{
						System.out.println("Exception");
						exc.printStackTrace();
					}
				
				}
				
				IDField.setText("");
				NameField.setText("");
				DOBField.setText("");
				
				
				
			}
		});
		btnRegister.setBounds(166, 295, 89, 23);
		add(btnRegister);
		
		JButton btnHome = new JButton("Home");
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IDField.setText("");
				NameField.setText("");
				DOBField.setText("");
				pswitch.ChangePanels("1");
			}
		});
		btnHome.setBounds(457, 322, 89, 23);
		add(btnHome);
		
		
		
		

	}
	public boolean checkID(String id)
	{
		int i;
		if(id.charAt(0)!='S'&&id.charAt(0)!='T')
			return false;
		if(id.length()!=5) 
			return false;
		for(i=1;i<=4;i++)
		{
			if(!Character.isDigit(id.charAt(i)))
				return false;			
		}
		return true;
		
	}
	public boolean checkName(String name)
	{
		int i=0;
		if(name.length()==0)
			return false;
		while(i<name.length())
		{
			if(!Character.isLetter(name.charAt(i)))
				return false;
			i++;
		}
		return true;
	}
	public boolean checkDate(String date)
	{
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        format.setLenient(false);
        try {
            format.parse(date);
        } catch (ParseException e) {
            return false;
        }
        return true;
	}
}
