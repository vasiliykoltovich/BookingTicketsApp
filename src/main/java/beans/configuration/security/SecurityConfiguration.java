package beans.configuration.security;

import beans.services.UserService;
import beans.services.security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@ComponentScan("beans")
//@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests().antMatchers("/bookTicket/**")
//                .access("hasRole('BOOKING_MANAGER')").and().formLogin()
//                .loginPage("/login").failureUrl("/login?error")
//                .usernameParameter("name")
//                .passwordParameter("password")
//                .and().logout().logoutSuccessUrl("/login?logout")
//                .and().csrf()
//                .and().exceptionHandling().accessDeniedPage("/403");
//        http.csrf().disable();
        http.headers().frameOptions().disable();

        http.csrf().disable()
                .authorizeRequests()
          .antMatchers("/login").permitAll()
         .antMatchers("/getUsers/**").hasRole("REGISTERED_USER")
         .antMatchers("/getAuditoriums").hasRole("REGISTERED_USER")
         .antMatchers("/getAuditoriumByName/**").hasRole("REGISTERED_USER")
         .antMatchers("/getSeatsNumber/**").hasRole("REGISTERED_USER")
         .antMatchers("/getVipSeats/**").hasRole("REGISTERED_USER")
         .antMatchers("/getTicketPrice/**").hasRole("REGISTERED_USER")
         .antMatchers("/getTicketForEvent/**").hasRole("REGISTERED_USER")


         .antMatchers("/bookTicket**").access("hasRole('BOOKING_MANAGER')").and().formLogin()
                                                         .loginPage("/login").failureUrl("/login?error")
                                                         .usernameParameter("name")
                                                         .passwordParameter("password")
                                                         .and().logout().logoutSuccessUrl("/login?logout")
                                                         .and().csrf()
                                                         .and().exceptionHandling().accessDeniedPage("/403");

//
//        http.authorizeRequests().antMatchers("/bookTicket").hasRole("BOOKING_MANAGER")
//                .and().formLogin().loginPage("/login").failureUrl("/login?error")
//                .usernameParameter("name").passwordParameter("password")
//                .and().logout().logoutSuccessUrl("/login?logout")
//                .and().csrf().and().exceptionHandling().accessDeniedPage("/403");



//        http.authorizeRequests().anyRequest().hasRole("REGISTERED_USER").and()
//                .access("hasRole('BOOKING_MANAGER')").and().formLogin()
//                .loginPage("/login").failureUrl("/login?error")
//                .usernameParameter("name")
//                .passwordParameter("password")
//                .and().logout().logoutSuccessUrl("/login?logout")
//                .and().csrf()
//                .and().exceptionHandling().accessDeniedPage("/403");

    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
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


}
