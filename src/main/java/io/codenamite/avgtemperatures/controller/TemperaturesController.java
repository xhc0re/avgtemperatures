package io.codenamite.avgtemperatures.controller;

import io.codenamite.avgtemperatures.model.YearlyTemperature;
import io.codenamite.avgtemperatures.service.TemperaturesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TemperaturesController {

    private final TemperaturesService temperaturesService;
    private static final Logger LOGGER = LoggerFactory.getLogger(TemperaturesController.class);

    public TemperaturesController(TemperaturesService temperaturesService) {
        this.temperaturesService = temperaturesService;
    }

    @GetMapping("/temperatures")
    public ResponseEntity<List<YearlyTemperature>> getAverageYearlyTemperatures(@RequestParam String city) {
        try {
            List<YearlyTemperature> result = temperaturesService.getAverageYearlyTemperatures(city);
            return ResponseEntity.ok(result);
        } catch (IOException exception) {
            LOGGER.error("Internal error: {}", exception.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/temperatures/noopencsv")
    public ResponseEntity<List<YearlyTemperature>> getAverageYearlyTemperaturesNoOpenCSV(@RequestParam String city) {
        try {
            List<YearlyTemperature> result = temperaturesService.getAverageYearlyTemperaturesNoOpenCSV(city);
            return ResponseEntity.ok(result);
        } catch (IOException exception) {
            LOGGER.error("Internal error: {}", exception.getMessage());
            return ResponseEntity.status(500).build();
        }
    }
}
