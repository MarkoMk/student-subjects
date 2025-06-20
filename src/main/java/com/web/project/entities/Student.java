package com.web.project.entities;

import com.web.project.entities.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "student", indexes = {
    @Index(name = "idx_student_first_name", columnList = "first_name"),
    @Index(name = "idx_student_last_code", columnList = "last_name"),
    @Index(name = "idx_student_index", columnList = "index")
})
public class Student extends BaseEntity {
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "index", nullable = false, unique = true)
    private String index;

    @ManyToMany
    @JoinTable(name = "student_subject",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "subject_id"),
        indexes = {
            @Index(name = "idx_student_subject_student_id", columnList = "student_id"),
            @Index(name = "idx_student_subject_subject_id", columnList = "subject_id")
        })
    private List<Subject> subjects;
}
