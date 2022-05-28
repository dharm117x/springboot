package com.example.config;

import java.util.Random;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.validation.BindException;

import com.example.listener.BatchJobListener;
import com.example.model.Order;

//@Configuration
public class OrderBatchconfig {

	private String[] names= {"OrderRef","Amount","OrderDate","Note"};

	@Autowired
	JobBuilderFactory jobBuilderFactory;
	@Autowired
	StepBuilderFactory stepBuilderFactory;

	
	@Bean
	public Job orderJob(BatchJobListener listener, @Qualifier("orderStep") Step step) {
		
		return jobBuilderFactory.get("ORDER_BATCH_JOB")
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(step)
				.end()
				.build();
	}

	@Bean(name = "orderStep")
	public Step orderStep(JdbcBatchItemWriter<Order> orderWriter) {
		return stepBuilderFactory.get("ORDER_BATCH_STEP")
				.<Order, Order>chunk(1000)
				.reader(fileReader())
				.processor(orderProcessor())
				.writer(orderWriter)
				.build();
	}
	
	@Bean
	public FlatFileItemReader<Order> fileReader() {
		return new FlatFileItemReaderBuilder<Order>()
				.name("ORDER_ITEM_READER")
//				.resource(new ClassPathResource("csv/order.csv"))
				.resource(new FileSystemResource("D:/programs/DATA/order.csv"))
				.linesToSkip(1)
				.delimited()
				.names(names)
				.lineMapper(lineMapper())
				.fieldSetMapper(new BeanWrapperFieldSetMapper<Order>() {
					{
						setTargetType(Order.class);
					}
				}).build();
	}

	@Bean
	public LineMapper<Order> lineMapper() {
		DefaultLineMapper<Order> mapper = new DefaultLineMapper<>();
		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
		tokenizer.setDelimiter(",");
		tokenizer.setStrict(false);
		tokenizer.setNames(names);
		
		mapper.setLineTokenizer(tokenizer);
		mapper.setFieldSetMapper(orderFieldSetMapper());
		return mapper;
	}



	@Bean
	public FieldSetMapper<Order> orderFieldSetMapper() {
		return new FieldSetMapper<Order>() {

			@Override
			public Order mapFieldSet(FieldSet fieldSet) throws BindException {
				Order order = new Order();
				if(!fieldSet.readString("OrderRef").isEmpty()) {
					order.setOrderRef(fieldSet.readString("OrderRef"));
					order.setAmount(fieldSet.readString("Amount"));
					order.setOrderDate(fieldSet.readDate("OrderDate", "dd-MM-yyyy"));
					order.setNote(fieldSet.readString("Note"));
				}
				return order;
			}
			
		};
	}

	@Bean
	public ItemProcessor<Order, Order> orderProcessor() {
		
		return new ItemProcessor<Order, Order>(){
			long min = 100000000000000l;
			long max = 999999999999999l;
			long range = max - min +1;

			@Override
			public Order process(Order order) throws Exception {
				long fraction = (long) (range * (new Random()).nextDouble());
				order.setOrderId(fraction);
				return order;
			}
		};
	}
	
	@Bean
	public JdbcBatchItemWriter<Order> orderWriter(final DataSource dataSource) {
		String sql = "insert into batch_order(orderid, order_ref,amount, order_date, note) values(:orderId, :orderRef, :amount, :orderDate, :note)";
//		String sql = "insert into batch_order(orderid, order_ref, amount, order_date, note) values(?, ?, ?, ?, ?)";
		return new JdbcBatchItemWriterBuilder<Order>()
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Order>())
				.sql(sql)
				.dataSource(dataSource)
				.build();
	}

}
