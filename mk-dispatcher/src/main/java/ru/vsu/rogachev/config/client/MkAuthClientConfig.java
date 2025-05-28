package ru.vsu.rogachev.config.client;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import ru.vsu.rogachev.config.client.properties.MkAuthClientProperties;

@Configuration
@RequiredArgsConstructor
public class MkAuthClientConfig extends CommonClientConfig {

    public static final String MK_AUTH_CLIENT_NAME = "mk-auth-client";
    public static final String MK_AUTH_CONNECTION_PROVIDER_NAME = "mkAuthConnectionProvider";

    private final MkAuthClientProperties properties;

    @Override
    protected String connectionProviderName() {
        return MK_AUTH_CONNECTION_PROVIDER_NAME;
    }

    @Override
    protected CommonClientProperties properties() {
        return properties;
    }

    @Bean(name = MK_AUTH_CLIENT_NAME)
    public WebClient mkAuthClient() {
        return buildClient();
    }

}
