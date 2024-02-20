package ru.vsu.rogachev;

import org.springframework.beans.factory.annotation.Autowired;
import ru.vsu.rogachev.entities.GameSession;
import ru.vsu.rogachev.services.ConfirmService;
import ru.vsu.rogachev.services.GameSessionService;
import ru.vsu.rogachev.services.TaskService;
import ru.vsu.rogachev.services.UserService;

public class Test {

    @Autowired
    private UserService userService;
    @Autowired
    private ConfirmService confirmService;
    @Autowired
    private GameSessionService gameSessionService;
    @Autowired
    private TaskService taskService;

    public void test(){
        userService.addUser(1L, "e", "r", "er", "er@yandex.ru", "err");
        userService.addUser(2L, "er", "rr", "err", "err@yandex.ru", "errr");
        confirmService.add(1L, "111111");
        gameSessionService.add(500L, 1L, 2L);
        taskService.add(1L, null, "AAA", null);
        taskService.add(1L, null, "BBB", null);
        taskService.add(1L, null, "CCC", null);

        GameSession gameSession = gameSessionService.getById(1L);
        System.out.println(gameSession.getTasks().size());

        userService.changeUserActive(1L);
        System.out.println(userService.getUserById(1L).getIsActive());

    }

}
