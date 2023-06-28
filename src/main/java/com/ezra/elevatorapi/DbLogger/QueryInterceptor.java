package com.ezra.elevatorapi.DbLogger;

import com.ezra.elevatorapi.entity.DatabaseLogsSaver;
import com.ezra.elevatorapi.repository.QueryLogRepository;
import com.ezra.elevatorapi.utils.APIUtils;
import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueryInterceptor implements StatementInspector {

    @Autowired
    private QueryLogRepository queryLogRepository;

    @Override
    public String inspect(String sql) {
        // Get information about the caller (e.g., username, location)
        String callerName = APIUtils.getLoggedInUser.get(); // Get the caller name from your authentication context
        String callerLocation = APIUtils.USER_LOCATION; // Get the caller location from your request context

        // Log the SQL query with caller information
        DatabaseLogsSaver queryLog = new DatabaseLogsSaver();
        queryLog.setQueryText(sql);
        queryLog.setCallerName(callerName);
        queryLog.setCallerLocation(callerLocation);

        queryLogRepository.save(queryLog);

        return sql;
    }
}
