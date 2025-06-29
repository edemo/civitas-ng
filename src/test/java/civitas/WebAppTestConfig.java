package civitas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.map.repository.config.EnableMapRepositories;

@SpringBootApplication
@PropertySource("classpath:test.properties")
@ComponentScan("civitas")
@EnableMapRepositories("civitas")
public class WebAppTestConfig {

	public static void main(String[] args) {
		SpringApplication.run(WebAppTestConfig.class, args);
	}
}
