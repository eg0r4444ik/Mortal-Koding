package ru.vsu.rogachev.client.codeforces.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CodeforcesResponseContainer<T> {

    @JsonProperty("status")
    private String status;

    @JsonProperty("result")
    private T result;

}
