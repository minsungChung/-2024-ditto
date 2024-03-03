package org.example.config;

import lombok.RequiredArgsConstructor;
import org.example.domain.PricePerDay;
import org.example.repository.CompanyRepository;
import org.example.repository.PriceBulkRepository;
import org.example.repository.PricePerDayRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.boot.autoconfigure.batch.JobLauncherApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@ConditionalOnMissingBean(value = DefaultBatchConfiguration.class, annotation = EnableBatchProcessing.class)
public class BatchConfig {

    private final CompanyRepository companyRepository;
    private final PricePerDayRepository pricePerDayRepository;
    private final PriceBulkRepository priceBulkRepository;

    @Bean
    public Job fiveYearsPriceJob(JobRepository jobRepository, Step fiveYearsPriceStep){
        return new JobBuilder("five-years", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(fiveYearsPriceStep)
                .build();
    }

    @Bean
    public Step fiveYearsPriceStep(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new StepBuilder("five-years-step", jobRepository)
                .<List<PricePerDay>, List<PricePerDay>>chunk(1, transactionManager)
                .reader(fiveReader())
                .writer(fiveWriter())
                .build();
    }
//    @Bean
//    public Job todayScheduleJob(JobRepository jobRepository, Step todayScheduleStep){
//        return new JobBuilder("spring-batch", jobRepository)
//                .incrementer(new RunIdIncrementer())
//                .start(todayScheduleStep)
//                .build();
//    }
//
//    @Bean
//    public Step todayScheduleStep(JobRepository jobRepository, PlatformTransactionManager transactionManager){
//        return new StepBuilder("today-schedule-step", jobRepository)
//                .<List<PricePerDay>, List<PricePerDay>>chunk(1, transactionManager)
//                .reader(reader())
//                .writer(writer())
//                .build();
//    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "spring.batch.job", name = "enabled", havingValue = "true", matchIfMissing = true)
    public JobLauncherApplicationRunner jobLauncherApplicationRunner(JobLauncher jobLauncher, JobExplorer jobExplorer,
                                                                     JobRepository jobRepository, BatchProperties properties, Collection<Job> jobs){
        JobLauncherApplicationRunner runner = new JobLauncherApplicationRunner(jobLauncher, jobExplorer, jobRepository);
        String jobNames = properties.getJob().getName();
        if (StringUtils.hasText(jobNames)){
            if (jobs.stream().map(Job::getName).noneMatch(s -> s.equals(jobNames))) {
                throw new IllegalArgumentException(jobNames + "는 등록되지 않은 job name입니다.");
            }
            runner.setJobName(jobNames);
        }
        return runner;
    }

    @Bean
    public FiveYearPriceItemReader fiveReader(){
        return new FiveYearPriceItemReader(companyRepository);
    }

    @Bean
    public FiveYearPriceItemWriter fiveWriter(){
        return new FiveYearPriceItemWriter(priceBulkRepository);
    }
//    @Bean
//    public PriceItemReader reader(){
//        return new PriceItemReader(companyRepository);
//    }

//    @Bean
//    public PriceItemWriter writer(){
//        return new PriceItemWriter(pricePerDayRepository);
//    }
}
