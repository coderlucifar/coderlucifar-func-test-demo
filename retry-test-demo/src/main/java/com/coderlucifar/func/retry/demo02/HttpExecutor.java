package com.coderlucifar.func.retry.demo02;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Component
public class HttpExecutor {

    private final Logger log = LoggerFactory.getLogger(HttpExecutor.class);

    @Retryable(value = NullPointerException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000, multiplier = 3))
    public void doGet(String param) {
        log.info("send get request");
        int length = param.length();
    }

    public void doGet2(String param) {
        log.info("send get request");
        int length = param.length();
    }

    public String doGet3(String param) {
        log.info("send get request");
        return "hello, coderlucifar.";
    }

}
