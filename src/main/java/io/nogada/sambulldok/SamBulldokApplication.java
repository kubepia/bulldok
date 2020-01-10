package io.nogada.sambulldok;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@EnableAutoConfiguration
@SpringBootApplication
@RestController
public class SamBulldokApplication {

	public static void main(String[] args) {
		SpringApplication.run(SamBulldokApplication.class, args);
	}
	@ResponseBody
	@GetMapping(value = "/health")
	public String health() {
	   return "ok";
	}

	// logger filter setting
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public FilterRegistrationBean loggingFilter() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();

		registrationBean.setFilter(new MdcLoggingFilter());

		return registrationBean;
	}
}
