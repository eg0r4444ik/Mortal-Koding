package ru.vsu.rogachev.config;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Data
@Validated
@Component
@Accessors(chain = true)
@ConfigurationProperties(prefix = "bot")
public class TelegramBotProperties {

    @NotBlank(message = "Не указано имя телеграм бота")
    private String name;

    @NotBlank(message = "Не указан токен для подключения к телеграм боту")
    private String token;

}
