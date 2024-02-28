package ru.vsu.rogachev.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.vsu.rogachev.entities.User;
import ru.vsu.rogachev.entities.enums.UserState;
import ru.vsu.rogachev.exceptions.DbDontContainObjectException;
import ru.vsu.rogachev.services.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/add")
    public void add(@RequestParam String userJSON) throws JsonProcessingException {
        User user = objectMapper.readValue(userJSON, User.class);
        userService.addUser(user);
    }

    @PostMapping("/addByParams")
    public void add(@RequestParam Long telegramUserId, @RequestParam String firstName, @RequestParam String lastName,
                            @RequestParam String username, @RequestParam String email, @RequestParam Long rating,
                            @RequestParam String codeforcesUsername){
        userService.addUser(telegramUserId, firstName, lastName, username, email, rating, codeforcesUsername);
    }

    @GetMapping("/get/{id}")
    public String getById(@PathVariable(value = "id") long id) throws JsonProcessingException {
        User user = userService.getUserById(id);
        String response = objectMapper.writeValueAsString(user);
        return response;
    }

    @GetMapping("/getByTelegramId/{id}")
    public String getByTelegramId(@PathVariable(value = "id") long tgId) throws DbDontContainObjectException, JsonProcessingException {
        User user = userService.getUserByTelegramId(tgId);
        String response = objectMapper.writeValueAsString(user);
        return response;
    }

    @GetMapping("/getByCodeforcesUsername/{username}")
    public String getByCodeforcesUsername(@PathVariable(value = "username") String username) throws DbDontContainObjectException, JsonProcessingException {
        User user = userService.getUserByCodeforcesUsername(username);
        String response = objectMapper.writeValueAsString(user);
        return response;
    }

    @GetMapping("/changeState/{id}/{state}")
    public void changeUserState(@PathVariable(value = "id") long id, @PathVariable(value = "id") String state){
        UserState userState = UserState.BASIC_STATE;
        if(state.equals("WAIT_FOR_EMAIL_STATE")){
            userState = UserState.WAIT_FOR_EMAIL_STATE;
        }
        if(state.equals("DURING_THE_GAME")){
            userState = UserState.DURING_THE_GAME;
        }
        userService.changeUserState(id, userState);
    }

    @GetMapping("/changeActive/{id}")
    public void changeUserActive(@PathVariable(value = "id") long id){
        userService.changeUserActive(id);
    }

    @GetMapping("/changeCodeforcesUsername/{id}/{username}")
    public void changeCodeforcesUsername(@PathVariable(value = "id") long id, @PathVariable(value = "id") String username){
        userService.changeCodeforcesUsername(id, username);
    }

    @GetMapping("/delete/{id}")
    public void deleteById(@PathVariable(value = "id") long id) throws JsonProcessingException {
        userService.deleteUserById(id);
    }

    @GetMapping("/deleteByTelegramId/{id}")
    public void deleteByTelegramId(@PathVariable(value = "id") long tgId) throws DbDontContainObjectException, JsonProcessingException {
        userService.deleteUserByTelegramId(tgId);
    }

    @GetMapping("/deleteByCodeforcesUsername/{username}")
    public void deleteByCodeforcesUsername(@PathVariable(value = "username") String username) throws DbDontContainObjectException, JsonProcessingException {
        userService.deleteUserByCodeforcesUsername(username);
    }
}
