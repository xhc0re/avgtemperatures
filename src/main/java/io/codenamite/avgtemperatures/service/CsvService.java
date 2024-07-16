package io.codenamite.avgtemperatures.service;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import io.codenamite.avgtemperatures.exception.CorruptedCsvFileException;
import io.codenamite.avgtemperatures.model.TemperatureRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

@Service
public class CsvService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CsvService.class);
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public List<TemperatureRecord> readCsv() throws IOException {
        List<TemperatureRecord> records = new ArrayList<>();
        Resource resource = new ClassPathResource("data/example_file.csv");
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(resource.getFile().getPath()));
             CSVReader csvReader = new CSVReaderBuilder(reader)
                     .withCSVParser(new CSVParserBuilder()
                             .withSeparator(';')
                             .build()
                     ).build();
        ) {
            List<String[]> chunk = new ArrayList<>();
            String[] line;
            int batchSize = 1000; // Example batch size
            int count = 0;
            while ((line = csvReader.readNext()) != null) {
                chunk.add(line);
                count++;
                if (count % batchSize == 0) {
                    processChunk(chunk, records);
                    chunk.clear();
                }
            }
            if (!chunk.isEmpty()) {
                processChunk(chunk, records);
            }
        } catch (CsvValidationException exception) {
            LOGGER.error("Corrupted CSV file: {}", exception.getMessage());
            throw new CorruptedCsvFileException("Corrupted CSV file:", exception);
        }
        return records;
    }

    private void processChunk(List<String[]> chunk, List<TemperatureRecord> records) {
        records.addAll(chunk.stream()
                .map(line -> {
                    String city = line[0];
                    LocalDateTime dateTime;
                    double temperature;
                    try {
                        dateTime = LocalDateTime.parse(line[1], dateFormatter);
                        temperature = Math.floor(Double.parseDouble(line[2]) * 100) / 100;
                        return new TemperatureRecord(city, dateTime, temperature);
                    } catch (NumberFormatException | DateTimeException | ArrayIndexOutOfBoundsException exception) {
                        LOGGER.error("Corrupted entry in CSV file: {}", exception.getMessage());
                        return null; // Lub obsługa błędu
                    }
                })
                .filter(Objects::nonNull) //
                .toList());
    }

    public List<TemperatureRecord> readCsv2() throws IOException {
        List<TemperatureRecord> records = new ArrayList<>();
        Resource resource = new ClassPathResource("data/example_file.csv");

        try (BufferedReader reader = new BufferedReader(new FileReader(resource.getFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length < 3) {
                    LOGGER.error("Invalid CSV line: {}", line);
                    continue;
                }
                String city = parts[0];
                LocalDateTime dateTime;
                double temperature;
                try {
                    dateTime = LocalDateTime.parse(parts[1], dateFormatter);
                    temperature = Math.floor(Double.parseDouble(parts[2]) * 100) / 100;
                    records.add(new TemperatureRecord(city, dateTime, temperature));
                } catch (NumberFormatException | DateTimeException e) {
                    LOGGER.error("Corrupted entry in CSV file: {}", line, e);
                }
            }
        } catch (IOException e) {
            LOGGER.error("Error reading CSV file: {}", e.getMessage());
            throw e;
        }
        return records;
    }
}