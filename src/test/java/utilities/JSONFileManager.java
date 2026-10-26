
package utilities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;

public class JSONFileManager {

    private static final Logger log =
            LogManager.getLogger(JSONFileManager.class);

    private final LinkedHashMap<String, Object> data;

    public JSONFileManager(String filePath) {

        Type type = new TypeToken<LinkedHashMap<String, Object>>() {
        }.getType();

        try (var reader = Files.newBufferedReader(
                Path.of(filePath), StandardCharsets.UTF_8)) {

            data = new Gson().fromJson(reader, type);

            if (data == null) {
                throw new IllegalStateException(
                        "JSON file is empty or invalid: " + filePath
                );
            }

            log.info("JSON data loaded successfully from {}", filePath);

        } catch (IOException e) {
            log.error("Failed to read JSON file: {}", filePath, e);
            throw new RuntimeException(
                    "Could not read JSON file: " + filePath, e
            );
        }
    }

    public Object getValue(String key) {
        if (!data.containsKey(key)) {
            throw new IllegalArgumentException(
                    "Key not found in JSON: " + key
            );
        }

        return data.get(key);
    }

    public double getDouble(String key) {
        return ((Number) getValue(key)).doubleValue();
    }

    public int getInt(String key) {
        return ((Number) getValue(key)).intValue();
    }
    public String getString(String key) {
        return ((String) getValue(key));
    }


}
