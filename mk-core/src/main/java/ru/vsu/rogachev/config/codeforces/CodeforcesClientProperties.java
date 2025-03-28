package ru.vsu.rogachev.config.codeforces;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Data
@Validated
@Accessors(chain = true)
@ConfigurationProperties(prefix = "client.codeforces")
public class CodeforcesClientProperties {

    @NotBlank(message = "Не указано поле URL")
    private String url;

    @NotNull(message = "Не указано поле connections")
    @Positive(message = "Поле connections должно быть больше 0")
    private Integer connections = 30;

    @PositiveOrZero(message = "Поле maxRetryAttempts должно быть больше или равно 0")
    private Integer maxRetryAttempts = 0;

    @Positive(message = "Поле connectionsTimeoutInSeconds должно быть больше 0")
    private Integer connectionsTimeoutInSeconds = 25;

    @Positive(message = "Поле readTimeoutInSeconds должно быть больше 0")
    private Integer readTimeoutInSeconds = 25;

    @Positive(message = "Поле pendingAcquireTimeoutInSeconds должно быть больше 0")
    private Integer pendingAcquireTimeoutInSeconds = 10;

}
