package com.baeldung.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;

import com.opencsv.CSVReader;

public class ReadCSVInArrayUnitTest {
    public static final String COMMA_DELIMITER = ",";
    public static final String CSV_FILE = "src/test/resources/book.csv";
    public static final List<List<String>> EXPECTED_ARRAY = Collections.unmodifiableList(new ArrayList<List<String>>() {
        {
            add(new ArrayList<String>() {
                {
                    add("Mary Kom");
                    add("Unbreakable");
                }
            });
            add(new ArrayList<String>() {
                {
                    add("Kapil Isapuari");
                    add("Farishta");
                }
            });
        }
    });


    @Test
    public void givenCSVFile_whenBufferedReader_thenContentsAsExpected() throws IOException {
        List<List<String>> records = new ArrayList<List<String>>();
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                records.add(Arrays.asList(values));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < EXPECTED_ARRAY.size(); i++) {
            Assert.assertArrayEquals(EXPECTED_ARRAY.get(i)
                .toArray(),
                records.get(i)
                    .toArray());
        }
    }

    @Test
    public void givenCSVFile_whenScanner_thenContentsAsExpected() throws IOException {
        List<List<String>> records = new ArrayList<List<String>>();
        try (Scanner scanner = new Scanner(new File(CSV_FILE));) {
            while (scanner.hasNextLine()) {
                records.add(getRecordFromLine(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < EXPECTED_ARRAY.size(); i++) {
            Assert.assertArrayEquals(EXPECTED_ARRAY.get(i)
                .toArray(),
                records.get(i)
                    .toArray());
        }
    }

    private List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<String>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(COMMA_DELIMITER);
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return values;
    }

    @Test
    public void givenCSVFile_whenOpencsv_thenContentsAsExpected() throws IOException {
        List<List<String>> records = new ArrayList<List<String>>();
        try (CSVReader csvReader = new CSVReader(new FileReader(CSV_FILE));) {
            String[] values = null;
            while ((values = csvReader.readNext()) != null) {
                records.add(Arrays.asList(values));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < EXPECTED_ARRAY.size(); i++) {
            Assert.assertArrayEquals(EXPECTED_ARRAY.get(i)
                .toArray(),
                records.get(i)
                    .toArray());
        }
    }

    @Test
    public void givenCSVFile_whenUsingFilesReadAllLinesMethod_thenContentsAsExpected() throws IOException {
        List<List<String>> records = Files.readAllLines(Paths.get(CSV_FILE))
          .stream()
          .map(line -> Arrays.asList(line.split(COMMA_DELIMITER)))
          .collect(Collectors.toList());

        for (int i = 0; i < EXPECTED_ARRAY.size(); i++) {
            Assert.assertArrayEquals(EXPECTED_ARRAY.get(i)
                .toArray(),
                records.get(i)
                    .toArray());
        }
    }

    @Test
    public void givenCSVFile_whenUsingFilesNewBufferedReaderMethod_thenContentsAsExpected() throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(CSV_FILE))) {
            List<List<String>> records = reader.lines()
              .map(line -> Arrays.asList(line.split(COMMA_DELIMITER)))
              .collect(Collectors.toList());

            for (int i = 0; i < EXPECTED_ARRAY.size(); i++) {
                Assert.assertArrayEquals(EXPECTED_ARRAY.get(i)
                    .toArray(),
                    records.get(i)
                        .toArray());
            }
        }
    }

    @Test
    public void givenCSVFile_whenUsingFilesLinesMethod_thenContentsAsExpected() throws IOException {
        try (Stream<String> lines = Files.lines(Paths.get(CSV_FILE))) {
            List<List<String>> records = lines.map(line -> Arrays.asList(line.split(COMMA_DELIMITER)))
              .collect(Collectors.toList());

            for (int i = 0; i < EXPECTED_ARRAY.size(); i++) {
                Assert.assertArrayEquals(EXPECTED_ARRAY.get(i)
                    .toArray(),
                    records.get(i)
                        .toArray());
            }
        }
    }
}
