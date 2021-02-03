package com.jojoldu.blogcode.hibernatebatchsize.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class BatchSizeWorkingJobConfig {
    public static final String JOB_NAME = "BatchSizeWorkingJobConfig";
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    private int chunkSize;

    @Value("${chunkSize:5}")
    public void setChunkSize(int chunkSize) {
        this.chunkSize = chunkSize;
    }

    @Bean(name = JOB_NAME)
    public Job jpaPagingItemReaderJob() {
        return jobBuilderFactory.get(JOB_NAME)
                .start(step())
                .build();
    }

    @Bean(name = JOB_NAME +"_step")
    public Step step() {
        return stepBuilderFactory.get(JOB_NAME +"_step")
                .<Teacher, Teacher>chunk(chunkSize)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean(name = JOB_NAME +"_reader")
    @StepScope
    public FixedJpaPagingItemReader<Teacher> reader() {
        FixedJpaPagingItemReader<Teacher> itemReader = new FixedJpaPagingItemReader<>();
        itemReader.setName(JOB_NAME +"_reader");
        itemReader.setEntityManagerFactory(entityManagerFactory);
        itemReader.setPageSize(chunkSize);
        itemReader.setQueryString("SELECT t FROM Teacher t");
        itemReader.setTransacted(true);

        return itemReader;
    }

    @Bean(name = JOB_NAME +"_processor")
    public ItemProcessor<Teacher, Teacher> processor() {
        return teacher -> {
            log.info("students count={}", teacher.getStudents().size());
            return teacher;
        };
    }

    private ItemWriter<Teacher> writer() {
        return list -> {
            for (Teacher teacher: list) {
                log.info("Current Teacher={}", teacher);
            }
        };
    }

}
