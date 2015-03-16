package imageboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
@ImportResource("data_source.xml")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired DriverManagerDataSource dataSource;

    @Bean
    public JdbcTemplate jdbcTemplate() {
	    return new JdbcTemplate(this.dataSource);
    }

}

