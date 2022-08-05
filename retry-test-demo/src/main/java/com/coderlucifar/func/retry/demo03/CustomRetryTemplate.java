package com.coderlucifar.func.retry.demo03;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Component
public class CustomRetryTemplate {

    private static final Logger log = LoggerFactory.getLogger(CustomRetryTemplate.class);

    @Value("${coderlucifar.retry.interval}")
    private Long[] interval;

    private Queue<Long> retryIntervalTimes;

    public <T> T doRetry(Supplier<T> supplier) {
        retryIntervalTimes = new LinkedList<>(Arrays.asList(interval));
        try {
            return supplier.get();
        } catch (Exception ex) {
            while (!retryIntervalTimes.isEmpty()) {
                Long interval = retryIntervalTimes.poll();
                try {
                    TimeUnit.MILLISECONDS.sleep(interval);
                } catch (InterruptedException e) {
                    log.info(e.getMessage());
                }
                try {
                    return supplier.get();
                } catch (Exception e) {
                    if (retryIntervalTimes.isEmpty()) {
                        log.info(e.getMessage() + "请求异常");
                        throw e;
                    }
                }
            }
        }
        return null;
    }

}
