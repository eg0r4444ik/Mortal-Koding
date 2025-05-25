package ru.vsu.rogachev.config.db;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@EnableJpaRepositories(basePackages = "ru.vsu.rogachev.repositories")
@EntityScan(basePackages = "ru.vsu.rogachev.entity")
public @interface EnableDb {
}
