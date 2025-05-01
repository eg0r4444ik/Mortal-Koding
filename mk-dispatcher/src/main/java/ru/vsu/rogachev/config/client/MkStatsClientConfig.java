package ru.vsu.rogachev.config.client;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import ru.vsu.rogachev.config.client.properties.MkStatsClientProperties;

@Configuration
@RequiredArgsConstructor
public class MkStatsClientConfig extends CommonClientConfig {

    public static final String MK_STATS_CLIENT_NAME = "mk-stats-client";
    public static final String MK_STATS_CONNECTION_PROVIDER_NAME = "mkStatsConnectionProvider";

    private final MkStatsClientProperties properties;

    @Override
    protected String connectionProviderName() {
        return MK_STATS_CONNECTION_PROVIDER_NAME;
    }

    @Override
    protected CommonClientProperties properties() {
        return properties;
    }

    @Bean(name = MK_STATS_CLIENT_NAME)
    public WebClient mkStatsClient() {
        return buildClient();
    }

}
