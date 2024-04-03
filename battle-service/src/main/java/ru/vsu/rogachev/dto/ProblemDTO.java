package ru.vsu.rogachev.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProblemDTO implements Comparable<ProblemDTO>{

    public enum Type{
        PROGRAMMING,
        QUESTION;
    }

    private long contestId;
    private String index, name;
    private Type type;
    private long rating;
    private List<String> tags;

    @Override
    public int compareTo(ProblemDTO o) {
        return Long.compare(this.rating, o.rating);
    }

}
