package jdbc_study.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import jdbc_study.jdbc.DBCon;
import jdbc_study.jdbc.JdbcUtil;
import jdbc_study.jdbc.dto.Employee;

public class EmployeeDao {
	private static final EmployeeDao instance = new EmployeeDao();
	
	private String sql;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private Connection con;
	public EmployeeDao() {
	}
	
	public static EmployeeDao getInstance() {
		return instance;
	}
	
	public List<Employee> selectEmployeeByAll(){
		List<Employee> lists = new ArrayList<>();
		sql = "select * from employee";
		con = DBCon.getInstance().getConn();
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				int empno = rs.getInt("empno");
				String empname = rs.getString("empname");
				String title = rs.getString("title");
				int manager = rs.getInt("manager");
				int salary = rs.getInt("salary");
				int dno = rs.getInt("dno");
				
				lists.add(new Employee(empno, empname, title, manager, salary, dno));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JdbcUtil.close(rs);
		}
		return lists;
	}
	
	
	public void insertEmployee(Employee emp){
		sql = "insert into employee value( ? , ? , ? , ? , ?, ?)";
		con = DBCon.getInstance().getConn();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, emp.getEmpno());
			pstmt.setString(2, emp.getEmpname());
			pstmt.setString(3, emp.getTitle());
			pstmt.setInt(4, emp.getManager());
			pstmt.setInt(5, emp.getSalary());
			pstmt.setInt(6, emp.getDno());
			
			int res = pstmt.executeUpdate();
			
			if(res < 0){
				System.out.println("입력 실패");
			}
			JOptionPane.showMessageDialog(null, emp + "를 입력 하였습니다.");			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JdbcUtil.close(rs);
		}
	}
	
	public void updateEmployee(Employee emp){
		sql = "update employee set title = ? ,salary = ? where empno = ?";
		con = DBCon.getInstance().getConn();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,emp.getTitle());
			pstmt.setInt(2, emp.getSalary());
			pstmt.setInt(3,emp.getEmpno());
			
			int res = pstmt.executeUpdate();
			if(res < 0){
				System.out.println("수정 실패");
			}
			JOptionPane.showMessageDialog(null, emp + "를 수정 하였습니다.");			

		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JdbcUtil.close(rs);
		}
	}
	
	public Employee selectEmployeeByNo(Employee emp){
		Employee employee = null;
		sql = "select * from employee";
		con = DBCon.getInstance().getConn();
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				int empno = rs.getInt("empno");
				String empname = rs.getString("empname");
				String title = rs.getString("title");
				int manager = rs.getInt("manager");
				int salary = rs.getInt("salary");
				int dno = rs.getInt("dno");
				
				employee = new Employee(empno, empname, title, manager, salary, dno);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			JdbcUtil.close(rs);
		}
		return employee;
	}
	
	public void deletEmployee(Employee emp){
		sql = "delete from employee where empno = ?";
		con = DBCon.getInstance().getConn();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, emp.getEmpno());
			int res = pstmt.executeUpdate();
						
			if(res<0){
				System.out.println("삭제 실패");
			}
			JOptionPane.showMessageDialog(null, emp + "를 삭제 하였습니다.");				
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			JdbcUtil.close(rs);
		}
	}
	
	
}
