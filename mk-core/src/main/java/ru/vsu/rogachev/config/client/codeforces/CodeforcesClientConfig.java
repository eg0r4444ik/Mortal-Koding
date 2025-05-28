package ru.vsu.rogachev.config.client.codeforces;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import ru.vsu.rogachev.config.client.CommonClientConfig;
import ru.vsu.rogachev.config.client.CommonClientProperties;

@Configuration
@RequiredArgsConstructor
public class CodeforcesClientConfig extends CommonClientConfig {

    public static final String CODEFORCES_CLIENT_NAME = "codeforces-client";
    public static final String CODEFORCES_CONNECTION_PROVIDER_NAME = "codeforcesConnectionProvider";

    private final CodeforcesClientProperties properties;

    @Override
    protected String connectionProviderName() {
        return CODEFORCES_CONNECTION_PROVIDER_NAME;
    }

    @Override
    protected CommonClientProperties properties() {
        return properties;
    }

    @Bean(name = CODEFORCES_CLIENT_NAME)
    public WebClient codeforcesClient() {
        return buildClient();
    }

}
