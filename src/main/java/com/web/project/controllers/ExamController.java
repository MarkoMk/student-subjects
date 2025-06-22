package com.web.project.controllers;

import ch.qos.logback.core.model.Model;
import com.web.project.dtos.responses.SubjectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/exams")
public class ExamController {
    private final RestTemplate restTemplate;

    @GetMapping("/create")
    public String createExam(Model model) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> getExamsEntity = new HttpEntity<>(headers);
        ResponseEntity<List<ExamResponse>> examResponses = restTemplate.exchange("http://localhost:8080/api/exams", HttpMethod.GET, getExamsEntity, new ParameterizedTypeReference<>() {
        });
        List<ExamResponse> exams = examResponses.getBody();

        model.addAtribute("exams",exams);

        return "create/create-exams";
    }
}