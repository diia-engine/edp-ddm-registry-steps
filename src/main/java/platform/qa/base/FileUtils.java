package platform.qa.base;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.commons.io.FilenameUtils;

public class FileUtils {
    public static String readFromFile(String path, String name) {
        try {
            return Files.readString(Path.of(path, FilenameUtils.getName(name)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("File was not found!: ", e);
        }
    }
}
