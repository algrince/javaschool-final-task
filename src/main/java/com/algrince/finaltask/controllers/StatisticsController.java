package com.algrince.finaltask.controllers;

import com.algrince.finaltask.services.StatisticsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequestMapping("statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("monthly_revenue")
    public List<LinkedHashMap<Object, Object>> getMonthlyRevenue(
            @RequestParam(required = true) int number) {
        List<List<Calendar>> months = statisticsService.getMonths(number);
        return statisticsService.getPeriodRevenue(months, "month");
    }

    @GetMapping("weekly_revenue")
    public List<LinkedHashMap<Object, Object>> getWeeklyRevenue(
            @RequestParam(required = true) int number) {
        List<List<Calendar>> weeks = statisticsService.getWeeks(number);
        return statisticsService.getPeriodRevenue(weeks, "week");
    }

    @GetMapping("top_products")
    public List<LinkedHashMap<Object, Object>> getTop10Products() {
        return statisticsService.getTop10Products();
    }

    @GetMapping("top_buyers")
    public List<LinkedHashMap<Object, Object>> getTop10Buyers() {
        return statisticsService.getTop10Buyers();
    }
}
