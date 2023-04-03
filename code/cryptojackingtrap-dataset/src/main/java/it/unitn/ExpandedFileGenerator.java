package it.unitn;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ExpandedFileGenerator {
    private static final String RELATIVE_PATH = "src/main/resources/generated-files/expanded/";
    private static String srcFileName = "/original/Monero - MinerGate-small.out";
    private static String destFileName = "/random-monitor-data.out";
    private static int CHUNK_SIZE = 100000;

    public static void main(String[] args) throws Exception {
        generateExpandedFile(1);
        generateExpandedFile(2);
        generateExpandedFile(3);
        generateExpandedFile(4);
        generateExpandedFile(5);
        generateExpandedFile(6);
        generateExpandedFile(7);
        generateExpandedFile(8);
        generateExpandedFile(9);
        generateExpandedFile(10);
        generateExpandedFile(11);
        generateExpandedFile(12);
    }

    /**
     * this method add lineCount=times-1 lines between each two lines of origin file. The inserted data are from
     * getRandomReadLogLine
     *
     * @param times
     * @throws Exception
     */
    private static void generateExpandedFile(Integer times) throws Exception {
        //read from scr and write on des and substitute the mentioned lines with random content
        String absolutePath = new File(RELATIVE_PATH).getAbsolutePath();
        Path path = Paths.get(absolutePath + "\\" + times.toString());
        Files.createDirectories(path);
        String destPath = absolutePath + "\\" + times + "\\" + destFileName;
        if ((new File(destPath)).exists()) {
            (new File(destPath)).delete();
        }
        Files.createFile(Paths.get(destPath));

        RandomAccessFile debugFile = new RandomAccessFile(absolutePath + srcFileName, "r");
        FileChannel inChannel = debugFile.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(CHUNK_SIZE);
        String lastLineRemaning = "";
        String chunkToWrite = "";
        Integer lineCount = times - 1;
        RandomUtil randomUtil = new RandomUtil();
        String nextLine;
        while (inChannel.read(buffer) > 0) {
            buffer.flip();
            String chunk = new String(buffer.array());
            chunk = lastLineRemaning + chunk;
            String[] lines = chunk.split("\n");
            for (int i = 0; i < lines.length - 1; i++) {
                String line = lines[i];
                chunkToWrite += line + "\n";
                for (int j = 0; j < lineCount; j++) {
                    try {
                        timeStamp = line.substring(0, 19);
                    } catch (Exception e) {
                        //just skip it and continue with the most recent value and null is also safe
                    }
                    nextLine = randomUtil.getRandomReadLogLine(timeStamp);
                    chunkToWrite += nextLine + "\n";
                }
            }
            //print out the log
            Files.write(
                    Paths.get(destPath),
                    (chunkToWrite + "\n").getBytes(),
                    StandardOpenOption.APPEND);
            chunkToWrite = "";
            lastLineRemaning = lines[lines.length - 1];
            buffer.clear();
        }
        inChannel.close();
        debugFile.close();
    }

    static String timeStamp = null;

    /**
     * @param input
     * @return the the same input string with probability of (100-RANDOMIZATION_RATE)/100 and otherwise return a
     * content with the following format:
     * input: [timestamp \space content]  output: [timestamp \space getRandomReadLogLine]
     */
    private static String getRandomized(String input, Integer randomizationRate) {
        RandomUtil randomUtil = new RandomUtil();
        Boolean randomizeLine = randomUtil.rollProbabilityBasedDice(randomizationRate);
        String out;
        if (randomizeLine) {
            try {
                timeStamp = input.substring(0, 19);
            } catch (Exception e) {
                //just skip it and continue with the most recent value and null is also safe
            }
            out = randomUtil.getRandomReadLogLine(timeStamp);
        } else {
            out = input;
        }
        return out;
    }
}
