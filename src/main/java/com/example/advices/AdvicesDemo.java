package com.example.advices;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
class MyAspect {

    @Autowired
    public Worker worker;

    @Pointcut(value = "execution(public * com.ex*..*.*(..))")
    public void matchingAll(){}

    @AfterThrowing(pointcut = "matchingAll()", throwing = "e")
    public void myAdvice(RuntimeException e){
        Thread.setDefaultUncaughtExceptionHandler((t, e1) -> System.out.println("Caught " + e1.getMessage()));
        System.out.println("Worker returned " + worker.print());
    }
}

@Component
class Worker {

    public static int value = 0;

    public int print() {
        if (value++ == 0) {
            System.out.println("Throwing exception");
            throw new RuntimeException("Hello world");
        } else {
            return value;
        }
    }
}

@SpringBootApplication
@EnableAspectJAutoProxy
public class AdvicesDemo {

    public static void main(String[] args) {
        final ConfigurableApplicationContext applicationContext = SpringApplication.run(AdvicesDemo.class);
        final Worker worker = applicationContext.getBean(Worker.class);
        System.out.println("Worker returned " + worker.print());
        System.out.println("All done");
    }

}
