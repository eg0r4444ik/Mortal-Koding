package ru.vsu.rogachev.config.client;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import ru.vsu.rogachev.config.client.properties.MkGameClientProperties;

@Configuration
@RequiredArgsConstructor
public class MkGameClientConfig extends CommonClientConfig {

    public static final String MK_GAME_CLIENT_NAME = "mk-game-client";
    public static final String MK_GAME_CONNECTION_PROVIDER_NAME = "mkGameConnectionProvider";

    private final MkGameClientProperties properties;

    @Override
    protected String connectionProviderName() {
        return MK_GAME_CONNECTION_PROVIDER_NAME;
    }

    @Override
    protected CommonClientProperties properties() {
        return properties;
    }

    @Bean(name = MK_GAME_CLIENT_NAME)
    public WebClient mkGameClient() {
        return buildClient();
    }

}
