package beans.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@ComponentScan("beans")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.headers().frameOptions().disable();
        http.csrf().disable().authorizeRequests()
                .antMatchers("/getUserById/**").hasAuthority("REGISTERED_USER")
                .antMatchers("/getAuditoriums").hasAuthority("REGISTERED_USER")
                .antMatchers("/getAuditoriumByName/**").hasAuthority("REGISTERED_USER")
                .antMatchers("/getSeatsNumber/**").hasAuthority("REGISTERED_USER")
                .antMatchers("/getVipSeats/**").hasAuthority("REGISTERED_USER")
                .antMatchers("/getTicketPrice/**").hasAuthority("REGISTERED_USER")
                .antMatchers("/getTicketForEvent/**").hasAuthority("REGISTERED_USER")
                .antMatchers("/bookTicket**").hasAuthority("BOOKING_MANAGER").

                 anyRequest().authenticated().and().formLogin().loginPage("/login")
                .permitAll().failureUrl("/login?error").usernameParameter("name").passwordParameter("password")
                .and().logout().permitAll()
                .logoutSuccessUrl(
                "/login?logout")
                .deleteCookies("remember-me")
                .and().csrf().and().exceptionHandling().accessDeniedPage("/403")
                .and().rememberMe()
                .tokenRepository(persistentTokenRepository()).tokenValiditySeconds(120);

    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder()); //restore
//        auth.inMemoryAuthentication().withUser("user").password("user").roles("REGISTERED_USER");
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(dataSource);
        return db;
    }



}
