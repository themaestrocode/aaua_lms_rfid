package com.themaestrocode.aaualms.service;

import com.themaestrocode.aaualms.entity.Department;
import com.themaestrocode.aaualms.repository.DepartmentRepository;
import javafx.collections.ObservableList;

public class DepartmentService {

    public ObservableList<String> getFacultiesUnderSelectedDepartment(String facultyName) {
        DepartmentRepository departmentRepository = new DepartmentRepository();
        return departmentRepository.getFacultiesUnderSelectedDepartment(facultyName);
    }

    public Department getDepartmentById(int departmentId) {
        DepartmentRepository departmentRepository = new DepartmentRepository();
        return departmentRepository.getDepartmentById(departmentId);
    }
}
