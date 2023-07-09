package com.ezra.elevatorapi.DbLogger;

import com.ezra.elevatorapi.repository.QueryLogRepository;
import com.ezra.elevatorapi.service.QueryLogsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.ezra.elevatorapi.DbLogger.CustomStatementInspector.queryLogArrayList;

@Slf4j
@Component
public record QueryLogSaver(QueryLogRepository queryLogRepository) {

    @Scheduled(fixedRate = 3000)// Runs every 5 seconds
    public void scheduleSavingQueryLogs() {
        try {

            log.info("SAVING QUERY LOGS TO DB");
            if (queryLogArrayList.size() != 0) {
                queryLogArrayList
                        .stream().forEach(
                                queryLogRepository::save
                        );
            }
            queryLogArrayList.clear();
        }catch (Exception ex){
            ex.printStackTrace();
        }
  }
}
