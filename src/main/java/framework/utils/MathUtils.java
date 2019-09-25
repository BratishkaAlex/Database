package framework.utils;

import java.util.Random;

public class MathUtils {
    public static int getRandomInt(int maxInt) {
        return new Random().nextInt(maxInt);
    }
}
