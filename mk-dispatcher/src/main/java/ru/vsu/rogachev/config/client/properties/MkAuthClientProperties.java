package ru.vsu.rogachev.config.client.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import ru.vsu.rogachev.config.client.CommonClientProperties;

@Component
@ConfigurationProperties(prefix = "client.mk.auth")
public class MkAuthClientProperties extends CommonClientProperties {

}
