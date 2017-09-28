package jdbc_study.jdbc.UI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import javafx.scene.control.ComboBox;
import jdbc_study.jdbc.dao.DepartmentDao;
import jdbc_study.jdbc.dao.EmployeeDao;
import jdbc_study.jdbc.dto.Department;
import jdbc_study.jdbc.dto.Employee;

public class JPanelEmp extends JPanel {
	private JTextField tfEmpNo;
	private JTextField tfEmpName;
	private JTextField tfEmpSalary;
	private JTextField tfTitle;
	private JTable table;
	private JComboBox cbEmpManager;
	private JComboBox cbEmpDeptNo;
	private JButton btn;
	private JButton btnCancle;
	private EmployeeDao empDao;
	private DepartmentDao deptDao;
	private static final String[] columnNames =  new String[] {"사원 번호", "사원명", "직급", "관리자", "급여", "부서번호"};
	private List<Employee> lists;
	private List<Department> deptLists;
	public JPanelEmp() {
		deptDao = DepartmentDao.getInstance();
		deptLists = deptDao.selectDepartmentByAll();
		empDao = EmployeeDao.getInstance();
		lists = empDao.selectEmployeeByAll();
		
		setLayout(null);
		
		JPanel pEmpInfo = new JPanel();
		pEmpInfo.setBounds(0, 0, 225, 300);
		add(pEmpInfo);
		
		JLabel lblEmpInfo = new JLabel("사원 정보");
		pEmpInfo.add(lblEmpInfo);
		
		JPanel pEmpData = new JPanel();
		pEmpInfo.add(pEmpData);
		pEmpData.setLayout(new GridLayout(0, 2, 10, 10));
		
		JLabel lblEmpNo = new JLabel("사원번호");
		pEmpData.add(lblEmpNo);
		
		tfEmpNo = new JTextField();
		pEmpData.add(tfEmpNo);
		tfEmpNo.setColumns(8);
		
		JLabel lblEmpName = new JLabel("사원명");
		pEmpData.add(lblEmpName);
		
		tfEmpName = new JTextField();
		pEmpData.add(tfEmpName);
		tfEmpName.setColumns(8);
		
		JLabel lblEmpManager = new JLabel("관리자");
		pEmpData.add(lblEmpManager);
		
		cbEmpManager = new JComboBox<String>();
		cbEmpManager.setModel(new DefaultComboBoxModel(getManager(lists)));
		pEmpData.add(cbEmpManager);
		
		JLabel lblEmpDeptNo = new JLabel("부서");
		pEmpData.add(lblEmpDeptNo);
		
		cbEmpDeptNo = new JComboBox<String>();
		cbEmpDeptNo.setModel(new DefaultComboBoxModel(getDeptName(deptLists)));
		pEmpData.add(cbEmpDeptNo);
		
		JLabel lblEmpSalary = new JLabel("급여");
		pEmpData.add(lblEmpSalary);
		
		tfEmpSalary = new JTextField();
		pEmpData.add(tfEmpSalary);
		tfEmpSalary.setColumns(8);
		
		JLabel lblTitle = new JLabel("직책");
		pEmpData.add(lblTitle);
		
		tfTitle = new JTextField();
		pEmpData.add(tfTitle);
		tfTitle.setColumns(8);
		
		btn = new JButton("추가");
		pEmpInfo.add(btn);
		
		btnCancle = new JButton("취소");
		pEmpInfo.add(btnCancle);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(225, 0, 448, 300);
		add(panel_1);
		panel_1.setLayout(new BorderLayout(10, 10));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane);
		
		
		table = new JTable();
		
		DefaultTableModel model = new DefaultTableModel(getData(lists), columnNames);
		table.setModel(model);
		scrollPane.setViewportView(table);
		
	}
	
	private String[] getDeptName(List<Department> deptLists){
		String [] tempList = new String[deptLists.size()];
		
		int i = 0;
		for(Department e : deptLists){
			String str = "(" + e.getDeptNo() + ")" + e.getDeptname();
			tempList[i++] = str;
		}
		
		return tempList;
	}
	
	private String[] getManager(List<Employee> emplists){
		String [] tempList = new String[emplists.size()];
		int i = 0;
		for(Employee e : emplists){
			String str = "(" + e.getEmpno() + ")" + e.getEmpname();
			tempList[i++] = str;
		}
		
		return tempList;
	}
	
	private Object[][] getData(List<Employee> lists) {
		Object[][] repackLists = new Object[lists.size()][];
		
		int i=0;
		for(Employee e : lists){
			repackLists[i++] = e.toArray();
		}
		
		return repackLists;
	}

	public void setEmpDao(EmployeeDao empDao) {
		this.empDao = empDao;
	}

	
	public void setDeptDao(DepartmentDao deptDao) {
		this.deptDao = deptDao;
	}

	public JButton getBtn() {
		return btn;
	}

	public JButton getBtnCancle() {
		return btnCancle;
	}
	
	public void setBtnName(String text){
		btn.setText(text);		
	}
	
	public void setEmp(Employee emp){
		tfEmpNo.setText(String.valueOf(emp.getEmpno()));
		tfEmpName.setText(emp.getEmpname());
		tfTitle.setText(emp.getTitle());
		
		for(int i=0; i<cbEmpDeptNo.getItemCount(); i++){
			String item = (String)cbEmpDeptNo.getItemAt(i);
			int deptNo = Integer.parseInt(item.substring(item.indexOf("(")+1, item.indexOf(")")));
			
			if(deptNo == emp.getDno()){
				cbEmpDeptNo.setSelectedIndex(i);
			}
		}
		
		for(int i=0; i<cbEmpManager.getItemCount(); i++){
			String item = (String)cbEmpManager.getItemAt(i);
			int No = Integer.parseInt(item.substring(item.indexOf("(")+1, item.indexOf(")")));
			
			if(No == emp.getManager()){
				cbEmpManager.setSelectedIndex(i);
			}
		}
		
		tfEmpSalary.setText(String.valueOf(emp.getSalary()));		
	}

	
	
	public Employee getEmp(boolean isOk){
		int empno = Integer.parseInt(tfEmpNo.getText());
		
		if(isOk){			
			String empname = tfEmpName.getText();
			String title =  tfTitle.getText();
			String manager = String.valueOf(cbEmpManager.getSelectedItem());
			int managerNum = Integer.parseInt(manager.substring(manager.indexOf("(")+1, manager.indexOf(")")));
			int salary =  Integer.parseInt(tfEmpSalary.getText());
			String dempName = String.valueOf(cbEmpDeptNo.getSelectedItem());
			int dno = Integer.parseInt(dempName.substring(dempName.indexOf("(")+1, dempName.indexOf(")")));
			return new Employee(empno, empname, title, managerNum, salary, dno);
		}
		
		return new Employee(empno);	
	}
}
