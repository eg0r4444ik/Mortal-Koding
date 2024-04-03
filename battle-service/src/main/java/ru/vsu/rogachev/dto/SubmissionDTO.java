package ru.vsu.rogachev.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubmissionDTO {

    public enum Verdict {
        FAILED ("FAILED"),
        OK ("OK"),
        PARTIAL ("PARTIAL"),
        COMPILATION_ERROR ("COMPILATION_ERROR"),
        RUNTIME_ERROR ("RUNTIME_ERROR"),
        WRONG_ANSWER ("WRONG_ANSWER"),
        PRESENTATION_ERROR ("PRESENTATION_ERROR"),
        TIME_LIMIT_EXCEEDED ("TIME_LIMIT_EXCEEDED"),
        MEMORY_LIMIT_EXCEEDED ("MEMORY_LIMIT_EXCEEDED"),
        IDLENESS_LIMIT_EXCEEDED ("IDLENESS_LIMIT_EXCEEDED"),
        SECURITY_VIOLATED ("SECURITY_VIOLATED"),
        CRASHED ("CRASHED"),
        INPUT_PREPARATION_CRASHED ("INPUT_PREPARATION_CRASHED"),
        CHALLENGED ("CHALLENGED"),
        SKIPPED ("SKIPPED"),
        TESTING ("TESTING"),
        REJECTED ("REJECTED");

        Verdict(String s) {
        }
    }

    private long creationTimeSeconds;
    private ProblemDTO problem;
    private String programmingLanguage;
    private Verdict verdict;
}
