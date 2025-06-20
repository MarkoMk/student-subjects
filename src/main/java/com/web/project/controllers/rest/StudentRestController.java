package com.web.project.controllers.rest;

import com.web.project.dtos.requests.StudentRequest;
import com.web.project.dtos.responses.StudentResponse;
import com.web.project.services.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/students")
public class StudentRestController {
    private final StudentService studentService;

    @GetMapping("/{id}")
    public StudentResponse retrieveStudentById(@PathVariable String id) {
        return studentService.retrieveStudentById(id);
    }

    @GetMapping
    public List<StudentResponse> listAllStudents() {
        return studentService.listAllStudents();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentResponse createStudent(@Valid @RequestBody StudentRequest studentRequest) {
        return studentService.createStudent(studentRequest);
    }

    @PutMapping("/{id}")
    public StudentResponse updateStudent(@PathVariable String id, @Valid @RequestBody StudentRequest studentRequest) {
        return studentService.updateStudent(id, studentRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable String id) {
        return studentService.deleteStudent(id);
    }
}
