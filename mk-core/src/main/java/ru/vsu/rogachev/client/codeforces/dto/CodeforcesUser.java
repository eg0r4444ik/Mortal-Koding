package ru.vsu.rogachev.client.codeforces.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CodeforcesUser {

    // Хэндл пользователя Codeforces
    @NotNull
    @JsonProperty("handle")
    private String handle;

    // Email пользователя
    // Показывается только когда пользователь дал согласие на отображение своей контактной информации
    @Nullable
    @JsonProperty("email")
    private String email;

    // Рейтинг пользователя
    @NotNull
    @JsonProperty("rating")
    private Long rating;

}
