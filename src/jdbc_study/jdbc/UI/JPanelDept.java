package jdbc_study.jdbc.UI;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;

public class JPanelDept extends JPanel {
	private JTextField tfDeptName;
	private JTextField tfDeptNo;
	private JTextField tfDeptFloor;
	private JTable table;

	public JPanelDept() {
		setLayout(new GridLayout(0, 1, 10, 10));
		
		JPanel panel = new JPanel();
		add(panel);
		panel.setLayout(new GridLayout(0, 2, 10, 10));
		
		JLabel lblDeptNo = new JLabel("부서번호");
		lblDeptNo.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblDeptNo);
		
		tfDeptNo = new JTextField();
		tfDeptNo.setColumns(10);
		panel.add(tfDeptNo);
		
		JLabel lblDeptName = new JLabel("부서명");
		lblDeptName.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblDeptName);
		
		tfDeptName = new JTextField();
		tfDeptName.setColumns(10);
		panel.add(tfDeptName);
		
		JLabel lblDeptFloor = new JLabel("위치");
		lblDeptFloor.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblDeptFloor);
		
		tfDeptFloor = new JTextField();
		tfDeptFloor.setColumns(10);
		panel.add(tfDeptFloor);
		
		JButton btnAdd = new JButton("추가");
		panel.add(btnAdd);
		
		JButton btnCancle = new JButton("취소");
		panel.add(btnCancle);
		
		JPanel panel_1 = new JPanel();
		add(panel_1);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
			},
			new String[] {
				"부서번호", "부서명", "위치"
			}
		));

	}

}
