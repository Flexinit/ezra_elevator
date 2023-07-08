package com.ezra.elevatorapi.DbLogger;

import com.ezra.elevatorapi.entity.QueryLog;
import com.ezra.elevatorapi.utils.APIUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class QueryLogListener {


    @PrePersist
    @PreUpdate
    public void populateTrackingInfo(Object entity) {
        if (entity instanceof QueryLog) {
            QueryLog queryLog = (QueryLog) entity;
            queryLog.setCallerName(APIUtils.getLoggedInUser.get());
            queryLog.setCallerLocation(APIUtils.USER_LOCATION);
        }
    }
}
