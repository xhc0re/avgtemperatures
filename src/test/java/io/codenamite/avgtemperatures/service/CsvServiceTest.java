package io.codenamite.avgtemperatures.service;

import io.codenamite.avgtemperatures.model.TemperatureRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class CsvServiceTest {

    private final CsvService csvService = new CsvService();

    @Test
    void shouldReturn10386TemperaturesData() throws IOException {
        List<TemperatureRecord> temperatureRecords = csvService.readCsv();
        Assertions.assertEquals(10386, temperatureRecords.size());
    }

    @Test
    void shouldReturn10386TemperaturesDataNoOpenCSV() throws IOException {
        List<TemperatureRecord> temperatureRecords = csvService.readCsv2();
        Assertions.assertEquals(10386, temperatureRecords.size());
    }
}
