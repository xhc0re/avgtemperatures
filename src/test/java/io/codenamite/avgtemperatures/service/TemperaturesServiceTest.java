package io.codenamite.avgtemperatures.service;

import io.codenamite.avgtemperatures.model.YearlyTemperature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class TemperaturesServiceTest {

    private final CsvService csvService = new CsvService();
    private final TemperaturesService temperaturesService = new TemperaturesService(csvService);

    @Test
    void shouldReturnAverageTemperaturesForWarszawa() throws IOException {
        //given
        String city = "Warszawa";

        //when
        List<YearlyTemperature> yearlyTemperaturesForWarszawa = temperaturesService.getAverageYearlyTemperatures(city);

        //then
        Assertions.assertEquals(6, yearlyTemperaturesForWarszawa.size());
        Assertions.assertEquals(13.8, yearlyTemperaturesForWarszawa.get(1).averageTemperature());
    }

    @Test
    void shouldReturnEmptyArrayForUnknownCity() throws IOException {
        //given
        String city = "Unknown";

        //when
        List<YearlyTemperature> yearlyTemperaturesForUnknown = temperaturesService.getAverageYearlyTemperatures(city);

        //then
        Assertions.assertEquals(0, yearlyTemperaturesForUnknown.size());
    }

    @Test
    void shouldReturnAverageTemperaturesForWarszawaNoOpenCSV() throws IOException {
        //given
        String city = "Warszawa";

        //when
        List<YearlyTemperature> yearlyTemperaturesForWarszawa = temperaturesService.getAverageYearlyTemperaturesNoOpenCSV(city);

        //then
        Assertions.assertEquals(6, yearlyTemperaturesForWarszawa.size());
        Assertions.assertEquals(13.8, yearlyTemperaturesForWarszawa.get(1).averageTemperature());
    }

    @Test
    void shouldReturnEmptyArrayForUnknownCityNoOpenCSV() throws IOException {
        //given
        String city = "Unknown";

        //when
        List<YearlyTemperature> yearlyTemperaturesForUnknown = temperaturesService.getAverageYearlyTemperaturesNoOpenCSV(city);

        //then
        Assertions.assertEquals(0, yearlyTemperaturesForUnknown.size());
    }
}
