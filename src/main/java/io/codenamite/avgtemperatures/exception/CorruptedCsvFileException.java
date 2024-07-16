package io.codenamite.avgtemperatures.exception;

public class CorruptedCsvFileException extends RuntimeException {

    public CorruptedCsvFileException(String message) {
        super(message);
    }

    public CorruptedCsvFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
