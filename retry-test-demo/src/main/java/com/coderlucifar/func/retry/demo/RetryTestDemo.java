package com.coderlucifar.func.retry.demo;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class RetryTestDemo {
    public static void main(String[] args) {
// 间隔时间
        long retryIntervalTime = 2000;   // ms
        // 重试次数 (这个值后面通过配置文件注入)
        int retryCount = 3;
        final int count = retryCount;
        long start = System.currentTimeMillis();
        while (retryCount >= 0) {
            try {
//                throw new RuntimeException("hahaha");
                if (retryCount < count) {
                    System.out.println("重试第" + (3-retryCount) + "次");
                }
//                throw new SocketException();

                BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(new File("/Users/coderlucifar/a.txt")));

                // 这里就会抛非IO异常
                int i = 1 / 0;

                // 如果执行成功就会直接归零
                retryCount = -1;
            } catch (IOException ex) {
                System.out.println("IO异常维持原有重试策略");
            } catch (Exception ex) {
                retryCount--;
                if (retryCount >=0) {
                    System.out.println("遇到非IO异常！" + "准备间隔" + retryIntervalTime + "ms" + "重试第" + (3-retryCount) + "次！");
                }else {
                    System.out.println(count + "次重试都失败！！！，抛出异常");
                }

                try {
                    Thread.sleep(retryIntervalTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        long now = System.currentTimeMillis();
        long l = now - start;
        System.out.println(l);

    }
}
