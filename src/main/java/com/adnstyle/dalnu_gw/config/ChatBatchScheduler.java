package com.adnstyle.dalnu_gw.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatBatchScheduler {
    
    private final JobLauncher jobLauncher;
    
    private final ChatBatchConfig chatBatchConfig;
    
    @Scheduled(cron = "0 */1 * * *  *")// 1분마다 실행
    public void runChatBatchJob() {
        
        Map<String, JobParameter> jobPMap = new HashMap<>();
        jobPMap.put("time", new JobParameter(System.currentTimeMillis()));
        jobPMap.put("batchType", new JobParameter("chatBatch"));
        JobParameters jobParameters = new JobParameters(jobPMap);
        
        try {
            jobLauncher.run(chatBatchConfig.chatBatchJob(), jobParameters);
        } catch (JobInstanceAlreadyCompleteException | JobExecutionAlreadyRunningException |
                 JobParametersInvalidException | JobRestartException e) {
            log.info("error 발생 {}", e.getMessage());
        }
    }
}