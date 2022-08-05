package com.coderlucifar.func.retry.demo.test;

import com.coderlucifar.func.retry.demo02.HttpExecutor;
import com.coderlucifar.func.retry.demo03.CustomRetryTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RetryApplicationTest {

    @Resource
    HttpExecutor httpExecutor;

    @Resource
    CustomRetryTemplate customRetryTempate;

    /**
     * 测试spring-retry框架的异常重试功能
     */
    @Test
    public void test_spring_retry() {
        httpExecutor.doGet(null);
    }

    /**
     * 测试基于队列实现的自定义重试方法
     */
    @Test
    public void test_custom_retry() {
        Object o = customRetryTempate.doRetry(() -> {
            try {
                httpExecutor.doGet2(null);
                return null;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        System.out.println("------------------------------------------");
        System.out.println(o);
    }

    @Test
    public void test_custom_retry2() {
        String s1 = customRetryTempate.doRetry(() -> {
            String s = httpExecutor.doGet3("hi");
            return s;
        });
        System.out.println("------------------------------------------");
        System.out.println(s1);

    }

    @Test
    public void test_custom_retry3() {
        new CustomRetryTemplate().doRetry(() -> {
            httpExecutor.doGet2(null);
            return null;
        });
    }

    @Test
    public void test_custom_retry4() {
        customRetryTempate.doRetry(() -> {
            // 推送数据到接口中
            try {
                System.out.println("hello, world");
                throw new RuntimeException("【数据推送】数据推送失败!");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }


}
