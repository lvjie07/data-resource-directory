package com.ruizhi.data.bootstrap;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.ruizhi.data")
@MapperScan("com.ruizhi.data.dal.mapper")
public class DataResourceDirectoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataResourceDirectoryApplication.class, args);
	}

}
