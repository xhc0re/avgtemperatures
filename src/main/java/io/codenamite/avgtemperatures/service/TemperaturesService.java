package io.codenamite.avgtemperatures.service;

import io.codenamite.avgtemperatures.model.TemperatureRecord;
import io.codenamite.avgtemperatures.model.YearlyTemperature;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TemperaturesService {

    private final CsvService csvService;

    public TemperaturesService(CsvService csvService) {
        this.csvService = csvService;
    }

    @Caching(evict={@CacheEvict(value="yearlyTemperaturesCache", key="#city")})
    public List<YearlyTemperature> getAverageYearlyTemperatures(String city) throws IOException {
        List<TemperatureRecord> records = csvService.readCsv();
        return getYearlyTemperatures(city, records);
    }

    @Caching(evict={@CacheEvict(value="yearlyTemperaturesCacheNoOpenCSV", key="#city")})
    public List<YearlyTemperature> getAverageYearlyTemperaturesNoOpenCSV(String city) throws IOException {
        List<TemperatureRecord> records = csvService.readCsv2();
        return getYearlyTemperatures(city, records);
    }

    private List<YearlyTemperature> getYearlyTemperatures(String city, List<TemperatureRecord> records) {
        Map<Integer, Double> yearlyAverages = records.stream()
                .filter(record -> record.city().equalsIgnoreCase(city))
                .collect(Collectors.groupingBy(
                        record -> record.date().getYear(),
                        Collectors.averagingDouble(TemperatureRecord::temp)
                ));
        return yearlyAverages.entrySet().stream()
                .map(entry -> new YearlyTemperature(entry.getKey(), Math.floor(entry.getValue() * 100) / 100))
                .collect(Collectors.toList());
    }
}
