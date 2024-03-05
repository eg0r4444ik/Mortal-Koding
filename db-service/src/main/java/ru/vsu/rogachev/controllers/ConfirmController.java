package ru.vsu.rogachev.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.vsu.rogachev.dto.ConfirmDTO;
import ru.vsu.rogachev.entities.ConfirmRequest;
import ru.vsu.rogachev.exceptions.DbDontContainObjectException;
import ru.vsu.rogachev.services.ConfirmService;

import java.util.ArrayList;
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
        List<ConfirmDTO> result = new ArrayList<>();
        for(ConfirmRequest request : requests){
            result.add(new ConfirmDTO(request));
        }
        String response = objectMapper.writeValueAsString(result);
        return response;
    }

    @GetMapping("/getById/{id}")
    public String getById(@PathVariable(value = "id") long id) throws JsonProcessingException {
        ConfirmRequest request = confirmService.getById(id);
        ConfirmDTO dto = new ConfirmDTO(request);
        String response = objectMapper.writeValueAsString(dto);
        return response;
    }

    @GetMapping("/getByUserId/{id}")
    public String getByUserId(@PathVariable(value = "id") long id) throws DbDontContainObjectException, JsonProcessingException {
        ConfirmRequest request = confirmService.getByUserId(id);
        ConfirmDTO dto = new ConfirmDTO(request);
        String response = objectMapper.writeValueAsString(dto);
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

    @GetMapping("/existById/{id}")
    public String existById(@PathVariable(value = "id") long id){
        return String.valueOf(confirmService.existById(id));
    }

    @GetMapping("/existByUserId/{id}")
    public String existByUserId(@PathVariable(value = "id") long id){
        return String.valueOf(confirmService.existByUserId(id));
    }
}
