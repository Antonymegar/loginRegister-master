package com.example.loginregister.Config;

import com.example.loginregister.Config.EmployeeDetailsServiceImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@Configuration
@EnableWebSecurity
public class WebConfig  extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {
        return new EmployeeDetailsServiceImp();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/home").hasAnyAuthority("Employee", "Officer", "Admin")
                .antMatchers("/register_employee").hasAnyAuthority("Officer", "Admin")
                .antMatchers("/edit/**").hasAnyAuthority("Admin", "Officer")
                .antMatchers("/delete/**").hasAuthority("Admin")
                .antMatchers("/admin").hasAuthority("Admin")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/log-in")
                .usernameParameter("username").permitAll()
//                .successHandler(new CustomLoginSuccessHandler())
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/AccesDenied")
        ;
    }

}

