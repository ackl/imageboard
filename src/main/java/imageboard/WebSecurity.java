package imageboard;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {

      auth.jdbcAuthentication().dataSource(dataSource)
        .passwordEncoder(passwordEncoder())
        .usersByUsernameQuery(
            "select username, password, enabled from users where username=?")
        .authoritiesByUsernameQuery(
            "select username, role from user_roles where username=?");
    }

    @Bean
	public PasswordEncoder passwordEncoder(){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
	  http.authorizeRequests()
          //.antMatchers("/users/**").permitAll()
          //.antMatchers("/users/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
          .antMatchers("/j_spring_security_check*").permitAll()
          .antMatchers("/login*").permitAll()
          .antMatchers("/public/**").permitAll()
          .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
          .antMatchers("/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
		.and()
		    .formLogin().loginPage("/login").failureUrl("/login?error").loginProcessingUrl("/login.do")
		    .usernameParameter("username").passwordParameter("password")
		.and()
		    .logout().logoutUrl("/logout").logoutSuccessUrl("/login?logout");
        //.and()
            //.exceptionHandling().accessDeniedPage("/403");
	}
}
