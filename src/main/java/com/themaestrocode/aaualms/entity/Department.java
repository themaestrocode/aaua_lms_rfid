package com.themaestrocode.aaualms.entity;

public class Department {

    private int departmentId;
    private String departmentName;
    private int faculty_id;

    public Department(int departmentId, String departmentName, int faculty_id) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.faculty_id = faculty_id;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getFaculty_id() {
        return faculty_id;
    }

    public void setFaculty_id(int faculty_id) {
        this.faculty_id = faculty_id;
    }
}
