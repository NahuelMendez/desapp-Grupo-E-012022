package ar.edu.unq.desapp.grupoE.backenddesappapi;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(0)
public class LogInfo {

    static Logger logger = LoggerFactory.getLogger(LogInfo.class);

    @Pointcut("execution(* ar.edu.unq.desapp.grupoE.backenddesappapi.webservice.*.*(..))")
    public void methodsStarterServicePointcut(){
    }

    @Around(value = "methodsStarterServicePointcut()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("/////// AROUND START  logExecutionTime annotation //////");
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;
        logger.info("/////// " + joinPoint.getSignature() + " executed in " + executionTime + "ms");
        return proceed;
    }
}
