package team.gjz.database.redis.increID;

import org.junit.Test;

import java.util.ArrayList;

/**
 * @description:
 * @author: jxk
 * @create: 2020-04-07 15:43
 **/
public class TestId {
    @Test
    public void test() throws InterruptedException {
        long start = System.currentTimeMillis();
        IncreID testId = new IncreID();
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            int finalI = i+1;
            Thread thread = new Thread(() -> grtid( finalI, testId ), "th" + i);
            thread.start();
            threads.add(thread);
        }
        for (int i = 0; i < 6; i++) {
            threads.get(i).join();
        }
        System.out.println(System.currentTimeMillis()-start);
    }

    private void grtid(int finalI, IncreID testId){
        for (int i = 0; i <20000; i++) {
             testId.generateOrderId();
        }
    }
}
