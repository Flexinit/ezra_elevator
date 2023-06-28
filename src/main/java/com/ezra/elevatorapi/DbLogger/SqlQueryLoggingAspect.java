package com.ezra.elevatorapi.DbLogger;

import com.ezra.elevatorapi.entity.QueryLog;
import com.ezra.elevatorapi.repository.QueryLogRepository;
import com.ezra.elevatorapi.utils.APIUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class SqlQueryLoggingAspect {
    private final QueryLogRepository queryLogRepository;

    public SqlQueryLoggingAspect(QueryLogRepository queryLogRepository) {
        this.queryLogRepository = queryLogRepository;
    }

    @AfterReturning(pointcut = "execution(* org.springframework.data.repository.Repository+.*(..)) && @annotation(org.springframework.data.jpa.repository.Query)", returning = "result")
    public void logQueryExecution(JoinPoint joinPoint, Object result) {
        String query = joinPoint.getSignature().toShortString();
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        LocalDateTime timestamp = LocalDateTime.now();

        QueryLog queryLog = new QueryLog();
        queryLog.setQueryText(query);
        queryLog.setCallerName(user);
        queryLog.setCallerLocation(APIUtils.USER_LOCATION);

        queryLogRepository.save(queryLog);
    }
}
