package com.example.demo.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.example.demo.entities.EntitiesClass;
import com.example.demo.processor.EntitiesItemProcessor;
import com.example.demo.repository.EntitiesRepository;

@Configuration
@EnableBatchProcessing
public class MyBatchConfig {
	@Autowired
	private JobBuilderFactory jobFactory;
	@Autowired
	private StepBuilderFactory stepFactory;
	@Autowired
	private EntitiesRepository repository;

	@Bean
	public FlatFileItemReader<EntitiesClass> itemReader() {
		FlatFileItemReader<EntitiesClass> reader = new FlatFileItemReader<>();
		reader.setResource(new FileSystemResource("src/main/resources/Customer.csv"));
		reader.setLinesToSkip(1);
		reader.setLineMapper(linemapper());
		return reader;
	}

	private LineMapper<EntitiesClass> linemapper() {
		DefaultLineMapper<EntitiesClass> linemapper = new DefaultLineMapper<>();
		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer(",");
		tokenizer.setStrict(false);
		tokenizer.setNames("Id", "Name", "Email", "Contact", "Address");
		BeanWrapperFieldSetMapper<EntitiesClass> setmapper = new BeanWrapperFieldSetMapper<>();
		setmapper.setTargetType(EntitiesClass.class);
		linemapper.setLineTokenizer(tokenizer);
		linemapper.setFieldSetMapper(setmapper);
		return linemapper;
	}

	@Bean
	public RepositoryItemWriter<EntitiesClass> itemWriter() {
		RepositoryItemWriter<EntitiesClass> writer = new RepositoryItemWriter<>();
		writer.setRepository(repository);
		writer.setMethodName("save");
		return writer;
	}

	@Bean
	public EntitiesItemProcessor itemProcessor() {
		return new EntitiesItemProcessor();
	}

	@Bean
	public Step step() {
		return stepFactory.get("Step-1").<EntitiesClass, EntitiesClass>chunk(3).reader(itemReader())
				.processor(itemProcessor()).writer(itemWriter()).build();
	}

	@Bean
	public Job job() {
		return jobFactory.get("Job-1").flow(step()).end().build();
	}
}
