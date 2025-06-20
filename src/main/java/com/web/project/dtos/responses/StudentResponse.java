package com.web.project.dtos.responses;

import lombok.Data;
import java.util.List;

@Data
public class StudentResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String index;
    private List<SubjectResponse> subjects;
}
