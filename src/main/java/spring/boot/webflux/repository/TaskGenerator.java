package spring.boot.webflux.repository;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class TaskGenerator {

    private static final Random RANDOM = new Random(System.currentTimeMillis());

    private static final List<String> TASK_NAME =
            Arrays.asList(
                    "Something", "thatThing", "OhNotThis", "YeahThis", "Nanana",
                    "StopIt", "Do-it", "Slacking", "Whatsup", "Nothing");


    private static final List<Boolean> TASK_STATUS =
            Arrays.asList(
                    true,
                    false);

    public static String randomTaskName() {
        return TASK_NAME.get(RANDOM.nextInt(TASK_NAME.size()));
    }

    public static Boolean randomStatus() {
        return TASK_STATUS.get(RANDOM.nextInt(TASK_STATUS.size()));
    }
    
    public static Long randomId() {
        return ThreadLocalRandom.current().nextLong(21, 1234567890);
    }
    
    
    

}
