package org.example.config;

import lombok.RequiredArgsConstructor;
import org.example.domain.PricePerDay;
import org.example.repository.CompanyRepository;
import org.example.repository.PricePerDayRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class BatchConfig {

    private final CompanyRepository companyRepository;
    private final PricePerDayRepository pricePerDayRepository;

    @Bean
    public Job todayScheduleJob(JobRepository jobRepository, Step todayScheduleStep){
        return new JobBuilder("today-schedule-job", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(todayScheduleStep)
                .build();
    }

    @Bean
    public Step todayScheduleStep(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new StepBuilder("today-schedule-step", jobRepository)
                .<List<PricePerDay>, List<PricePerDay>>chunk(1, transactionManager)
                .reader(reader())
                .writer(writer())
                .build();
    }

    @Bean
    public PriceItemReader reader(){
        return new PriceItemReader(companyRepository);
    }

    @Bean
    public PriceItemWriter writer(){
        return new PriceItemWriter(pricePerDayRepository);
    }
}
