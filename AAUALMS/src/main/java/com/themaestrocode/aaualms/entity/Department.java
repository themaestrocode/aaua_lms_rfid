package com.themaestrocode.aaualms.entity;

public class Department {

    private int departmentId;
    private String departmentName;
    private Faculty faculty;

    public Department(int departmentId, String departmentName, Faculty faculty) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.faculty = faculty;
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

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }
}
