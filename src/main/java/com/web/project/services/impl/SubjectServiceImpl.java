package com.web.project.services.impl;

import com.web.project.configurations.exceptions.RecordNotFoundException;
import com.web.project.dtos.requests.SubjectRequest;
import com.web.project.dtos.responses.SubjectResponse;
import com.web.project.entities.Subject;
import com.web.project.repositories.SubjectRepository;
import com.web.project.services.SubjectService;
import com.web.project.utils.EntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;

    @Override
    public SubjectResponse retrieveSubjectById(String id) {
        Subject subject = subjectRepository.findById(id).orElseThrow(
            () -> new RecordNotFoundException("subject not found: " + id)
        );

        return EntityMapper.mapToSubjectResponse(subject);
    }

    @Override
    public List<SubjectResponse> listAllSubjects() {
        return subjectRepository.findAll().stream().map(EntityMapper::mapToSubjectResponse).toList();
    }

    @Override
    @Transactional
    public SubjectResponse createSubject(SubjectRequest subjectRequest) {
        return EntityMapper.mapToSubjectResponse(subjectRepository.save(EntityMapper.mapToSubject(subjectRequest)));
    }

    @Override
    @Transactional
    public SubjectResponse updateSubject(String id, SubjectRequest subjectRequest) {
        Subject subject = subjectRepository.findById(id).orElseThrow(
            () -> new RecordNotFoundException("subject not found: " + id)
        );

        subject.setSubjectName(subjectRequest.getSubjectName());
        subject.setSubjectCode(subjectRequest.getSubjectCode());
        subjectRepository.save(subject);

        return EntityMapper.mapToSubjectResponse(subject);
    }

    @Override
    @Transactional
    public ResponseEntity<Void> deleteSubject(String id) {
        Subject subject = subjectRepository.findById(id).orElseThrow(
            () -> new RecordNotFoundException("subject not found: " + id)
        );

        subjectRepository.delete(subject);

        return ResponseEntity.ok().build();
    }
}
