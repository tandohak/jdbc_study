package jdbc_study.jdbc.UI;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import jdbc_study.jdbc.dao.DepartmentDao;
import jdbc_study.jdbc.dto.Department;

public class JPanelDept extends JPanel {
	private JTextField tfDeptName;
	private JTextField tfDeptNo;
	private JTextField tfDeptFloor;
	private JTable table;
	private JButton btnAdd;
	private JButton btnCancle;
	private DepartmentDao deptDao;
	private List<Department> lists;
	
	public void setDeptDao(DepartmentDao deptDao) {
		this.deptDao = deptDao;
	}

	private static final String[] columnNames = new String[] {"부서번호", "부서명", "위치"};
	private DefaultTableModel model;
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
		
		btnAdd = new JButton("추가");
		panel.add(btnAdd);
		
		btnCancle = new JButton("취소");
		panel.add(btnCancle);
		
		JPanel panel_1 = new JPanel();
		add(panel_1);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane);
		
		deptDao = DepartmentDao.getInstance();
		lists = deptDao.selectDepartmentByAll();
		
		table = new JTable();
		scrollPane.setViewportView(table);
		model = new DefaultTableModel(repackLists(lists), columnNames);
		table.setModel(model);		
	}	
	
	
	
	public Object[][] repackLists(List<Department> lists){
		Object[][] newLists = new Object[lists.size()][];
		
		int i = 0;
		for( Department d : lists){
			
			Object[] deptArr = d.toArray();
			
			newLists[i++] = deptArr;
		}
		
		return newLists;		
	}
	
	public void setBtnAdd(String btnName) {
		btnAdd.setText(btnName);
	}



	public JButton getBtnAdd() {
		return btnAdd;
	}

	public JButton getBtnCancle() {
		return btnCancle;
	}
	
	public void setDept(Department newDept){
		String deptNo = String.valueOf(newDept.getDeptNo());
		String deptName = newDept.getDeptname();
		String floor = String.valueOf(newDept.getFloor());
		
		tfDeptNo.setText(deptNo);
		tfDeptName.setText(deptName);
		tfDeptFloor.setText(floor);
	}
	
	public void setEnable(boolean isOk){
		if(!isOk){
			tfDeptNo.setEnabled(true);
			tfDeptName.setEditable(false);
			tfDeptFloor.setEditable(false);
			return;
		}
		tfDeptName.setEditable(true);
		tfDeptFloor.setEditable(true);		
	}
	
	public Department getDept(boolean isOk){
		int deptNo = Integer.parseInt(tfDeptNo.getText());
		
		if(isOk){			
			String deptName = tfDeptName.getText();
			int floor = Integer.parseInt(tfDeptFloor.getText());
			
			return new Department(deptNo, deptName, floor);	
		}
		
		return new Department(deptNo);	
	}
}
