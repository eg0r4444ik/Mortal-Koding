package ru.vsu.rogachev.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import ru.vsu.rogachev.dto.enums.InfoType;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GameInfoDTO {

    private List<List<Long>> points;
    private List<String> tasksUrls;
    private InfoType type;
    private List<String> handles;

}
