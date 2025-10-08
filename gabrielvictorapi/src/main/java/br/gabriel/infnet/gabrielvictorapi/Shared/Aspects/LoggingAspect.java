package br.gabriel.infnet.gabrielvictorapi.Shared.Aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import br.gabriel.infnet.gabrielvictorapi.Shared.Anotations.LogMask;
import br.gabriel.infnet.gabrielvictorapi.Shared.Anotations.LogMaskResult;
import br.gabriel.infnet.gabrielvictorapi.Shared.Exceptions.BadRequestException;
import br.gabriel.infnet.gabrielvictorapi.Shared.Exceptions.OperationException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.StringJoiner;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* br.gabriel.infnet.domain.Application.Handlers..*.*(..))")
    public void applicationHandlersPointcut() {}
    @Pointcut("execution(* br.gabriel.infnet.domain.Infraestructure.Repositories..*.*(..))")
    public void infrastructureRepositoriesPointcut() {}
    @Pointcut("applicationHandlersPointcut() || infrastructureRepositoriesPointcut()")
    public void allApplicationLayersPointcut() {}

    @Around("allApplicationLayersPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        
        StringJoiner argsJoiner = new StringJoiner(", ");
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        Object[] args = joinPoint.getArgs();

        for (int i = 0; i < args.length; i++) {
            boolean isMasked = false;
            for (Annotation annotation : parameterAnnotations[i]) {
                if (annotation instanceof LogMask) {
                    isMasked = true;
                    break;
                }
            }
            if (isMasked) {
                argsJoiner.add("[MASKED]");
            } else {
                argsJoiner.add(String.valueOf(args[i]));
            }
        }

        logger.info("Entrando: {}.{}() com argumentos = [{}]",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                argsJoiner.toString()); 

        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long timeTaken = System.currentTimeMillis() - startTime;

        String resultString;
        
        if (method.isAnnotationPresent(LogMaskResult.class)) {
            resultString = "[RESULTADO MASCARADO]";
        } else {
            resultString = String.valueOf(result);
        }

        logger.info("Saindo: {}.{}() com resultado = {}; Executado em {}ms",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                resultString,
                timeTaken);

        return result;
    }

    @AfterThrowing(pointcut = "applicationHandlersPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        String methodName = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();

        if (e instanceof BadRequestException || e instanceof OperationException) {
            
            logger.warn("Exceção de negócio tratada em {}: {} - Mensagem: {}",
                    methodName,
                    e.getClass().getSimpleName(),
                    e.getMessage());
        } else {
            logger.error("Exceção inesperada em {}(): {}",
                    methodName,
                    e.getMessage(),
                    e); 
        }
    }
}
