/**
*
* Copyright:   Copyright (c)2016
* Company:     YvesHe
* @version:    1.0
* Create at:   2018年12月13日
* Description:
*
* Author       YvesHe
*/
package com.yveshe.concurrent;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CyclicBarrier_Demo {
    public static void main(String[] args) {
        CyclicBarrier available = new CyclicBarrier(5, new TakePhotosTask());// 5个人聚餐
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int userIndex = 1; userIndex <= 5; userIndex++) {// 模拟5个用户
            executor.execute(new DineTask(available, userIndex));
        }
        executor.shutdown();
    }

    /**
     * 聚餐Task
     *
     * @author YvesHe
     *
     */
    public static class DineTask implements Runnable {
        private final CyclicBarrier cyclicBarrier;
        private final int userIndex;

        public DineTask(CyclicBarrier semaphore, int userIndex) {
            this.cyclicBarrier = semaphore;
            this.userIndex = userIndex;
        }

        @Override
        public void run() {
            try {
                // 每个线程都需要执行逻辑
                echo("用户[" + userIndex + "]从家里出发...");
                TimeUnit.SECONDS.sleep(new Random().nextInt(9) + 1);
                echo("用户[" + userIndex + "]到达聚餐地点.");
                cyclicBarrier.await();

                // 控制安全点的后逻辑只执行一次
                if (userIndex == 5) {
                    echo("人到齐了,大家一起开始吃饭!");
                }
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 拍照Task
     *
     * @author YvesHe
     *
     */
    public static class TakePhotosTask implements Runnable {

        @Override
        public void run() {
            echo("大家一起先拍个照片!");
        }
    }

    public static void echo(String msg) {
        System.out.println(msg);
    }
}

/* 结果 */
// 用户[1]从家里出发...
// 用户[3]从家里出发...
// 用户[2]从家里出发...
// 用户[5]从家里出发...
// 用户[4]从家里出发...
// 用户[2]到达聚餐地点.
// 用户[5]到达聚餐地点.
// 用户[3]到达聚餐地点.
// 用户[4]到达聚餐地点.
// 用户[1]到达聚餐地点.
// 大家一起先拍个照片!
// 人到齐了,大家一起开始吃饭!
