package ru.vsu.rogachev.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CommandHandlerButtons {

    private final List<String> buttonText;

    private final List<String> buttonCallbackData;

}
