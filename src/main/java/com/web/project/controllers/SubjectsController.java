package com.web.project.controllers;

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
@RequestMapping("/subjects")
public class SubjectsController {
    private final RestTemplate restTemplate;

    @GetMapping
    public String viewAllSubjects(Model model) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> getSubjectsEntity = new HttpEntity<>(headers);
        ResponseEntity<List<SubjectResponse>> subjectResponses = restTemplate.exchange("http://localhost:8080/api/subjects", HttpMethod.GET, getSubjectsEntity, new ParameterizedTypeReference<>() {});
        List<SubjectResponse> subjects = subjectResponses.getBody();

        model.addAttribute("subjects", subjects);

        return "list/list-subjects";
    }

    @GetMapping("/create")
    public String viewCreateSubject() {
        return "create/create-subject";
    }

    @GetMapping("/update/{id}")
    public String viewUpdateSubject(@PathVariable String id, Model model) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> getSubjectEntity = new HttpEntity<>(headers);
        ResponseEntity<SubjectResponse> subjectResponse = restTemplate.exchange("http://localhost:8080/api/subjects/" + id, HttpMethod.GET, getSubjectEntity, SubjectResponse.class);
        SubjectResponse subject = subjectResponse.getBody();

        model.addAttribute("subject", subject);

        return "update/update-subject";
    }

    @GetMapping("/delete/{id}")
    public String viewDeleteSubject(@PathVariable String id, Model model) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> getSubjectEntity = new HttpEntity<>(headers);
        ResponseEntity<SubjectResponse> subjectResponse = restTemplate.exchange("http://localhost:8080/api/subjects/" + id, HttpMethod.GET, getSubjectEntity, SubjectResponse.class);
        SubjectResponse subject = subjectResponse.getBody();

        model.addAttribute("subject", subject);

        return "delete/delete-subject";
    }
}
