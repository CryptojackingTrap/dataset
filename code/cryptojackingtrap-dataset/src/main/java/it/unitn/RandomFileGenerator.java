package it.unitn;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class RandomFileGenerator {
    private static final String RELATIVE_PATH = "src/main/resources/generated-files/randomized/100/";
    private static String fileName = "/random-monitor-data.out";
    private static Integer FILE_LINE_NUMBER = 20;

    public static void main(String[] args) throws Exception {
        String absolutePath = new File(RELATIVE_PATH).getAbsolutePath();
        Path path = Paths.get(absolutePath);
        Files.createDirectories(path);
        if ((new File(absolutePath + fileName)).exists()) {
            (new File(absolutePath + fileName)).delete();
        }
        Files.createFile(Paths.get(absolutePath + fileName));
        for (int i = 0; i < FILE_LINE_NUMBER; i++) {
            Files.write(
                    Paths.get(absolutePath + fileName),
                    (RandomUtil.getRandomReadLogLine(null) + "\n").getBytes(),
                    StandardOpenOption.APPEND);
        }
    }
}
