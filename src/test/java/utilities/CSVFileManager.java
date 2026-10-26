
package utilities;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CSVFileManager {
    private final String filePath;


    public CSVFileManager(String filePath) {
        this.filePath = filePath;
    }

    public void writeProductsHeader() {
        try (OutputStreamWriter output = new OutputStreamWriter(
                new FileOutputStream(filePath, false),
                StandardCharsets.UTF_8)) {

            output.write('\uFEFF');

            try (CSVWriter writer = new CSVWriter(output)) {
                writer.writeNext(new String[]{"name", "price", "quantity"});
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to write CSV header", e);
        }
    }

    public void writeProduct(String name, double price, int quantity) {
        try (CSVWriter writer = new CSVWriter(
                new OutputStreamWriter(
                        new FileOutputStream(filePath, true),
                        StandardCharsets.UTF_8))) {

            writer.writeNext(new String[]{
                    name,
                    String.valueOf(price),
                    String.valueOf(quantity)
            });

        } catch (IOException e) {
            throw new RuntimeException("Failed to write product to CSV", e);
        }
    }
    public List<String[]> readProductsFromCSV() {

        try (CSVReader reader = new CSVReader(
                Files.newBufferedReader(
                        Path.of(filePath),
                        StandardCharsets.UTF_8))) {

            List<String[]> rows = reader.readAll();

            if (!rows.isEmpty()) {
                rows.remove(0);
            }


            if (!rows.isEmpty() && rows.get(0).length > 0) {
                rows.get(0)[0] =
                        rows.get(0)[0].replace("\uFEFF", "");
            }

            return rows;

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to read products from CSV", e);
        }
    }


    public int[] readPriceRangeFromCSV() {
        List<String[]> rows = readProductsFromCSV();

        if (rows.isEmpty() || rows.get(0).length < 2) {
            throw new RuntimeException(
                    "CSV must contain minPrice and maxPrice."
            );
        }

        int minPrice = Integer.parseInt(rows.get(0)[0].trim());
        int maxPrice = Integer.parseInt(rows.get(0)[1].trim());

        return new int[]{minPrice, maxPrice};
    }

}
