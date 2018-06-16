package br.com.academiadev.reembolsoazul.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import br.com.academiadev.reembolsoazul.config.jwt.ApiPasswordEncoder;
import br.com.academiadev.reembolsoazul.config.jwt.AutenticacaoRestListener;
import br.com.academiadev.reembolsoazul.config.jwt.TokenFilter;
import br.com.academiadev.reembolsoazul.config.jwt.TokenHelper;
import br.com.academiadev.reembolsoazul.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new ApiPasswordEncoder(new BCryptPasswordEncoder());
	}

	@Autowired
	private CustomUserDetailsService jwtUserDetailsService;

	@Autowired
	private AutenticacaoRestListener restAuthenticationEntryPoint;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Autowired
	TokenHelper tokenHelper;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable();

		http.cors().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()//
				.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()//
				.authorizeRequests()//
				.antMatchers("/auth/**").permitAll()//
				.antMatchers(HttpMethod.POST, "/user/**").permitAll()//
				.antMatchers(HttpMethod.POST, "/company/**").permitAll()//
				.antMatchers("/management/**").permitAll()//
				.anyRequest().authenticated().and()//
				.addFilterBefore(new TokenFilter(tokenHelper, jwtUserDetailsService), BasicAuthenticationFilter.class);
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addExposedHeader("Authorization");
		config.addAllowedMethod(HttpMethod.OPTIONS);
		config.addAllowedMethod(HttpMethod.GET);
		config.addAllowedMethod(HttpMethod.POST);
		config.addAllowedMethod(HttpMethod.PUT);
		config.addAllowedMethod(HttpMethod.DELETE);
		source.registerCorsConfiguration("/**", config);
		return source;
	}

	@Override
	public void configure(WebSecurity web) throws Exception {

		web.ignoring().antMatchers(HttpMethod.GET, //
				"/", //
				"/webjars/**", //
				"/*.html", //
				"/favicon.ico", //
				"/**/*.html", //
				"/v2/api-docs", //
				"/configuration/ui", //
				"/swagger-resources/**", //
				"/configuration/**", //
				"/swagger-ui.html", //
				"/webjars/**", //
				"/**/*.css", //
				"/**/*.js"//
		);

	}
}
