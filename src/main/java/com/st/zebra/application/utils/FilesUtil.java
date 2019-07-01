package com.st.zebra.application.utils;

import com.st.zebra.infrastructure.AppException;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * Class help to work with file system
 */
public class FilesUtil {
    private final static Logger log = Logger.getLogger(FilesUtil.class.getName());

    /**
     * Find file by name and return Supplier to reading file text by one line.
     *
     * @param filePath file name or path
     * @return Supplier - use it like: .get().forEach(System.out::println);
     * @throws AppException throw if incorrect path
     */
    public static Supplier<Stream<String>> getFileLines(String filePath) throws AppException {
        final Path path = Paths.get(filePath);
        if (Files.notExists(path)) {
            throw new AppException("Incorrect path");
        }

        return () -> {
            try {
                return Files.lines(path);
            } catch (IOException e) {
                log.log(Level.WARNING, "Error in reading lines file.", e);
                throw new IllegalArgumentException("Reading file lines exception");
            }
        };
    }

    /**
     * Create file from newFileName or with date (then option 'create')
     * Write into file all strings from 'linesToPrint'
     *
     * @param linesToPrint - Strings to save into a file
     * @param newFileName  file name
     * @param option       File saving strategy
     */
    public static void createFileFromLines(List<String> linesToPrint, String newFileName, StandardOpenOption option) {
        try {
            Files.write(Paths.get(newFileName), linesToPrint, option);
            log.log(Level.INFO, "Created file: " + newFileName);
        } catch (InvalidPathException | IOException e) {
            String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")) + "_" + newFileName;
            try {
                Files.write(Paths.get(fileName), linesToPrint, option);
                log.log(Level.INFO, "Created file: " + fileName);
            } catch (IOException ex) {
                log.log(Level.WARNING, "Error in creating file.", ex);
            }
        }
    }
}
