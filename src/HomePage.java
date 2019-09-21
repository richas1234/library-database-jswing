import javax.swing.*;
import java.awt.event.*;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//1
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class HomePage {
	
	JFrame frame;
	JPanel controlPanel=new JPanel();
	JPanel panelCont=new JPanel();
	JPanel homepanel=new JPanel();
	
	
	LoginPage ob1;
	RegisterPage ob2;
	UserLoginPage ob4;
	UserMainPage ob5;
	RegisterSuccessPage ob6;
	AdminLoginPage ob7;
	AdminMainPage ob8;
	AdminAddPage ob9;
	AdminAddSuccessPage ob10;
	AdminAddDetails ob11;
	AdminRemoveBook ob12;
	AdminRemoveSuccessPage ob13;
	UserBorrowPage ob14;
	UserBorrowSuccessPage ob15;
	UserReturnPage ob16;
	UserReturnSuccessPage ob17;
	
	SearchResults ob3;
	
	CardLayout cl;
	
	
	public HomePage()
	{
		ob1=new LoginPage(this);
		ob2=new RegisterPage(this);
		ob4=new UserLoginPage(this);
		ob5=new UserMainPage(this);
		ob6=new RegisterSuccessPage(this);
		ob7=new AdminLoginPage(this);
		ob8=new AdminMainPage(this);
		ob9=new AdminAddPage(this);
		ob10=new AdminAddSuccessPage(this);
		ob11=new AdminAddDetails(this);
		ob12=new AdminRemoveBook(this);
		ob13=new AdminRemoveSuccessPage(this);
		ob14=new UserBorrowPage(this);
		ob15=new UserBorrowSuccessPage(this);
		ob16=new UserReturnPage(this);
		ob17=new UserReturnSuccessPage(this);
		cl=new CardLayout();
		
		panelCont.setSize(594,391);
		panelCont.setLayout(cl);
		panelCont.add(homepanel,"1");
		panelCont.add(ob1,"2");
		panelCont.add(ob2,"3");
		panelCont.add(ob4,"5");
		panelCont.add(ob5,"6");
		panelCont.add(ob6,"7");
		panelCont.add(ob7,"8");
		panelCont.add(ob8,"9");
		panelCont.add(ob9,"10");
		panelCont.add(ob10,"12");
		panelCont.add(ob11,"11");
		panelCont.add(ob12,"13");
		panelCont.add(ob13,"14");
		panelCont.add(ob14,"15");
		panelCont.add(ob15,"16");
		panelCont.add(ob16,"17");
		panelCont.add(ob17,"18");
	}
	public void PrepareHomePage()
	{
		
		frame = new JFrame();
		frame.setSize(594, 391);
		frame.getContentPane().setLayout(null);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		controlPanel.setSize(594,391);
		controlPanel.setLayout(null);
		
		frame.add(controlPanel);
		frame.setVisible(true);
		
		
		homepanel.setSize(594,391);
		homepanel.setLayout(null);
		
		controlPanel.add(panelCont);
		
		
		
		ChangePanels("1");	
		
		
		
		JButton btnLogin = new JButton("LOGIN");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ChangePanels("2");
				
			}
		});
		btnLogin.setFont(new Font("Garamond", Font.BOLD, 14));
		btnLogin.setBounds(217, 118, 110, 23);
		homepanel.add(btnLogin);
		
		JButton btnRegister = new JButton("REGISTER");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ChangePanels("3");
				
			}
		});
		btnRegister.setFont(new Font("Garamond", Font.BOLD, 14));
		btnRegister.setBounds(217, 160, 110, 23);
		homepanel.add(btnRegister);
		
		JButton btnBookSearch = new JButton("Search For A Book");
		btnBookSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ob3=new SearchResults();
				ob3.setVisible(true);
			}
		});
		btnBookSearch.setFont(new Font("Garamond", Font.BOLD, 14));
		btnBookSearch.setBounds(188, 255, 172, 23);
		homepanel.add(btnBookSearch);
		
		JLabel lblUniversityLibraryPortal = new JLabel("~University Library Portal~");
		lblUniversityLibraryPortal.setFont(new Font("Georgia", Font.BOLD, 18));
		lblUniversityLibraryPortal.setBounds(134, 40, 309, 42);
		homepanel.add(lblUniversityLibraryPortal);
		
		JLabel lblOr = new JLabel("Or,");
		lblOr.setFont(new Font("Garamond", Font.BOLD, 20));
		lblOr.setBounds(252, 218, 46, 23);
		homepanel.add(lblOr);
		
		frame.setVisible(true);
		
	}
	public void ChangePanels(String a)
	{
		cl.show(panelCont, a);
	}
	
	
	public void UserBorrowReturn(String a,String b)
	{
		ob5.getValues(a,b);
		
	}
	public void AdminAddRemove(String a,String b)
	{
		ob8.getValues(a, b);
	}
	public void AdminGetISBN(String a)
	{
		ob11.getISBN(a);
	}
	public void UserGetMemID(String a)
	{
		ob14.getValues(a);
	}
	public void UserGetMemID1(String a)
	{
		ob16.getValues(a);
	}
	public void getFine(int a,String b)
	{
		ob17.getValues(a,b);
	}
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HomePage window = new HomePage();
					window.PrepareHomePage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

}
