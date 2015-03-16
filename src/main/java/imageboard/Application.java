package imageboard;

import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.*;

import imageboard.dao.UsersDao;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    public void run(String... args) throws Exception {
	    	    
	    int num = jdbc.queryForInt("SELECT COUNT(*) FROM information_schema.tables WHERE table_type = 'base table'");

	    if (num == 0) jdbc.execute("CREATE TABLE users (id INTEGER NOT NULL PRIMARY KEY, name VARCHAR(255),	pass VARCHAR(255), image_url VARCHAR(255), keycode VARCHAR(255) NOT NULL PRIMARY KEY, expiry_date INTEGER NOT NULL)");
	    UsersDao dao = new UsersDao();
	    dao.insertUser("keycode", 1234567);

    }

    @Bean
    public DriverManagerDataSource initDataSource() {
	    DriverManagerDataSource data = new DriverManagerDataSource();
	    data.setDriverClassName("com.mysql.jdbc.Driver");
	    data.setUrl("jdbc:mysql://localhost:3306/db");
	    data.setUsername("root");
	    data.setPassword("420");

	    return data;
    }

    @Autowired
    JdbcTemplate jdbc;

}

