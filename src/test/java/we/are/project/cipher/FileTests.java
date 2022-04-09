package we.are.project.cipher;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FileTests {

    private static final String REGEX_PACKAGE = "^package [A-Za-z0-9\\.]+;$";
    private static final String REGEX_IMPORT = "^import [A-Za-z0-9\\.\\*]+;$";
    private static final String REGEX_EMPTY = "^\\w*$";
    private static final Logger LOGGER = LoggerFactory.getLogger(FileTests.class);
    File sourcesRoot = new File(System.getProperty("user.dir"), "src" + File.separator + "main" + File.separator + "java");
    Map<File, List<String>> source = new HashMap<>();

    @BeforeAll
    public void readSourceFiles() throws IOException {
        LOGGER.debug("Loading source code in " + sourcesRoot.getAbsolutePath());
        Files.walk(sourcesRoot.toPath()).filter(path -> {
            if (!Files.isRegularFile(path)) return false;
            return path.toFile().getName().endsWith(".java");
        }).forEach(path -> {
            File file = path.toFile();
            List<String> lines;
            try {
                lines = readLines(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            source.put(file, lines);
            LOGGER.debug("Read " + lines.size() + " lines from file " + file.getAbsolutePath());
        });
    }

    private static List<String> readLines(File file) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            return bufferedReader.lines().collect(Collectors.toList());
        }
    }

    @Test
    public void countSemicolons() {
        for (Map.Entry<File, List<String>> entry : source.entrySet()) {
            File file = entry.getKey();
            List<String> lines = entry.getValue();
            List<Map.Entry<String, Integer>> perLine = new ArrayList<>();
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                if (isIgnoredLine(line)) continue;
                int semicolons = getSemicolons(line);
                String linePath = file.getAbsolutePath().replace(System.getProperty("user.dir"),"") + ":" + (i + 1);
                assert semicolons < 4 : linePath + " contains " + semicolons + " semicolons";
                perLine.add(new AbstractMap.SimpleEntry<>(linePath, semicolons));
            }
            perLine = perLine.stream().filter(line -> line.getValue() > 0).collect(Collectors.toList());
            if (!perLine.isEmpty()) perLine.remove(perLine.size() - 1);
            perLine.forEach(line -> {
                if (line.getValue() < 3) {
                    LOGGER.warn(line.getKey() + " only uses " + line.getValue() + " semicolons, could be more.");
                }
            });
        }
    }

    private static boolean isIgnoredLine(String line) {
        return line.matches(REGEX_IMPORT) || line.matches(REGEX_PACKAGE) || line.matches(REGEX_EMPTY);
    }

    private static int getSemicolons(String string) {
        return (int) string.chars().filter(value -> ((char) value) == ';').count();
    }

}
