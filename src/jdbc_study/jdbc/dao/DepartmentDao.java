package jdbc_study.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import javafx.scene.DepthTest;
import jdbc_study.jdbc.DBCon;
import jdbc_study.jdbc.JdbcUtil;
import jdbc_study.jdbc.dto.Department;

public class DepartmentDao {
	private static final DepartmentDao instance = new DepartmentDao();

	private String sql;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private Connection con;

	private DepartmentDao() {
	}

	public static DepartmentDao getInstance() {
		return instance;
	}

	public List<Department> selectDepartmentByAll() {
		List<Department> lists = new ArrayList<>();
		sql = "select deptno, deptname, floor from department";
		con = DBCon.getInstance().getConn();
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int deptNo = rs.getInt("deptno");
				String deptname = rs.getString("deptname");
				int floor = rs.getInt("floor");
				lists.add(new Department(deptNo, deptname, floor));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return lists;
	}

	public Department selectDepartmentByNo(Department dept) {
		
		try {
			String sql = "select * from department where deptno = ?";
			
			pstmt = DBCon.getInstance().getConn().prepareStatement(sql);
			pstmt.setInt(1, dept.getDeptNo());
			Department department = null;
			ResultSet res = pstmt.executeQuery();
			while(res.next()){
				int deptNo = res.getInt(1);
				String deptname = res.getString(2);
				int floor =  res.getInt(3);
				department = new Department(deptNo, deptname, floor);
			};			

			
			return department;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;		
	}

	public void insertDepartment(Department dept) {
		String sql = "insert into department values(?,?,?)";
		try {
			pstmt = DBCon.getInstance().getConn().prepareStatement(sql);
			pstmt.setInt(1, dept.getDeptNo());
			pstmt.setString(2, dept.getDeptname());
			pstmt.setInt(3, dept.getFloor());	
			
			int res = pstmt.executeUpdate();
			if(res<0){
				System.out.println("삽입 실패");
				return;
			}
			JOptionPane.showMessageDialog(null, dept + "추가하였습니다.");

		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JdbcUtil.close(pstmt);
		}
	}

	public void updateDepartment(Department dept) {
		String sql = "update department set deptname = ? , floor = ? where deptno = ?";
		try {
			pstmt = DBCon.getInstance().getConn().prepareStatement(sql);
			pstmt.setString(1, dept.getDeptname());
			pstmt.setInt(2, dept.getFloor());
			pstmt.setInt(3,dept.getDeptNo());
			
		    int res = pstmt.executeUpdate();
		    if(res < 0){
		    	System.out.println("업데이트 실패");
		    }
		    JOptionPane.showMessageDialog(null, dept + "수정하였습니다.");
		} catch (SQLException e) {		
			e.printStackTrace();
		}finally {
			JdbcUtil.close(pstmt);
		}
	}

	public void deleteDepartment(Department dept) {
		String sql = "delete from department where deptno = ?";
		try{
			pstmt = DBCon.getInstance().getConn().prepareStatement(sql);
			pstmt.setInt(1, dept.getDeptNo());
			
			int res = pstmt.executeUpdate();
			if(res < 0){
				System.out.println("삭제 실패");
			}
			JOptionPane.showMessageDialog(null, dept + "삭제 하였습니다.");
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			JdbcUtil.close(pstmt);
		}
		
	}
}
