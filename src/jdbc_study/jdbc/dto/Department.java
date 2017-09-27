package jdbc_study.jdbc.dto;

public class Department {
	private int deptNo;
	private String deptname;
	private int floor;
	
	public Department(int deptNo) {
		this.deptNo = deptNo;
	}

	public Department(int deptNo, String deptname, int floor) {
		this.deptNo = deptNo;
		this.deptname = deptname;
		this.floor = floor;
	}

	public int getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(int deptNo) {
		this.deptNo = deptNo;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	@Override
	public String toString() {
		return String.format("Department [deptNo=%s, deptname=%s, floor=%s]", deptNo, deptname, floor);
	}	
}
