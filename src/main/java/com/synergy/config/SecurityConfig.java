package com.synergy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	
	
	@Autowired
	UserDetailsService userDetailsService;
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptpeasswordEncoder());
	}
	
	protected void configure(HttpSecurity hts) throws Exception {
		hts.authorizeRequests().antMatchers("/home","/GetHotelsBytext/*","/findHotels","/signup","/saveUser").permitAll();
		hts.authorizeRequests().anyRequest().authenticated()
		.and()
		.formLogin().loginPage("/login").permitAll().defaultSuccessUrl("/home", true)
		.and()
		.logout().logoutSuccessUrl("/login?logout")
		.and()
		.exceptionHandling().accessDeniedPage("/accessDeniedPage")
		.and()
		.httpBasic()
		.and()
		.csrf().disable();
	}
	public void configure(WebSecurity webs) {
		webs.ignoring().antMatchers("/images/*","/css/*","/js/*");
	}
	@Bean
	public BCryptPasswordEncoder bCryptpeasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}