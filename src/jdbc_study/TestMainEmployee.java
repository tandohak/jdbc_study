package jdbc_study;

import java.util.List;

import jdbc_study.jdbc.dao.EmployeeDao;
import jdbc_study.jdbc.dto.Employee;

public class TestMainEmployee {

	public static void main(String[] args) {
		EmployeeDao emp = EmployeeDao.getInstance();
		
		showEmployee(emp);
		System.out.println("==================");
		
		emp.insertEmployee(new Employee(7777, "서현진", "대리", 4377, 25000000, 2));

		showEmployee(emp);
		System.out.println("==================");
		
		emp.updateEmployee(new Employee(7777, "서현진", "과장", 4377, 35000000, 2));
	
		System.out.println(emp.selectEmployeeByNo(getEmp(7777)));
		System.out.println("==================");
		
		emp.deletEmployee(getEmp(7777));
	}

	private static Employee getEmp(int empno) {
		return new Employee(empno);
	}

	private static void showEmployee(EmployeeDao emp) {
		List<Employee> lists = emp.selectEmployeeByAll();
		for(Employee e : lists){
			System.out.println(e);
		}
	}

}
