package ru.vsu.rogachev.config;

import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.vsu.rogachev.repositories.ConfirmRepository;
import ru.vsu.rogachev.repositories.GameRepository;
import ru.vsu.rogachev.repositories.InviteRepository;
import ru.vsu.rogachev.repositories.PlayerRepository;
import ru.vsu.rogachev.repositories.TaskRepository;
import ru.vsu.rogachev.repositories.UserRepository;
import ru.vsu.rogachev.services.ConfirmService;
import ru.vsu.rogachev.services.PlayerService;
import ru.vsu.rogachev.services.TaskService;
import ru.vsu.rogachev.services.UserService;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@EnableJpaRepositories
@Import({
        ConfirmRepository.class,
        GameRepository.class,
        InviteRepository.class,
        PlayerRepository.class,
        TaskRepository.class,
        UserRepository.class,
        ConfirmService.class,
        GameService.class,
        InviteService.class,
        PlayerService.class,
        TaskService.class,
        UserService.class
})
public @interface EnableDb {
}
