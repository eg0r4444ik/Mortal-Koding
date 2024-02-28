package ru.vsu.rogachev.kafka;

import lombok.*;
import ru.vsu.rogachev.kafka.enums.Table;
import ru.vsu.rogachev.kafka.enums.Operation;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JpaKafkaRequest {

    private Operation operation;
    private Table table;
    private String fieldName;

}
