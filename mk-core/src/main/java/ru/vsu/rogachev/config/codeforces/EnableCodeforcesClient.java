package ru.vsu.rogachev.config.codeforces;

import org.springframework.context.annotation.Import;
import ru.vsu.rogachev.client.codeforces.CodeforcesClient;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({CodeforcesClient.class, CodeforcesClientConfig.class, CodeforcesClientProperties.class})
public @interface EnableCodeforcesClient {
}
