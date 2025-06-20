package com.web.project.entities;

import com.web.project.entities.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "subject", indexes = {
    @Index(name = "idx_subject_subject_name", columnList = "subject_name"),
    @Index(name = "idx_subject_subject_code", columnList = "subject_code")
})
public class Subject extends BaseEntity {
    @Column(name = "subject_name", nullable = false)
    private String subjectName;

    @Column(name = "subject_code", nullable = false, unique = true)
    private String subjectCode;
}
