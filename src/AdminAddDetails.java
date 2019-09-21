import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import java.awt.Font;

//11

public class AdminAddDetails extends JPanel {
	
	JLabel ISBNLabel;
	private JTextField TitleField;
	private JTextField AuthorField;
	private JTextField ShelfIdField;
	private JTextField PublisherField;
	
	String title;
	String author;
	String dept;
	String shelfid;
	String publisher;
	
	int acc_no;
	
	HomePage pswitch;
	String ISBN;

	/**
	 * Create the panel.
	 */
	public AdminAddDetails(HomePage ob) {
		
		pswitch=ob;
		setLayout(null);
		setSize(594,391);
		
		JLabel lblEnterNewBook = new JLabel("Enter New Book Details");
		lblEnterNewBook.setFont(new Font("Garamond", Font.BOLD, 18));
		lblEnterNewBook.setBounds(46, 42, 194, 33);
		add(lblEnterNewBook);
		
		ISBNLabel = new JLabel("ISBN: ");
		ISBNLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		ISBNLabel.setBounds(46, 86, 194, 14);
		add(ISBNLabel);
		
		JLabel lblTitle = new JLabel("Title:");
		lblTitle.setBounds(80, 135, 46, 14);
		add(lblTitle);
		
		JLabel lblAuthor = new JLabel("Author:");
		lblAuthor.setBounds(80, 163, 46, 14);
		add(lblAuthor);
		
		JLabel lblDepartment = new JLabel("Department:");
		lblDepartment.setBounds(80, 199, 74, 14);
		add(lblDepartment);
		
		JLabel lblShelfId = new JLabel("Shelf ID:");
		lblShelfId.setBounds(80, 239, 46, 14);
		add(lblShelfId);
		
		JLabel lblPublisher = new JLabel("Publisher:");
		lblPublisher.setBounds(80, 278, 74, 14);
		add(lblPublisher);
		
		TitleField = new JTextField();
		TitleField.setBounds(209, 132, 194, 20);
		add(TitleField);
		TitleField.setColumns(10);
		
		AuthorField = new JTextField();
		AuthorField.setBounds(209, 160, 194, 20);
		add(AuthorField);
		AuthorField.setColumns(10);
		
		String s1[] = { "Comp. Sci.", "Physics", "Mathematics", "Biology", "Economics" };
		JComboBox DeptBox = new JComboBox(s1);
		DeptBox.setBounds(209, 195, 194, 22);
		DeptBox.setEditable(false);
		add(DeptBox);
		
		ShelfIdField = new JTextField();
		ShelfIdField.setBounds(209, 236, 194, 20);
		add(ShelfIdField);
		ShelfIdField.setColumns(10);
		
		PublisherField = new JTextField();
		PublisherField.setBounds(209, 275, 194, 20);
		add(PublisherField);
		PublisherField.setColumns(10);

		JLabel ErrorLabel = new JLabel("");
		ErrorLabel.setBounds(80, 321, 257, 14);
		add(ErrorLabel);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean a,b,c,d;
				title=TitleField.getText();
				author=AuthorField.getText();
				dept=String.valueOf(DeptBox.getSelectedItem());
				shelfid=ShelfIdField.getText();
				c=CheckShelfId(shelfid);
				publisher=PublisherField.getText();
				if(c==false)
				{
					ErrorLabel.setText("Data entered was not valid. Try again.");
					TitleField.setText("");
					AuthorField.setText("");
					ShelfIdField.setText("");
					PublisherField.setText("");
					
				}
				else
				{
					try {
						int j=0;
						int copies;
						Class.forName("oracle.jdbc.driver.OracleDriver"); 
			            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","root"); 
			            
			            PreparedStatement stmt = con.prepareStatement("insert into book values(?,?,?,?,?,?,1)");
			            stmt.setString(1, ISBN);
			            stmt.setString(2, title);
			            stmt.setString(3, author);
			            stmt.setString(4, dept);
			            stmt.setString(5, shelfid);
			            stmt.setString(6, publisher);
			            
			            j=stmt.executeUpdate();
			            acc_no=GenerateAccNo();
			            stmt=con.prepareStatement("insert into book_id values(?,?,'T')");
			            stmt.setInt(1, acc_no);
			            stmt.setString(2,ISBN);
			            
			            
			            
			            pswitch.ChangePanels("12");
			            
			            
			            TitleField.setText("");
						AuthorField.setText("");
						ShelfIdField.setText("");
						PublisherField.setText("");
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
		btnAdd.setBounds(139, 346, 89, 23);
		add(btnAdd);
		
		JButton btnHome = new JButton("Home");
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pswitch.ChangePanels("1");
			}
		});
		btnHome.setBounds(453, 321, 89, 23);
		add(btnHome);
		

	}
	public void getISBN(String a)
	{
		ISBN=a;
		ISBNLabel.setText("ISBN: "+ISBN);
		
		
	}
	public boolean CheckShelfId(String a)
	{
		if(shelfid.length()!=2)
			return false;
		if(!Character.isDigit(shelfid.charAt(0))&&!Character.isAlphabetic(shelfid.charAt(1)))
				return false;
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
