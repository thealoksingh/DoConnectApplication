package com.hcl.doconnect.security;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

import com.hcl.doconnect.filter.JwtFilter;

@Configuration
@EnableWebSecurity  //applies login and makes it mandatory
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	DataSource dSource;

	@Autowired
	JwtFilter jwtFilter;

	private static final String[] AUTH_WHITE_LIST = {

            "/v3/api-docs/**",

            "/swagger-ui/**",

            "/v2/api-docs/**",

            "/swagger-resources/**",

            "/swagger-ui.html",

//            "/api/user/**",
            "/api/admin/**"

    };



	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception 

	{

		auth.inMemoryAuthentication()

		.withUser(User.withUsername("user4").password("user456").roles("user"));

	}
	
	

	@Bean
	public PasswordEncoder getEncoder()

	{

		return NoOpPasswordEncoder.getInstance();

	}

	@Override
	public void configure(WebSecurity web) throws Exception 

	{

		web.ignoring().antMatchers("/h2-console/**");

		//	.antMatchers("/swagger-ui.html/**"); 

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

	    http.csrf().disable()

	        .authorizeRequests()

	            .antMatchers(AUTH_WHITE_LIST).permitAll() 

//	            .antMatchers("/api/owners/login","/api/owners/register").permitAll()  

	            .antMatchers("/api/user/login","/api/user/register").permitAll()

	            .anyRequest().authenticated()

	            .and()

	            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

	}
 

	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}