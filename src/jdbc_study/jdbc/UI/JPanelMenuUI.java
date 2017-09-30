package jdbc_study.jdbc.UI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.plaf.synth.SynthSplitPaneUI;

import jdbc_study.jdbc.dao.DepartmentDao;
import jdbc_study.jdbc.dao.EmployeeDao;
import jdbc_study.jdbc.dto.Employee;

public class JPanelMenuUI extends JPanel implements ActionListener {
	private JMenuItem mntmEmpAdd;
	private JMenuItem menuEmpUpdate;
	private JMenuItem menuEmpDel;
	private JMenuItem menuEmpSearch;
	private JMenuItem menuDeptAdd;
	private JMenuItem menuDeptUpdate;
	private JMenuItem menuDeptDel;
	private JMenuItem menuDeptSearch;
	private JPanelDept deptView;
	private JPanelEmp empView;
	private DepartmentDao deptDao = DepartmentDao.getInstance();
	private EmployeeDao empDao = EmployeeDao.getInstance();

	public JPanelMenuUI() {

		setLayout(new BorderLayout(0, 0));

		JMenuBar menuBar = new JMenuBar();
		add(menuBar, BorderLayout.NORTH);

		JMenu empMenu = new JMenu("사원 관리");
		menuBar.add(empMenu);

		mntmEmpAdd = new JMenuItem("추가");
		mntmEmpAdd.addActionListener(this);
		empMenu.add(mntmEmpAdd);

		menuEmpUpdate = new JMenuItem("수정");
		menuEmpUpdate.addActionListener(this);
		empMenu.add(menuEmpUpdate);

		menuEmpDel = new JMenuItem("삭제");
		menuEmpDel.addActionListener(this);
		empMenu.add(menuEmpDel);

		menuEmpSearch = new JMenuItem("검색");
		menuEmpSearch.addActionListener(this);
		empMenu.add(menuEmpSearch);

		JMenu detpMenu = new JMenu("부서 관리");
		menuBar.add(detpMenu);

		menuDeptAdd = new JMenuItem("추가");
		menuDeptAdd.addActionListener(this);
		detpMenu.add(menuDeptAdd);

		menuDeptUpdate = new JMenuItem("수정");
		menuDeptUpdate.addActionListener(this);
		detpMenu.add(menuDeptUpdate);

		menuDeptDel = new JMenuItem("삭제");
		menuDeptDel.addActionListener(this);
		detpMenu.add(menuDeptDel);

		menuDeptSearch = new JMenuItem("검색");
		menuDeptSearch.addActionListener(this);
		detpMenu.add(menuDeptSearch);
	}

	private ActionListener deptItemAction = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand() == "수정") {
				deptView.setDept(deptDao.selectDepartmentByNo(deptView.getDept(false)));
			}
		}
	};

	private void createEmpView() {
		empView = new JPanelEmp();
		empView.setEmpDao(empDao);
		empView.setDeptDao(deptDao);
		add(empView, BorderLayout.CENTER);
		empView.setVisible(true);
		JButton btnEmpAdd = empView.getBtn();
		JButton btnEmpCancle = empView.getBtnCancle();
		btnEmpAdd.addActionListener(btnEmpAddAction);
		btnEmpCancle.addActionListener(btnCancleAction);
	}

	private void creatDeptView() {
		deptView = new JPanelDept();
		deptView.setDeptDao(deptDao);
		add(deptView, BorderLayout.CENTER);
		deptView.setVisible(true);
		JButton btnDeptAdd = deptView.getBtnAdd();
		JButton btnDeptCancle = deptView.getBtnCancle();
		btnDeptAdd.addActionListener(btnAddAction);
		btnDeptCancle.addActionListener(btnCancleAction);
	}

	private ActionListener btnAddAction = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {

			if (e.getActionCommand() == "추가") {

				if (!deptView.tfConfirm(1)) {
					return;
				}

				deptDao.insertDepartment(deptView.getDept(true));
				reloadDeptPview("추가");
			}
			if (e.getActionCommand() == "삭제") {
				if (!deptView.tfConfirm(2)) {
					return;
				}

				deptDao.deleteDepartment(deptView.getDept(false));
				reloadDeptPview("삭제");
				deptView.setEnable(false);
			}
			if (e.getActionCommand() == "수정") {

				if (!deptView.tfConfirm(1)) {
					return;
				}

				deptDao.updateDepartment(deptView.getDept(true));
				reloadDeptPview("수정");
			}
			if (e.getActionCommand() == "검색") {

				if (!deptView.tfConfirm(2)) {
					return;
				}

				deptView.setDept(deptDao.selectDepartmentByNo(deptView.getDept(false)));
				deptView.setEnable(false);
			}

		}
	};

	private ActionListener btnEmpAddAction = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand() == "추가") {
				if (!empView.tfConfirm(1)) {				
					return;
				}
				
				empDao.insertEmployee(empView.getEmp(true));
				reloadEmpPview("추가");
			}
			if (e.getActionCommand() == "삭제") {
				if (!empView.tfConfirm(2)) {				
					return;
				}
				
				empDao.deletEmployee(empView.getEmp(false));
				reloadEmpPview("삭제");
			}
			if (e.getActionCommand() == "수정") {
				if (!empView.tfConfirm(1)) {				
					return;
				}
				
				empDao.updateEmployee(empView.getEmp(true));
				reloadEmpPview("수정");
			}
			if (e.getActionCommand() == "검색") {
				if (!empView.tfConfirm(2)) {				
					return;
				}
				
				Employee emp = empDao.selectEmployeeByNo(empView.getEmp(false));
				empView.setEmp(emp);
			}

		}
	};

	private void reloadDeptPview(String type) {
		if (deptView != null) {
			remove(deptView);
		}
		if (empView != null) {
			remove(empView);
		}

		creatDeptView();
		deptView.setBtnAdd(type);
		revalidate();
	}

	private void reloadEmpPview(String type) {
		if (empView != null) {
			remove(empView);
		}
		if (deptView != null) {
			remove(deptView);
		}

		createEmpView();
		empView.setBtnName(type);
		revalidate();
	}

	private ActionListener btnCancleAction = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (deptView != null) {
				deptView.setVisible(false);
			}

			if (empView != null) {
				empView.setVisible(false);
			}

			revalidate();
		}
	};

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mntmEmpAdd) {
			mntmEmpAddActionPerformed(e);
		}
		if (e.getSource() == menuEmpUpdate) {
			menuEmpUpdateActionPerformed(e);
		}
		if (e.getSource() == menuEmpSearch) {
			menuEmpSearchActionPerformed(e);
		}
		if (e.getSource() == menuEmpDel) {
			menuEmpDelActionPerformed(e);
		}
		if (e.getSource() == menuDeptAdd) {
			menuDeptAddActionPerformed(e);
		}
		if (e.getSource() == menuDeptDel) {
			menuDeptDelActionPerformed(e);
		}
		if (e.getSource() == menuDeptSearch) {
			menuDeptSearchActionPerformed(e);
		}
		if (e.getSource() == menuDeptUpdate) {
			menuDeptUpdateActionPerformed(e);
		}
	}

	protected void menuDeptUpdateActionPerformed(ActionEvent e) {

		reloadDeptPview("수정");
		deptView.setEnable(true);
		revalidate();
	}

	protected void menuDeptSearchActionPerformed(ActionEvent e) {
		reloadDeptPview("검색");
		deptView.setEnable(false);
		revalidate();
	}

	protected void menuDeptDelActionPerformed(ActionEvent e) {

		reloadDeptPview("삭제");
		deptView.setEnable(false);
		revalidate();
	}

	protected void menuDeptAddActionPerformed(ActionEvent e) {

		reloadDeptPview("추가");
		deptView.setEnable(true);
		revalidate();
	}

	protected void menuEmpDelActionPerformed(ActionEvent e) {
		reloadEmpPview("삭제");
		empView.setEnable(false);
		revalidate();
	}

	protected void menuEmpSearchActionPerformed(ActionEvent e) {
		reloadEmpPview("검색");
		empView.setEnable(false);
		revalidate();
	}

	protected void menuEmpUpdateActionPerformed(ActionEvent e) {
		reloadEmpPview("수정");
		empView.setEnable(true);
		revalidate();
	}

	protected void mntmEmpAddActionPerformed(ActionEvent e) {
		reloadEmpPview("추가");
		empView.setEnable(true);
		revalidate();
	}
}
