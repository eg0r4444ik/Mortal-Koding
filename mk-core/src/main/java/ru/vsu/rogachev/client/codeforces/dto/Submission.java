package ru.vsu.rogachev.client.codeforces.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.vsu.rogachev.client.codeforces.dto.enums.SubmissionVerdict;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Submission {

    // Время создания попытки в формате unix
    @NotNull
    @JsonProperty("creationTimeSeconds")
    private Long creationTimeSeconds;

    // Задача
    @NotNull
    @JsonProperty("problem")
    private Problems.Problem problem;

    // Язык программирования на котором отправлялась попытка
    @NotNull
    @JsonProperty("programmingLanguage")
    private String programmingLanguage;

    // Вердикт попытки
    @Nullable
    @JsonProperty("verdict")
    private SubmissionVerdict verdict;

}
