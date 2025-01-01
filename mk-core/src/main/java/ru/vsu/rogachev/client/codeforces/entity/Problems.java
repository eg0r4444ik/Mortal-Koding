package ru.vsu.rogachev.client.codeforces.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.vsu.rogachev.client.codeforces.entity.enums.ProblemType;

import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Problems {

    @NotNull
    @JsonProperty("problems")
    private List<Problem> problems;

    @Data
    @Accessors(chain = true)
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Problem implements Comparable<Problem> {
        // Id соревнования, содержащего задачу
        @Nullable
        @JsonProperty("contestId")
        private Long contestId;

        // Обычно буква или буква с цифрой, обозначающие индекс задачи в соревновании
        @NotNull
        @JsonProperty("index")
        private String index;

        // Название задачи
        @NotNull
        @JsonProperty("name")
        private String name;

        // Тип задачи
        @NotNull
        @JsonProperty("type")
        private ProblemType type;

        // Рейтинг задачи (сложность)
        @Nullable
        @JsonProperty("rating")
        private Long rating;

        // Теги задачи
        @NotNull
        @JsonProperty("tags")
        private List<String> tags;

        @Override
        public int compareTo(Problem o) {
            return Long.compare(this.rating, o.rating);
        }
    }

}
