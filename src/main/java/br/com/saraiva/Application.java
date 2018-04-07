package br.com.saraiva;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * 
 * @author Thiago Araujo  - tjca1@hotmail.com - 07/04/2018
 *
 */

@SuppressWarnings("unused")
@SpringBootApplication
@Configuration
@EnableAutoConfiguration 
@ComponentScan(basePackages = "br.com.saraiva")
@EnableJpaRepositories("br.com.saraiva.dao.jpa") 
public class Application  {

    private static final Class<Application> applicationClass = Application.class;

	private static final Logger log = LoggerFactory.getLogger(applicationClass);

	public static void main(String[] args) {
			SpringApplication.run(applicationClass, args);
	}

    
    

}
