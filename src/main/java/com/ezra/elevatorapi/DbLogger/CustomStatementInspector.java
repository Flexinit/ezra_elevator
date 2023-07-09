package com.ezra.elevatorapi.DbLogger;

import com.ezra.elevatorapi.entity.QueryLog;
import com.ezra.elevatorapi.repository.QueryLogRepository;
import com.ezra.elevatorapi.utils.APIUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
public class CustomStatementInspector implements StatementInspector {

    public static List<QueryLog> queryLogArrayList = new ArrayList<>();

    @Override
    public String inspect(String sql) {
        QueryLog queryLog = new QueryLog();
        saveQueryLog(queryLog, sql);
        log.info("EXECUTED SQL Query: {}", sql);
        return sql;
    }

    private void saveQueryLog(QueryLog queryLog, String sql){
        // check if query is modifying and change sql query
        queryLog.setId(ThreadLocalRandom.current().nextLong(100));
        queryLog.setQueryText(sql);
        queryLog.setCallerName(APIUtils.getLoggedInUser.get());
        queryLog.setCallerLocation(APIUtils.USER_LOCATION);
        queryLog.setCreatedAt(LocalDateTime.now());

        queryLogArrayList.add(queryLog);
    }
}

