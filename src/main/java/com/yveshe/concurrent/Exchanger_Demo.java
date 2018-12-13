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
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Exchanger_Demo {
    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<String>();
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(new ExchangerTask<String>(exchanger, "张三", "小红"));
        executor.execute(new ExchangerTask<String>(exchanger, "小红家属", "100万"));
        executor.shutdown();
    }

    /**
     * 交换Task
     *
     * @author YvesHe
     *
     */
    public static class ExchangerTask<String> implements Runnable {
        private final Exchanger<String> exchanger;
        private final String userName;// 交换者
        private final String exchangeContent;// 交换内容

        public ExchangerTask(Exchanger<String> exchanger, String userName, String exchangeContent) {
            this.exchanger = exchanger;
            this.userName = userName;
            this.exchangeContent = exchangeContent;
        }

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(new Random().nextInt(9) + 1);
                echo("[" + userName + "]到达交易地点");// 我们可以发现需要等到2个线程都到达交易点才会执行交换
                String returnValue = exchanger.exchange(exchangeContent);
                echo("[" + userName + "]使用" + exchangeContent + "交换得到" + returnValue);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static void echo(String msg) {
        System.out.println(msg);
    }
}

/* 结果 */
// [小红家属]到达交易地点
// [张三]到达交易地点
// [张三]使用小红交换得到100万
// [小红家属]使用100万交换得到小红