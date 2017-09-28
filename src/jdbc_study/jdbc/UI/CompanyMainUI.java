package jdbc_study.jdbc.UI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class CompanyMainUI extends JFrame {

	private JPanel contentPane;


	public CompanyMainUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanelMenuUI pMenu = new JPanelMenuUI();
		contentPane.add(pMenu, BorderLayout.NORTH);
		
		JPanelDept pView = new JPanelDept();
		contentPane.add(pView, BorderLayout.CENTER);
	}

}
