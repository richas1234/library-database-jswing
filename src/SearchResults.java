import java.sql.*;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
public class SearchResults extends JFrame {
public int flag=1;
	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SearchResults frame = new SearchResults();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SearchResults() {
		setTitle("Search");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(200, 100, 787, 494);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTypeTextTo = new JLabel("Type text to search:");
		lblTypeTextTo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTypeTextTo.setBounds(208, 44, 158, 22);
		contentPane.add(lblTypeTextTo);
		
		JTextArea searchResults = new JTextArea();
		searchResults.setBounds(10, 146, 751, 298);
		contentPane.add(searchResults);
		
		textField = new JTextField();
		textField.setBounds(338, 47, 195, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		//searchResults.setText("ugug");
		JButton btnNewButton = new JButton("Search by ISBN");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					
				try {//searchResults.setText("ugug");
					Class.forName("oracle.jdbc.driver.OracleDriver");

					Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","root");
					
					String isbn="%"+textField.getText()+"%";
					PreparedStatement stat=con.prepareStatement("select acc_no,isbn,title,author,shelf_id,avail from "
							+ "book_id natural join book where ISBN like ?");
							stat.setString(1,isbn);							
							ResultSet rs=stat.executeQuery();
							String temp="";
							while(rs.next())
				            {
								flag=0;
							   temp=temp+"\n"+"Acc: "+rs.getInt(1)+"   "+"ISBN:"+rs.getString(2)+"   "+"Title:"+rs.getString(3)+"   "+"Author:"+rs.getString(4)+"   "+"Shelf-id:"+rs.getString(5)+"   "+"Available(T/F):"+rs.getString(6)+"\n";
							   
				            }	
							searchResults.setText(temp);
															
			                
				            con.close();
						
				if(flag==1)
					searchResults.setText("Not found\n");}
			
				catch(Exception e) {
					
				}
			}
		});
		flag=1;
		btnNewButton.setBounds(51, 89, 130, 35);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_2 = new JButton("Search by Publisher");
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {//searchResults.setText("ugug");
					Class.forName("oracle.jdbc.driver.OracleDriver");

					Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","root");
					
					String pub="%"+textField.getText()+"%";
					PreparedStatement stat=con.prepareStatement("select acc_no,book.isbn,title,author,shelf_id,avail from "
							+ "book_id, book where lower(publisher) like ? and book_id.ISBN=book.ISBN");
							pub=pub.toLowerCase();
							stat.setString(1,pub);					
							ResultSet rs=stat.executeQuery();
							String temp="";
							while(rs.next())
								
				            {
								flag=0;
								temp=temp+"\n"+"Acc: "+rs.getInt(1)+"   ISBN:"+rs.getString(2)+"   "+"Title:"+rs.getString(3)+"   "+"Author:"+rs.getString(4)+"   "+"Shelf-id:"+rs.getString(5)+"   "+"Available(T/F):"+rs.getString(6)+"\n";
								     
				            }	
							//System.out.println(temp);
							searchResults.setText(temp);
															
			                
				            con.close();
				            if(flag==1)
								searchResults.setText("Not found\n");
						}
				
			
				catch(Exception e) {
					
				}
				
			}
		});
		flag=1;
		btnNewButton_2.setBounds(206, 89, 175, 35);
		contentPane.add(btnNewButton_2);
		
		JButton btnSearchByAuthor = new JButton("Search by Author");
		btnSearchByAuthor.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnSearchByAuthor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Class.forName("oracle.jdbc.driver.OracleDriver");

					Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","root");
					
					String aut="%"+textField.getText()+"%";
					PreparedStatement stat=con.prepareStatement("select acc_no,isbn,title,author,shelf_id,avail from "
							+ "book_id natural join book where lower(author) like ?");
							aut=aut.toLowerCase();
							stat.setString(1,aut);	
							
							ResultSet rs=stat.executeQuery();
							String temp="";
							while(rs.next())
								
				            {
								flag=0;
								temp=temp+"\n"+"Acc: "+rs.getInt(1)+"   ISBN:"+rs.getString(2)+"   "+"Title:"+rs.getString(3)+"   "+"Author:"+rs.getString(4)+"   "+"Shelf-id:"+rs.getString(5)+"   "+"Available(T/F):"+rs.getString(6)+"\n";
								    
				            }	
							
							searchResults.setText(temp);
															
			                
				            con.close();
				        	if(flag==1)
								searchResults.setText("Not found\n");
						}
				
			
				catch(Exception e) {
				
				}
			}
		});
		btnSearchByAuthor.setBounds(407, 89, 153, 35);
		contentPane.add(btnSearchByAuthor);
		
		JLabel lblBookSearch = new JLabel("Book Search");
		lblBookSearch.setHorizontalAlignment(SwingConstants.CENTER);
		lblBookSearch.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblBookSearch.setBounds(308, 11, 144, 22);
		contentPane.add(lblBookSearch);
		
		JButton btnSearchByTitle = new JButton("Search by Title");
		btnSearchByTitle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				try {
					Class.forName("oracle.jdbc.driver.OracleDriver");

					Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","root");
					
					String tit="%"+textField.getText()+"%";
					PreparedStatement stat=con.prepareStatement("select acc_no,isbn,title,author,shelf_id,avail from "
							+ "book_id natural join book where lower(title) like ?");
							tit=tit.toLowerCase();
							stat.setString(1,tit);		
							ResultSet rs=stat.executeQuery();
							String temp="";
							while(rs.next())
								
				            {
								flag=0;
								temp=temp+"\n"+"Acc: "+rs.getInt(1)+"   ISBN:"+rs.getString(2)+"   "+"Title:"+rs.getString(3)+"   "+"Author:"+rs.getString(4)+"   "+"Shelf-id:"+rs.getString(5)+"   "+"Available(T/F):"+rs.getString(6)+"\n";
								    
				            }	
							
							searchResults.setText(temp);
															
			                
				            con.close();
				        	if(flag==1)
								searchResults.setText("Not found\n");
						}
				
			
				catch(Exception e) {
				
				}
			}
		});
		btnSearchByTitle.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnSearchByTitle.setBounds(580, 89, 153, 35);
		contentPane.add(btnSearchByTitle);
		
		
		
		
	}
}
