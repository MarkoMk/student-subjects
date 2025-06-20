package com.web.project.utils;

import com.web.project.configurations.exceptions.RecordNotFoundException;
import com.web.project.dtos.requests.StudentRequest;
import com.web.project.dtos.requests.SubjectRequest;
import com.web.project.dtos.responses.StudentResponse;
import com.web.project.dtos.responses.SubjectResponse;
import com.web.project.entities.Student;
import com.web.project.entities.Subject;
import com.web.project.repositories.SubjectRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EntityMapper {
    public static StudentResponse mapToStudentResponse(Student student) {
        StudentResponse studentResponse = new StudentResponse();
        studentResponse.setId(student.getId());
        studentResponse.setFirstName(student.getFirstName());
        studentResponse.setLastName(student.getLastName());
        studentResponse.setIndex(student.getIndex());

        if (student.getSubjects() != null && !student.getSubjects().isEmpty()) {
            studentResponse.setSubjects(student.getSubjects().stream().map(EntityMapper::mapToSubjectResponse).toList());
        } else {
            studentResponse.setSubjects(Collections.emptyList());
        }

        return studentResponse;
    }

    public static Student mapToStudent(StudentRequest studentRequest, SubjectRepository subjectRepository) {
        Student student = new Student();
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
        }

        return student;
    }

    public static SubjectResponse mapToSubjectResponse(Subject subject) {
        SubjectResponse subjectResponse = new SubjectResponse();
        subjectResponse.setId(subject.getId());
        subjectResponse.setSubjectName(subject.getSubjectName());
        subjectResponse.setSubjectCode(subject.getSubjectCode());

        return subjectResponse;
    }

    public static Subject mapToSubject(SubjectRequest subjectRequest) {
        Subject subject = new Subject();
        subject.setSubjectName(subjectRequest.getSubjectName());
        subject.setSubjectCode(subjectRequest.getSubjectCode());

        return subject;
    }
}
