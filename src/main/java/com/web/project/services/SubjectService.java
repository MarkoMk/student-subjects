package com.web.project.services;

import com.web.project.dtos.requests.SubjectRequest;
import com.web.project.dtos.responses.SubjectResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SubjectService {
    SubjectResponse retrieveSubjectById(String id);

    List<SubjectResponse> listAllSubjects();

    SubjectResponse createSubject(SubjectRequest subjectRequest);

    SubjectResponse updateSubject(String id, SubjectRequest subjectRequest);

    ResponseEntity<Void> deleteSubject(String id);
}
