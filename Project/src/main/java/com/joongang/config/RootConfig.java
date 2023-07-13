package com.joongang.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


@Configuration //한꺼번에 추가 
@ComponentScan(basePackages= {"com.joongang.service","com.joongang.task",
								"com.joongang.aop"})
@EnableAspectJAutoProxy
@EnableScheduling
@EnableTransactionManagement

@MapperScan(basePackages= {"com.joongang.mapper"})
public class RootConfig {
	
	@Bean //하나씩 추가
	public DataSource dataSource() {
		HikariConfig hikariConfig= new HikariConfig();
		//hikariConfig.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		//hikariConfig.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:XE");
		hikariConfig.setDriverClassName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
		hikariConfig.setJdbcUrl("jdbc:log4jdbc:oracle:thin:@localhost:1521:XE");
		hikariConfig.setUsername("spring1");
		hikariConfig.setPassword("1234");
		
		HikariDataSource dataSource = new HikariDataSource(hikariConfig);
		return dataSource;
	}
	@Bean 
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(dataSource());
		return (SqlSessionFactory) sqlSessionFactory.getObject();
	}
	@Bean
	public DataSourceTransactionManager txManager() {
		return new DataSourceTransactionManager(dataSource());
	}
}
