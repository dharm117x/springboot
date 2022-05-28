package com.example.config;

import java.util.Collections;
import java.util.Random;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.example.listener.BatchJobListener;
import com.example.mapper.EmployeeRowMapper;
import com.example.model.Employee;
import com.example.step.ConsoleItemWriter;

@Configuration
public class MultiStepBatchConfig {

	private String[] headers = new String[] {"id", "name", "email", "phone" };
	
	@Value("file:D:/programs/DATA/inputData*.csv")
	private Resource[] inputResources;
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	JobBuilderFactory jobBuilderFactory;
	@Autowired
	StepBuilderFactory stepBuilderFactory;
	@Autowired
	BatchJobListener listener;

	
	@Bean
	public Job multiStepJob() {
		System.out.println("MultiStepBatchConfig.multiStepJob()");
		return jobBuilderFactory.get("multiStepJob")
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(step1()).next(step2()).end()
				.build();
	}

	// Read data from file and write to Database.
	@Bean
	public Step step1() {
		System.out.println("MultiStepBatchConfig.step1()");
		return stepBuilderFactory.get("multiStep1").<Employee, Employee>chunk(10)
				.reader(multiResourceItemReader())
				.processor(empProcessor())
				.writer(empDBWriter())
				.build();
	}

	// Read Data from Database and write to file.
	@Bean
	public Step step2() {
		System.out.println("MultiStepBatchConfig.step2()");
		return stepBuilderFactory.get("multiStep2").<Employee, Employee>chunk(10)
				.reader(empDBPagingReader())
				.writer(empFileWriter())
				.build();
	}

	@Bean
	public MultiResourceItemReader<Employee> multiResourceItemReader() 
	{
	    MultiResourceItemReader<Employee> resourceItemReader = new MultiResourceItemReader<Employee>();
	    resourceItemReader.setResources(inputResources);
	    resourceItemReader.setDelegate(multiFilereader());
	    return resourceItemReader;
	}
	

	@Bean
	public FlatFileItemReader<Employee> multiFilereader() {
		//Create reader instance
	    FlatFileItemReader<Employee> reader = new FlatFileItemReader<Employee>();
	    //Set number of lines to skips. Use it if file has header rows.
	    reader.setLinesToSkip(1);   
	    //Configure how each line will be parsed and mapped to different values
	    reader.setLineMapper(new DefaultLineMapper<Employee>() {
	        {
	            //3 columns in each row
	            setLineTokenizer(new DelimitedLineTokenizer() {
					{
	                    setNames(headers);
	                }
	            });
	            //Set values in Employee class
	            setFieldSetMapper(new BeanWrapperFieldSetMapper<Employee>() {
	                {
	                    setTargetType(Employee.class);
	                }
	            });
	        }
	    });
	    return reader;
	}
	
	@Bean
    public FlatFileItemWriter<Employee> empFileWriter() {

        return new FlatFileItemWriterBuilder<Employee>()
                .name("EmpFileWriter")
                .append(false)
                .resource(new FileSystemResource("D:/programs/DATA/empText.txt"))
                .lineAggregator(new DelimitedLineAggregator<Employee>() {
                    {
                        setDelimiter(";");
                        setFieldExtractor(new BeanWrapperFieldExtractor<Employee>() {
                            {
                                setNames(headers);
                            }
                        });
                    }
                })
                .build();
    }

	@Bean
	public ItemProcessor<Employee, Employee> empProcessor() {
		
		return new ItemProcessor<Employee, Employee>(){
			long min = 100000000000000l;
			long max = 999999999999999l;
			long range = max - min +1;

			@Override
			public Employee process(Employee employee) throws Exception {
				long fraction = (long) (range * (new Random()).nextDouble());
				employee.setId(""+fraction);
				return employee;
			}
		};
	}
	
	@Bean
	public JdbcBatchItemWriter<Employee> empDBWriter() {
		String sql = "insert into Employee(id, name,email,phone) values(:id, :name, :email, :phone)";
		return new JdbcBatchItemWriterBuilder<Employee>()
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Employee>())
				.sql(sql)
				.dataSource(dataSource)
				.build();
	}
	
	@Bean
	public ItemReader<Employee> empDBReader() {
		
		String sql = "select * from employee";
		return new JdbcCursorItemReaderBuilder<Employee>()
                .name("cursorItemReader")
                .dataSource(dataSource)
                .sql(sql)
                .rowMapper(new BeanPropertyRowMapper<>(Employee.class))
                .build();
	}
	
	@Bean
    public ItemReader<Employee> empDBPagingReader() {

        return new JdbcPagingItemReaderBuilder<Employee>()
                .name("empDBPaginReader")
                .dataSource(dataSource)
                .selectClause("SELECT * ")
                .fromClause("FROM Employee ")
//                .whereClause("WHERE ID <= 100000000 ")
                .sortKeys(Collections.singletonMap("ID", Order.ASCENDING))
                .rowMapper(new EmployeeRowMapper())
                .build();
    }

	@Bean
	public ItemWriter<Employee> consoleWriter() {
		
		return new ConsoleItemWriter<Employee>();
	}
}
