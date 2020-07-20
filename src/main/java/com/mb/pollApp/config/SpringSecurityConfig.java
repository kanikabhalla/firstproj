package com.mb.pollApp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.mb.pollApp.security.CustomUserDetails;
import com.mb.pollApp.security.JwtAuthenticationEntryPoint;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{

	//CUSTOM user details 
	@Autowired
	private CustomUserDetails userdetails;
	
	
	// jwt handler for handling unauthorized requests
	@Autowired 
	private JwtAuthenticationEntryPoint unauthorizedHandler;
	
	@Autowired 
	private JwtAuthFilter jwtAuthFilter;
	
	//authorization
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.cors().and().csrf().disable()
		.exceptionHandling()
		.authenticationEntryPoint(unauthorizedHandler)
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.authorizeRequests()
		.antMatchers("/api/auth/**")
        .permitAll()
		.antMatchers("/",
				"/favicon.ico",
                "/**/*.png",
                "/**/*.gif",
                "/**/*.svg",
                "/**/*.jpg",
                "/**/*.html",
                "/**/*.css",
                "/**/*.js")
		.permitAll()
		.antMatchers("/api/user/checkUsernameAvailability", "/api/user/checkEmailAvailability")
        .permitAll()
    .antMatchers(HttpMethod.GET, "/api/polls/**", "/api/users/**")
        .permitAll()
    .anyRequest()
        .authenticated();
		
		// adding the jwtAuthFilter
		//this will authenticate before username and password filter
		
		http.addFilterBefore(jwtauthFilter, UsernamePasswordAuthenticationFilter.class);
		
	}

	// For authentication
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userdetails)
		.passwordEncoder(passwordEncoder());
	}
	
	// this is needed in controller
	@Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
