package platform.qa.base.utils;

import lombok.SneakyThrows;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    @SneakyThrows(IOException.class)
    public static <T> T readFileToObject(String resourcePath, String name, Class<T> clazzValue, ObjectMapper mapper) {
        Path pathToFile = Path.of(resourcePath, FilenameUtils.getName(name));
        return mapper.readValue(pathToFile.toFile(), clazzValue);
    }

    public static <T> List<T> readFilesToObjectList(String resourcePath, Class<T> clazzValue, ObjectMapper mapper) {
        int nameCount = Path.of(resourcePath).getNameCount();
        return IntStream.range(0, nameCount)
                .mapToObj(i -> readFileToObject(resourcePath, Path.of(resourcePath).getName(i).toString(), clazzValue
                        , mapper))
                .collect(Collectors.toList());
    }
}
