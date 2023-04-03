package it.unitn;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * redundant partly with detector project
 */
public class RandomUtil {
    private static Random r = new Random();

    //todo fixed time
    private static String DEFAULT_FILE_TIME_STAMP = "2022/05/18 16:57:05";
    private static Integer MIN_HEX_CHAR_COUNT = 1;
    private static Integer MAX_HEX_CHAR_COUNT = 31;

    public static String getRandomReadLogLine(String timeStamp) {
        Integer randomSize = RandomUtil.getRandomNumber(MIN_HEX_CHAR_COUNT, MAX_HEX_CHAR_COUNT);
        String randomHexStr = RandomUtil.getRandomHexString(randomSize);
        String time = DEFAULT_FILE_TIME_STAMP;
        if (timeStamp != null && !timeStamp.equals("")) {
            time = timeStamp;
        }
        String readLogLine = time + " 0x" + randomHexStr;
        //getRandomHexStringFaster(messageToWrite);
        return readLogLine;
    }

    public static String getRandomHexStringFaster(int numchars) {
        String hex = IntStream.range(0, numchars).mapToObj(i -> "F").collect(Collectors.joining(""));
        Long i = r.nextLong(Long.parseLong(hex, 16));
        return Long.toHexString(i);
    }

    public static String getRandomHexString(int numchars) {
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        while (sb.length() < numchars) {
            sb.append(Integer.toHexString(r.nextInt()));
        }
        return sb.toString().substring(0, numchars);
    }

    public static Integer getRandomNumber(Integer min, Integer max) {
        Random random = new Random();
        int randomNumber = random.nextInt(max + 1 - min) + min;
        return randomNumber;
    }

    /**
     * @param probability a number between 0 and 100 specifying the probability of returning true value
     * @return true or false
     */
    public static Boolean rollProbabilityBasedDice(Integer probability) {
        int randomNumber = getRandomNumber(1, 100);
        if (randomNumber <= probability) {
            return true;
        } else {
            return false;
        }
    }
}
