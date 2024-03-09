package ru.vsu.rogachev.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Problem implements Comparable<Problem>{

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
    public int compareTo(Problem o) {
        return Long.compare(this.rating, o.rating);
    }

}
