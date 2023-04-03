package it.unitn;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * this class accept a file and randomize the random lines with random contents  by a givven rate
 */
public class RandomizedFileGenerator {
    private static final String RELATIVE_PATH = "src/main/resources/generated-files/randomized/";
    private static String srcFileName = "/original/Monero - MinerGate-small.out";
    private static String destFileName = "/random-monitor-data.out";
    private static int CHUNK_SIZE = 100000;

    public static void main(String[] args) throws Exception {
        generateRandomizedFile(1);
        generateRandomizedFile(5);
        generateRandomizedFile(10);
        generateRandomizedFile(15);
        generateRandomizedFile(20);
        generateRandomizedFile(25);
        generateRandomizedFile(30);
        generateRandomizedFile(35);
        generateRandomizedFile(40);
        generateRandomizedFile(45);
        generateRandomizedFile(50);
        generateRandomizedFile(55);
        generateRandomizedFile(60);
        generateRandomizedFile(65);
        generateRandomizedFile(70);
        generateRandomizedFile(75);
        generateRandomizedFile(80);
        generateRandomizedFile(85);
    }

    /**
     * @param randomizationRate a number between 0 and 100 specifying the probability of substituting the original real
     *                          log line to random line
     * @throws Exception
     */
    private static void generateRandomizedFile(Integer randomizationRate) throws Exception {
        //read from scr and write on des and substitute the mentioned lines with random content
        String absolutePath = new File(RELATIVE_PATH).getAbsolutePath();
        Path path = Paths.get(absolutePath + "\\" + randomizationRate.toString());
        Files.createDirectories(path);
        String destPath = absolutePath + "\\" + randomizationRate + "\\" + destFileName;
        if ((new File(destPath)).exists()) {
            (new File(destPath)).delete();
        }
        Files.createFile(Paths.get(destPath));

        RandomAccessFile debugFile = new RandomAccessFile(absolutePath + srcFileName, "r");
        FileChannel inChannel = debugFile.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(CHUNK_SIZE);
        String lastLineRemaning = "";
        String chunkToWrite = "";
        while (inChannel.read(buffer) > 0) {
            buffer.flip();
            String chunk = new String(buffer.array());
            chunk = lastLineRemaning + chunk;
            String[] lines = chunk.split("\n");
            for (int i = 0; i < lines.length - 1; i++) {
                String line = lines[i];
                String randomizedLine = getRandomized(line, randomizationRate);
                chunkToWrite += randomizedLine + "\n";
            }
            //print out the log
            Files.write(
                    Paths.get(destPath),
                    (chunkToWrite).getBytes(),
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
