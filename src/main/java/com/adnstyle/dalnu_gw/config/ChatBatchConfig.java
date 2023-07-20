package com.adnstyle.dalnu_gw.config;

import com.adnstyle.dalnu_gw.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * SpringBoot Batch
 */

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableBatchProcessing
public class ChatBatchConfig {
    
    private final JobBuilderFactory jobBuilderFactory;
    
    private final StepBuilderFactory stepBuilderFactory;
    
    private final RedisService redisService;
    
    
    @Bean
    public Job chatBatchJob() {
        Job chatBatchJob = jobBuilderFactory.get("chatBatchJob")
            .start(chatBatchStep())
            .build();
        
        return chatBatchJob;
    }
    
    
    public Step chatBatchStep() {
        log.info("======== Step ========");
        return stepBuilderFactory.get("chatBatchStep")
            .tasklet((contribution, chunkContext) -> {
                log.info("ChatBatchConfig 의 chatBatchStep() ");
                log.info("=========redisToDB() start ==========");
                redisService.redisToDB();
                log.info("=========redisToDB() end ==========");
                return RepeatStatus.FINISHED;
            })
            .build();
    }
}
