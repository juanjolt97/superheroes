package com.example.superheroes.annotation;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;


/**
 * A custom Aspect for measuring the execution time of methods annotated with @ExecutionTime.
 */
@Component
@Aspect
@Slf4j
public class ExecutionTimeAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Measures the execution time of methods annotated with @ExecutionTime.
     *
     * @param joinPoint      The ProceedingJoinPoint representing the method call.
     * @param executionTime The @ExecutionTime annotation applied to the method.
     * @return The result of the method call.
     * @throws Throwable If an error occurs during method execution.
     */
    @Around("@annotation(executionTime)")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint, ExecutionTime executionTime) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long executionTimeMillis  = endTime - startTime;

        String methodName = joinPoint.getSignature().toShortString();
        String message = "Method " + methodName + " execute in " + executionTimeMillis + "ms";

        logger.info(message);

        return result;
    }
}
