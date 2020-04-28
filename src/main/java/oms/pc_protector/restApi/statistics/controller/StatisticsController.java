package oms.pc_protector.restApi.statistics.controller;


import oms.pc_protector.apiConfig.model.SingleResult;
import oms.pc_protector.apiConfig.service.ResponseService;
import oms.pc_protector.restApi.statistics.service.StatisticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "v1/statistics")
public class StatisticsController {

    private StatisticsService statisticsService;

    private ResponseService responseService;

    public StatisticsController(StatisticsService statisticsService, ResponseService responseService) {
        this.statisticsService = statisticsService;
        this.responseService = responseService;
    }

    @GetMapping(value = "")
    public SingleResult<?> findAll() {
        List<Object> result = statisticsService.findAll();
        return responseService.getSingleResult(result);
    }
}
