package io.codenamite.avgtemperatures.model;

public record TemperatureRecord(String city, java.time.LocalDateTime date, Double temp) {}