package jdbc_study.jdbc.UI;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.JMenuBar;
import java.awt.BorderLayout;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class JPanelMenuUI extends JPanel {

	public JPanelMenuUI() {
		setLayout(new BorderLayout(0, 0));
		
		JMenuBar menuBar = new JMenuBar();
		add(menuBar, BorderLayout.NORTH);
		
		JMenu empMenu = new JMenu("사원 관리");
		menuBar.add(empMenu);
		
		JMenuItem mntmEmpAdd = new JMenuItem("추가");
		empMenu.add(mntmEmpAdd);
		
		JMenuItem menuEmpUpdate = new JMenuItem("수정");
		empMenu.add(menuEmpUpdate);
		
		JMenuItem menuEmpDel = new JMenuItem("삭제");
		empMenu.add(menuEmpDel);
		
		JMenuItem menuEmpSearch = new JMenuItem("검색");
		empMenu.add(menuEmpSearch);
		
		JMenu detpMenu = new JMenu("부서 관리");
		menuBar.add(detpMenu);
		
		JMenuItem menuDeptAdd = new JMenuItem("추가");
		detpMenu.add(menuDeptAdd);
		
		JMenuItem menuDeptUpdate = new JMenuItem("수정");
		detpMenu.add(menuDeptUpdate);
		
		JMenuItem menuDeptDel = new JMenuItem("삭제");
		detpMenu.add(menuDeptDel);
		
		JMenuItem menuDeptSearch = new JMenuItem("검색");
		detpMenu.add(menuDeptSearch);

	}

}
