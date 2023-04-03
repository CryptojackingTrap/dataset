import it.unitn.RandomUtil;

public class RollProbabilityBasedDiceTest {
    public static void main(String[] args) {
        Integer probability = 10;
        Float count = 2000000f;
        Integer trueCounter = 0;
        for (int i = 0; i < count; i++) {
            Boolean dice = RandomUtil.rollProbabilityBasedDice(probability);
            if (dice) {
                trueCounter++;
            }
        }
        Float real = Float.valueOf((trueCounter / count) * 100);
        System.out.println("expected:" + probability + ", real:" + real);
    }
}
