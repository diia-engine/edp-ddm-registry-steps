package platform.qa.base;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

public class FileUtils {
    private static final Logger LOG = Logger.getLogger(FileUtils.class);

    public static String readFromFile(String path, String name) {
        try {
            Path pathToFile = Path.of(path, FilenameUtils.getName(name));
            LOG.info("Read from file: " + pathToFile);
            return Files.readString(pathToFile, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("File was not found!: ", e);
        }
    }
}
