package ru.vsu.rogachev.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.vsu.rogachev.dto.ConfirmDTO;
import ru.vsu.rogachev.entities.ConfirmRequest;
import ru.vsu.rogachev.entities.User;
import ru.vsu.rogachev.exceptions.DbDontContainObjectException;
import ru.vsu.rogachev.services.ConfirmService;
import ru.vsu.rogachev.services.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/confirm")
public class ConfirmController {

    @Autowired
    private ConfirmService confirmService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/add")
    public void add(@RequestBody ConfirmDTO confirmDTO) throws JsonProcessingException {
       confirmService.add(confirmDTO.getUser().getId(), confirmDTO.getConfirmationCode());
    }

    @GetMapping("/getAll")
    public String getAll() throws JsonProcessingException {
        List<ConfirmRequest> requests = confirmService.getAll();
        String response = objectMapper.writeValueAsString(requests);
        return response;
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
