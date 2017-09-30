package jdbc_study.jdbc.UI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

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
	private JPopupMenu popup;
	
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
		
		model = new DefaultTableModel(getData(lists), columnNames);
		table.setModel(model);
		scrollPane.setViewportView(table);
		table.addMouseListener(mouseListener );
		popup = new JPopupMenu();
		table.add(popup);
		JMenuItem itemUpdate = new JMenuItem("수정");
		JMenuItem itemDelete = new JMenuItem("삭제");
		popup.add(itemUpdate);	
		popup.add(itemDelete);	
		itemUpdate.addActionListener(ItemAction);
		itemDelete.addActionListener(ItemAction);
		
	}
	
	public boolean tfConfirm(int isType){
		
		switch (isType) {
			case 1:
				if(tfEmpName.getText().equals("")|| tfEmpNo.getText().equals("") || tfEmpSalary.getText().equals("") || tfTitle.getText().equals("")){
					JOptionPane.showMessageDialog(null, "빈칸을 모두 체우세요.", "경고!", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				break;
			case 2:
				if(tfEmpNo.getText().equals("")){
					JOptionPane.showMessageDialog(null, "빈칸을 모두 체우세요.", "경고!", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				break;
		}
		System.out.println("true");
		return true;
	}
	
	public void setEnable(boolean isOk){		
		if(!isOk){			
			tfEmpSalary.setEditable(false);
			tfEmpName.setEditable(false);
			tfTitle.setEditable(false);
			cbEmpDeptNo.setEnabled(false);
			cbEmpManager.setEnabled(false);
			return;
		}
		cbEmpDeptNo.setEnabled(true);
		cbEmpManager.setEnabled(true);
		tfEmpSalary.setEditable(true);
		tfEmpName.setEditable(true);
		tfTitle.setEditable(true);
		
	}
	
	private ActionListener ItemAction = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand() == "수정"){
			
				int row = table.getSelectedRow();
				String empNo = String.valueOf(table.getValueAt(row, 0));
				Employee emp = empDao.selectEmployeeByNo(new Employee(Integer.parseInt(empNo)));
				setEmp(emp);
				
				setBtnName("수정");
			}
			if(e.getActionCommand() == "삭제"){
				int row = table.getSelectedRow();
				String empNo = String.valueOf(table.getValueAt(row, 0));
				empDao.deletEmployee(new Employee(Integer.parseInt(empNo)));
				model.removeRow(row);
			}
		}
	};
	
	private MouseListener mouseListener = new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getButton()==3){
				popup.show((Component) e.getSource(), e.getX(), e.getY());
			}
		}
	};
	private DefaultTableModel model;
	
	
	
	
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
