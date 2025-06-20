package com.web.project.controllers.rest;

import com.web.project.dtos.requests.SubjectRequest;
import com.web.project.dtos.responses.SubjectResponse;
import com.web.project.services.SubjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/subjects")
public class SubjectRestController {
    private final SubjectService subjectService;

    @GetMapping("/{id}")
    public SubjectResponse retrieveSubjectById(@PathVariable String id) {
        return subjectService.retrieveSubjectById(id);
    }

    @GetMapping
    public List<SubjectResponse> listAllSubjects() {
        return subjectService.listAllSubjects();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SubjectResponse createSubject(@Valid @RequestBody SubjectRequest subjectRequest) {
        return subjectService.createSubject(subjectRequest);
    }

    @PutMapping("/{id}")
    public SubjectResponse updateSubject(@PathVariable String id, @Valid @RequestBody SubjectRequest subjectRequest) {
        return subjectService.updateSubject(id, subjectRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable String id) {
        return subjectService.deleteSubject(id);
    }
}
