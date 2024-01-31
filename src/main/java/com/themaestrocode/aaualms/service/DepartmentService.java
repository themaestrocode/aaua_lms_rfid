package com.themaestrocode.aaualms.service;

import com.themaestrocode.aaualms.entity.Department;
import com.themaestrocode.aaualms.repository.DepartmentRepository;
import javafx.collections.ObservableList;

public class DepartmentService {

    public ObservableList<String> getDepartmentsUnderSelectedFaculty(String facultyName) {
        DepartmentRepository departmentRepository = new DepartmentRepository();
        return departmentRepository.getDepartmentsUnderSelectedFaculty(facultyName);
    }

    public Department getDepartmentById(int departmentId) {
        DepartmentRepository departmentRepository = new DepartmentRepository();
        return departmentRepository.getDepartmentById(departmentId);
    }
}
