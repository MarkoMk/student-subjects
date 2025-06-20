package com.web.project.services.impl;

import com.web.project.configurations.exceptions.RecordNotFoundException;
import com.web.project.dtos.requests.StudentRequest;
import com.web.project.dtos.responses.StudentResponse;
import com.web.project.entities.Student;
import com.web.project.entities.Subject;
import com.web.project.repositories.StudentRepository;
import com.web.project.repositories.SubjectRepository;
import com.web.project.services.StudentService;
import com.web.project.utils.EntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

    @Override
    public StudentResponse retrieveStudentById(String id) {
        Student student = studentRepository.findById(id).orElseThrow(
            () -> new RecordNotFoundException("student not found: " + id)
        );

        return EntityMapper.mapToStudentResponse(student);
    }

    @Override
    public List<StudentResponse> listAllStudents() {
        return studentRepository.findAll().stream().map(EntityMapper::mapToStudentResponse).toList();
    }

    @Override
    @Transactional
    public StudentResponse createStudent(StudentRequest studentRequest) {
        return EntityMapper.mapToStudentResponse(studentRepository.save(EntityMapper.mapToStudent(studentRequest, subjectRepository)));
    }

    @Override
    @Transactional
    public StudentResponse updateStudent(String id, StudentRequest studentRequest) {
        Student student = studentRepository.findById(id).orElseThrow(
            () -> new RecordNotFoundException("student not found: " + id)
        );

        student.setFirstName(studentRequest.getFirstName());
        student.setLastName(studentRequest.getLastName());
        student.setIndex(studentRequest.getIndex());

        if (studentRequest.getSubjectIds() != null && !studentRequest.getSubjectIds().isEmpty()) {
            List<Subject> subjects = subjectRepository.findAllById(studentRequest.getSubjectIds());
            if (!subjects.isEmpty() && subjects.size() == studentRequest.getSubjectIds().size()) {
                student.setSubjects(subjects);
            } else {
                throw new RecordNotFoundException("invalid subject ids");
            }
        } else if (studentRequest.getSubjectIds() != null) {
            student.setSubjects(null);
        }
        studentRepository.save(student);

        return EntityMapper.mapToStudentResponse(student);
    }

    @Override
    @Transactional
    public ResponseEntity<Void> deleteStudent(String id) {
        Student student = studentRepository.findById(id).orElseThrow(
            () -> new RecordNotFoundException("student not found: " + id)
        );

        studentRepository.delete(student);

        return ResponseEntity.ok().build();
    }
}
