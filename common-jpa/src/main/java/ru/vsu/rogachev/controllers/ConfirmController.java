package ru.vsu.rogachev.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.vsu.rogachev.entities.ConfirmRequest;
import ru.vsu.rogachev.entities.User;
import ru.vsu.rogachev.exceptions.DbDontContainObjectException;
import ru.vsu.rogachev.services.ConfirmService;
import ru.vsu.rogachev.services.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/confirm")
public class ConfirmController {

    @Autowired
    private ConfirmService confirmService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/add")
    public void add(@RequestParam String confirmJSON) throws JsonProcessingException {
        ConfirmRequest request = objectMapper.readValue(confirmJSON, ConfirmRequest.class);
        confirmService.add(request);
    }

    @PostMapping("/addByParams")
    public void add(@RequestParam Long userId, @RequestParam String confirmationCode){
        confirmService.add(userId, confirmationCode);
    }

    @PostMapping("/addByParamsObj")
    public void add(@RequestParam String userJSON, @RequestParam String confirmationCode) throws JsonProcessingException {
        User user = objectMapper.readValue(userJSON, User.class);
       confirmService.add(user, confirmationCode);
    }

    @GetMapping("/getById/{id}")
    public String getById(@PathVariable(value = "id") long id) throws JsonProcessingException {
        ConfirmRequest request = confirmService.getById(id);
        String response = objectMapper.writeValueAsString(request);
        return response;
    }

    @GetMapping("/getByUserId/{id}")
    public String getByUserId(@PathVariable(value = "id") long id) throws DbDontContainObjectException, JsonProcessingException {
        ConfirmRequest request = confirmService.getByUserId(id);
        String response = objectMapper.writeValueAsString(request);
        return response;
    }

    @GetMapping("/deleteById/{id}")
    public void deleteById(@PathVariable(value = "id") long id){
        confirmService.deleteById(id);
    }

    @GetMapping("/deleteByUserId/{id}")
    public void deleteByUserId(@PathVariable(value = "id") long id){
        confirmService.deleteByUserId(id);
    }
}
