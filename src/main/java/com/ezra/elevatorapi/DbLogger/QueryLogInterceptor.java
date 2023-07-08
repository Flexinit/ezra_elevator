package com.ezra.elevatorapi.DbLogger;

import com.ezra.elevatorapi.entity.QueryLog;
import com.ezra.elevatorapi.repository.QueryLogRepository;
import com.ezra.elevatorapi.utils.APIUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.EmptyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

@Slf4j
@Component
public class QueryLogInterceptor extends EmptyInterceptor {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private QueryLogRepository queryLogRepository;

    @Override
    public String onPrepareStatement(String sql) {

        QueryLog queryLog = new QueryLog();
        queryLog.setQueryText(sql);
        queryLog.setCallerName(APIUtils.getLoggedInUser.get());
        queryLog.setCallerLocation(APIUtils.USER_LOCATION);

        entityManager.persist(queryLog);
        queryLogRepository.save(queryLog);


        return super.onPrepareStatement(sql);
    }
}
