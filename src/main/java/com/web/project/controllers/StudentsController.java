package com.web.project.controllers;

import com.web.project.dtos.responses.StudentResponse;
import com.web.project.dtos.responses.SubjectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/students")
public class StudentsController {
    private final RestTemplate restTemplate;

    @GetMapping
    public String viewAllStudents(Model model) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> getStudentsEntity = new HttpEntity<>(headers);
        ResponseEntity<List<StudentResponse>> studentsResponse = restTemplate.exchange("http://localhost:8080/api/students", HttpMethod.GET, getStudentsEntity, new ParameterizedTypeReference<>() {});
        List<StudentResponse> students = studentsResponse.getBody();

        model.addAttribute("students", students);

        return "list/list-students";
    }

    @GetMapping("/create")
    public String viewCreateStudent(Model model) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> getSubjectsEntity = new HttpEntity<>(headers);
        ResponseEntity<List<SubjectResponse>> subjectResponses = restTemplate.exchange("http://localhost:8080/api/subjects", HttpMethod.GET, getSubjectsEntity, new ParameterizedTypeReference<>() {});
        List<SubjectResponse> subjects = subjectResponses.getBody();

        model.addAttribute("subjects", subjects);

        return "create/create-student";
    }

    @GetMapping("/update/{id}")
    public String viewUpdateStudent(@PathVariable String id, Model model) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> getStudentEntity = new HttpEntity<>(headers);
        ResponseEntity<StudentResponse> studentResponse = restTemplate.exchange("http://localhost:8080/api/students/" + id, HttpMethod.GET, getStudentEntity, StudentResponse.class);
        StudentResponse student = studentResponse.getBody();

        HttpEntity<String> getSubjectsEntity = new HttpEntity<>(headers);
        ResponseEntity<List<SubjectResponse>> subjectResponses = restTemplate.exchange("http://localhost:8080/api/subjects", HttpMethod.GET, getSubjectsEntity, new ParameterizedTypeReference<>() {});
        List<SubjectResponse> subjects = subjectResponses.getBody();

        model.addAttribute("student", student);
        model.addAttribute("subjects", subjects);

        return "update/update-student";
    }

    @GetMapping("/delete/{id}")
    public String viewDeleteStudent(@PathVariable String id, Model model) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> getStudentEntity = new HttpEntity<>(headers);
        ResponseEntity<StudentResponse> studentResponse = restTemplate.exchange("http://localhost:8080/api/students/" + id, HttpMethod.GET, getStudentEntity, StudentResponse.class);
        StudentResponse student = studentResponse.getBody();

        model.addAttribute("student", student);

        return "delete/delete-student";
    }
}
