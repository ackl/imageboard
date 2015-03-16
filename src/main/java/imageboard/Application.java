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
	    DriverManagerDataSource data = new DriverManagerDataSource();
	    data.setDriverClassName("com.mysql.jdbc.Driver");
	    data.setUrl("jdbc:mysql://localhost:3306/db");
	    data.setUsername("root");
	    data.setPassword("420");

	    return new JdbcTemplate(this.dataSource);
    }

}

