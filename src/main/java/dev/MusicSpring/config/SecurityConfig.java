package dev.MusicSpring.config;


import dev.MusicSpring.db.entities.auth.BaseRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception{

        auth.jdbcAuthentication().dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery(
                        "select username,password,enabled from users where username=?"
                )
                .authoritiesByUsernameQuery(
                        "select username,role from user_roles where username=?"
                );
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }
    @Override
    public void configure(final HttpSecurity http) throws Exception{
        System.setProperty("https.protocols", "TLSv1.2");
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);

        http.addFilterBefore(filter, CsrfFilter.class);
        http.
                httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/home/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/user").permitAll()

                .antMatchers(HttpMethod.POST, "/api/logout").permitAll()
//                .antMatchers(HttpMethod.GET, "/api/client/**").hasAnyAuthority(BaseRole.CLIENT.getRole())
//                .antMatchers(HttpMethod.PUT,"/api/client/**").hasAnyAuthority(BaseRole.CLIENT.getRole())
//                .antMatchers(HttpMethod.POST, "/api/client/**").hasAnyAuthority(BaseRole.CLIENT.getRole())
                .antMatchers(HttpMethod.GET, "/api/admin/**").hasAnyAuthority(BaseRole.SUPER_USER.getRole())
                .antMatchers(HttpMethod.POST, "/api/admin/**").hasAnyAuthority(BaseRole.SUPER_USER.getRole())
                .antMatchers(HttpMethod.DELETE,"/api/admin/**").hasAnyAuthority(BaseRole.SUPER_USER.getRole())
                .antMatchers(HttpMethod.DELETE,"/api/admin/album/**").hasAnyAuthority(BaseRole.SUPER_USER.getRole())
                .antMatchers(HttpMethod.PUT,"/api/admin/**").hasAnyAuthority(BaseRole.SUPER_USER.getRole())
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .and()
                .csrf().disable();
        http.cors().disable();
    }
}