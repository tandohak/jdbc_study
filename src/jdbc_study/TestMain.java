package jdbc_study;

import java.util.List;

import jdbc_study.jdbc.dao.DepartmentDao;
import jdbc_study.jdbc.dto.Department;

public class TestMain {

	public static void main(String[] args) {
		/*DBCon dbCon = new DBCon();
		System.out.println(dbCon);
		System.out.println(dbCon.getConn());
		dbCon.connClose();
		
		DBCon dbCon1 = new DBCon();
		System.out.println(dbCon1);
		System.out.println(dbCon1.getConn());*/
		
		/*싱글턴 패턴
		 *위 방식으로 하면 해쉬코드가 각각 다르다
		 *그러므로 dbCon을 생성할때마다 오버헤드가 많이 발생한다.
		 *이를 방지하기 위해 싱글턴 패턴을 사용한다.
		 *싱글턴 패턴은 클래스내에서 미리 connection을 생성한 후 스테틱 선언을한다.
		 *
		 * */		
		/*DBCon dbCon = DBCon.getInstance();
		System.out.println(dbCon);
		System.out.println(dbCon.getConn());
		dbCon.connClose();
		*/
		DepartmentDao dao = DepartmentDao.getInstance();
		
		showDepartmentList(dao);
		
		//insert
		Department dept = new Department(6, "마케팅", 10);
		dao.insertDepartment(dept);
		showDepartmentList(dao);
		System.out.println("==============================");
		//update
		dao.updateDepartment(updateDept(6,"인사",11));
		showDepartmentList(dao);
		System.out.println("==============================");
		//delete
		dao.deleteDepartment(getDept(6));
		showDepartmentList(dao);
		System.out.println("==============================");
		//select where
		
		System.out.println(dao.selectDepartmentByNo(getDept(3)));
	}

	private static Department updateDept(int deptNo,String deptname, int floor) {
		Department updateDept = new Department(deptNo, deptname, floor);
		return updateDept;
	}

	private static Department getDept(int deptNo) {
		Department getDept = new Department(deptNo);
		return getDept;
	}

	private static void showDepartmentList(DepartmentDao dao) {
		List<Department> lists = dao.selectDepartmentByAll();
		for(Department dept : lists){
			System.out.println(dept);
		}
	}

}
