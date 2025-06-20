package com.web.project.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SubjectRequest {
    @NotBlank
    private String subjectName;

    @NotBlank
    private String subjectCode;
}
