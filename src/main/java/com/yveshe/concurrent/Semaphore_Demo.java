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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author YvesHe
 *
 */
public class Semaphore_Demo {
    public static void main(String[] args) {
        Semaphore available = new Semaphore(2, true);// 限流2个窗口,使用公平机制
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int userIndex = 1; userIndex <= 10; userIndex++) {// 10个用户
            executor.execute(new SemaphoreTask(available, userIndex));
        }
        executor.shutdown();
    }

    public static class SemaphoreTask implements Runnable {
        private final Semaphore semaphore;
        private final int userIndex;

        public SemaphoreTask(Semaphore sema, int userIndex) {
            this.semaphore = sema;
            this.userIndex = userIndex;
        }

        public void run() {
            try {
                // 获取1个信号量许可(如果2个都被获取了,则该线程等待)
                semaphore.acquire();

                echo("用户[" + userIndex + "],进入窗口,开始买票...");
                TimeUnit.SECONDS.sleep(new Random().nextInt(9) + 1);
                echo("用户[" + userIndex + "]结束买票,即将离开\n");

                // 释放1个信号量许可
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public static void echo(String msg) {
            System.out.println(msg);
        }
    }

}

/* 结果 */
// 用户[2],进入窗口,开始买票...
// 用户[1],进入窗口,开始买票...
// 用户[1]结束买票,即将离开
//
// 用户[3],进入窗口,开始买票...
// 用户[3]结束买票,即将离开
//
// 用户[4],进入窗口,开始买票...
// 用户[2]结束买票,即将离开
//
// 用户[5],进入窗口,开始买票...
// 用户[4]结束买票,即将离开
//
// 用户[6],进入窗口,开始买票...
// 用户[5]结束买票,即将离开
//
// 用户[7],进入窗口,开始买票...
// 用户[7]结束买票,即将离开
//
// 用户[8],进入窗口,开始买票...
// 用户[8]结束买票,即将离开
//
// 用户[6]结束买票,即将离开
//
// 用户[9],进入窗口,开始买票...
// 用户[10],进入窗口,开始买票...
// 用户[10]结束买票,即将离开
//
// 用户[9]结束买票,即将离开
