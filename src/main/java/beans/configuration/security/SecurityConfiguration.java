package beans.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@ComponentScan("beans")
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable().authorizeRequests().
//                .antMatchers("/getUserById/**").hasAnyAuthority("BOOKING_MANAGER","REGISTERED_USER")
//                .antMatchers("/getAuditoriums").hasAnyAuthority("BOOKING_MANAGER","REGISTERED_USER")
//                .antMatchers("/getAuditoriumByName/**").hasAnyAuthority("BOOKING_MANAGER","REGISTERED_USER")
//                .antMatchers("/getSeatsNumber/**").hasAnyAuthority("BOOKING_MANAGER","REGISTERED_USER")
//                .antMatchers("/getVipSeats/**").hasAnyAuthority("BOOKING_MANAGER","REGISTERED_USER")
//                .antMatchers("/getTicketPrice**").hasAnyAuthority("BOOKING_MANAGER","REGISTERED_USER")
//                .antMatchers("/getTicketForEvent**").hasAnyAuthority("BOOKING_MANAGER","REGISTERED_USER")
//
//                .antMatchers("/getDiscount**").hasAnyAuthority("BOOKING_MANAGER","REGISTERED_USER")
//                .antMatchers("/getEventByName/**").hasAnyAuthority("BOOKING_MANAGER","REGISTERED_USER")
//                .antMatchers("/getEvent**").hasAnyAuthority("BOOKING_MANAGER","REGISTERED_USER")
//                .antMatchers("/getAllEvents").hasAnyAuthority("BOOKING_MANAGER","REGISTERED_USER")
//                .antMatchers("/createEvent**").hasAnyAuthority("BOOKING_MANAGER","REGISTERED_USER")
//                .antMatchers("/deleteEvent**").hasAnyAuthority("BOOKING_MANAGER","REGISTERED_USER")
//                .antMatchers("/getForDateRange**").hasAnyAuthority("BOOKING_MANAGER","REGISTERED_USER")
//                .antMatchers("/getNextEvents**").hasAnyAuthority("BOOKING_MANAGER","REGISTERED_USER")
//                .antMatchers("/loadFiles**").hasAnyAuthority("BOOKING_MANAGER","REGISTERED_USER")
//                .antMatchers("/loadEvents**").hasAnyAuthority("BOOKING_MANAGER","REGISTERED_USER")
//
//                .antMatchers("/getUserByName/**").hasAnyAuthority("BOOKING_MANAGER","REGISTERED_USER")
//                .antMatchers("/getUserByEmail/**").hasAnyAuthority("BOOKING_MANAGER","REGISTERED_USER")
//                .antMatchers("/createUser**").hasAnyAuthority("BOOKING_MANAGER","REGISTERED_USER")
//                .antMatchers("/deleteUserByEmail**").hasAnyAuthority("BOOKING_MANAGER","REGISTERED_USER")
//                .antMatchers("/loadUsers**").hasAnyAuthority("BOOKING_MANAGER","REGISTERED_USER")
//                .antMatchers("/bookTicket**").hasAnyAuthority("BOOKING_MANAGER","REGISTERED_USER")
//                //Only for BOOKING MANAGER ROLE
//                .antMatchers("/getBookedTicketsByUser**").hasAuthority("BOOKING_MANAGER").

                 anyRequest().fullyAuthenticated().and()
                .formLogin().loginPage("/login").failureUrl("/login?error")
                .usernameParameter("name").passwordParameter("password").permitAll()
                .and()
                .logout().invalidateHttpSession(true).logoutUrl("/logout").logoutSuccessUrl("/login?logout").deleteCookies("JSESSIONID")
                .deleteCookies("remember-me").permitAll()
                .and().exceptionHandling().accessDeniedPage("/403")
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
