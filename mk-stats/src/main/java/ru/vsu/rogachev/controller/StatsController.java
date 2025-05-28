package ru.vsu.rogachev.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.rogachev.client.mk.container.ResponseContainer;
import ru.vsu.rogachev.client.mk.stats.dto.UserStatisticResponse;
import ru.vsu.rogachev.service.StatsService;

import static ru.vsu.rogachev.client.mk.stats.StatsEndpoints.GET_STATISTIC;

@RestController
@RequiredArgsConstructor
public class StatsController {

    private final StatsService statsService;

    @PostMapping(GET_STATISTIC)
    public ResponseContainer<UserStatisticResponse> getUserStatistic(@RequestBody String handle){
        return statsService.getUserStatistic(handle);
    }

}
