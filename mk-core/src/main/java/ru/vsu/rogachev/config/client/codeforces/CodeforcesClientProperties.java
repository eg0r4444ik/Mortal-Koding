package ru.vsu.rogachev.config.client.codeforces;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import ru.vsu.rogachev.config.client.CommonClientProperties;

@Component
@ConfigurationProperties(prefix = "client.codeforces")
public class CodeforcesClientProperties extends CommonClientProperties {

}
