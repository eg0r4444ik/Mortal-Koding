package ru.vsu.rogachev.client.mk.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckCodeRequest {

    @NotNull
    @JsonProperty("email")
    private String email;

    @NotNull
    @JsonProperty("code")
    private String code;

}
