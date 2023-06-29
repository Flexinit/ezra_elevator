//package com.ezra.elevatorapi.DbLogger;
//
//import com.ezra.elevatorapi.entity.QueryLog;
//import com.ezra.elevatorapi.repository.QueryLogRepository;
//import com.ezra.elevatorapi.utils.APIUtils;
//import org.hibernate.EmptyInterceptor;
//import org.springframework.beans.factory.annotation.Autowired;
//
//public class QueryInterceptor extends EmptyInterceptor {
//
//    @Autowired
//    private  QueryLogRepository queryLogRepository;
//
//    @Override
//    public String onPrepareStatement(String sql) {
//        // Save the SQL query to the database or perform any other action
//        saveQueryToDatabase(sql);
//        return super.onPrepareStatement(sql);
//    }
//
//    private void saveQueryToDatabase(String sql) {
//        QueryLog queryLog = new QueryLog();
//        queryLog.setQueryText(sql);
//        queryLog.setCallerName(APIUtils.getLoggedInUser.get());
//        queryLog.setCallerLocation(APIUtils.USER_LOCATION);
//
//        queryLogRepository.save(queryLog);
//
//    }
//}
