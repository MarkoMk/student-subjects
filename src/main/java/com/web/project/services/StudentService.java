package com.web.project.services;

import com.web.project.dtos.requests.StudentRequest;
import com.web.project.dtos.responses.StudentResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface StudentService {
    StudentResponse retrieveStudentById(String id);

    List<StudentResponse> listAllStudents();

    StudentResponse createStudent(StudentRequest studentRequest);

    StudentResponse updateStudent(String id, StudentRequest studentRequest);

    ResponseEntity<Void> deleteStudent(String id);
}
