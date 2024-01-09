package com.themaestrocode.aaualms.service;

import com.themaestrocode.aaualms.entity.Faculty;
import com.themaestrocode.aaualms.repository.FacultyRepository;
import javafx.collections.ObservableList;

public class FacultyService {

    public ObservableList<String> getAllFaculty() {
        FacultyRepository facultyRepository = new FacultyRepository();
        return facultyRepository.getAllFaculty();
    }

    public Faculty getFacultyByDepartmentId(int departmentId) {
        FacultyService facultyService = new FacultyService();
        return facultyService.getFacultyByDepartmentId(departmentId);
    }
}
