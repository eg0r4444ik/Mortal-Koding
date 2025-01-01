package ru.vsu.rogachev.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(CodeforcesClientProperties.class)
public class CodeforcesClientConfig {

    public static final String CODEFORCES_CLIENT = "codeforces-client";
    private static final String CODEFORCES_CONNECTION_PROVIDER_NAME = "codeforcesConnectionProvider";

    private final CodeforcesClientProperties properties;

    @Bean(CODEFORCES_CLIENT)
    public WebClient codeforcesClient() {
        // Настройка ConnectionProvider с ограничением на количество соединений и ожидание
        ConnectionProvider connectionProvider = ConnectionProvider.builder(CODEFORCES_CONNECTION_PROVIDER_NAME)
                .maxConnections(properties.getConnections())
                .pendingAcquireTimeout(Duration.ofSeconds(properties.getPendingAcquireTimeoutInSeconds()))
                .build();

        // Настройка HttpClient с таймаутами соединений и чтения
        HttpClient httpClient = HttpClient.create(connectionProvider)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, properties.getConnectionsTimeoutInSeconds() * 1000)
                .doOnConnected(connection ->
                        connection.addHandlerLast(new ReadTimeoutHandler(properties.getReadTimeoutInSeconds(), TimeUnit.SECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(properties.getReadTimeoutInSeconds(), TimeUnit.SECONDS)));

        // Создание WebClient с базовым URL и кастомным HttpClient
        return WebClient.builder()
                .baseUrl(properties.getUrl())
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

}
