package com.xinwei.commAccessDb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan ("com.xinwei.commAccessDb")
@MapperScan ("com.xinwei.commAccessDb.mapper")
@ImportResource ({ "classpath:hessian/hessian-client.xml", "classpath:hessian/hessian-server.xml" })
public class LotteryCommAccessDbApplication {

	public static void main(String[] args) {
		try {
		SpringApplication.run(LotteryCommAccessDbApplication.class, args);
		}
		catch(Throwable e)
		{
		   e.printStackTrace();	
		}
	}
}
