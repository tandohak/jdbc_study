package jdbc_study.jdbc.UI;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import jdbc_study.jdbc.dao.DepartmentDao;
import jdbc_study.jdbc.dto.Department;
import jdbc_study.jdbc.dto.Employee;

public class JPanelDept extends JPanel {
	private JTextField tfDeptName;
	private JTextField tfDeptNo;
	private JTextField tfDeptFloor;
	private JTable table;
	private JButton btnAdd;
	private JButton btnCancle;
	private DepartmentDao deptDao;
	private List<Department> lists;
	private JPopupMenu popup;
	
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
		table.addMouseListener(mouseListener);
		popup = new JPopupMenu();
		table.add(popup);
		JMenuItem itemUpdate = new JMenuItem("수정");
		JMenuItem itemDelete = new JMenuItem("삭제");
		popup.add(itemUpdate);	
		popup.add(itemDelete);	
		itemUpdate.addActionListener(ItemAction);
		itemDelete.addActionListener(ItemAction);
	}	
	
	private ActionListener ItemAction = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand() == "수정"){
			
				int row = table.getSelectedRow();
				String deptNo = String.valueOf(table.getValueAt(row, 0));
				Department dept = deptDao.selectDepartmentByNo(new Department(Integer.parseInt(deptNo)));
				setDept(dept);
				
				setBtnAdd("수정");
			}
			if(e.getActionCommand() == "삭제"){
				int row = table.getSelectedRow();
				String deptNo = String.valueOf(table.getValueAt(row, 0));
				deptDao.deleteDepartment(new Department(Integer.parseInt(deptNo)));
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
	
	public boolean tfConfirm(int isType){
		
		switch (isType) {
			case 1:
				if(tfDeptNo.getText().equals("") || tfDeptNo.getText().equals("") || tfDeptNo.getText().equals("")){
					JOptionPane.showMessageDialog(null, "빈칸을 모두 체우세요.", "경고!", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				break;
			case 2:
				if(tfDeptNo.getText().equals("")){
					JOptionPane.showMessageDialog(null, "빈칸을 모두 체우세요.", "경고!", JOptionPane.WARNING_MESSAGE);
					return false;
				}
				break;
		}		
		
		return true;	
		
	}
	
	public void setEnable(boolean isOk){
				
		if(!isOk){			
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
