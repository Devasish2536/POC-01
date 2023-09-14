package com.example.demo.config;

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

import com.example.demo.entities.Employee;
import com.example.demo.processor.EmployeeProcessor;
import com.example.demo.repository.EmployeeRepository;

@Configuration
@EnableBatchProcessing
public class EmployeeConfiguration {
	@Autowired
	private EmployeeRepository repository;
	@Autowired
	private StepBuilderFactory stepFactory;
	@Autowired
	private JobBuilderFactory jobFactory;

	@Bean
	public EmployeeProcessor processor() {
		return new EmployeeProcessor();
	}

	@Bean
	public FlatFileItemReader<Employee> itemReader() {
		FlatFileItemReader<Employee> reader = new FlatFileItemReader<>();
		reader.setResource(new FileSystemResource("src/main/resources/Employee.csv"));
		reader.setLinesToSkip(1);
		reader.setLineMapper(lineMapper());
		return reader;
	}

	private LineMapper<Employee> lineMapper() {
		DefaultLineMapper<Employee> mapper = new DefaultLineMapper<>();
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter(",");
		lineTokenizer.setNames("Id", "Name", "Salary", "Designation", "Department", "Address");
		lineTokenizer.setStrict(false);
		BeanWrapperFieldSetMapper<Employee> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(Employee.class);
		mapper.setLineTokenizer(lineTokenizer);
		mapper.setFieldSetMapper(fieldSetMapper);
		return mapper;
	}

	@Bean
	public RepositoryItemWriter<Employee> itemWriter() {
		RepositoryItemWriter<Employee> writer = new RepositoryItemWriter<>();
		writer.setRepository(repository);
		writer.setMethodName("save");
		return writer;
	}

	@Bean
	public Step step() {
		return stepFactory.get("Batch Step-1").<Employee, Employee>chunk(3).reader(itemReader()).processor(processor())
				.writer(itemWriter()).build();
	}

	@Bean
	public Job job() {
		return jobFactory.get("Batch Job-1").flow(step()).end().build();
	}

}
