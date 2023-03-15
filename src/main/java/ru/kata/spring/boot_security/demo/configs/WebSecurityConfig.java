package ru.kata.spring.boot_security.demo.configs;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final SuccessUserHandler successUserHandler;
  //    private final UserService userService;
  private final UserDetailsService userDetailsService;

  @Autowired
  public WebSecurityConfig(SuccessUserHandler successUserHandler, /*UserService userService,*/
      UserDetailsService userDetailsService) {

    this.successUserHandler = successUserHandler;
//        this.userService = userService;
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .authorizeRequests()
        .antMatchers("/", "/index", "/login").permitAll()
        .antMatchers("/admin/**").hasRole("ADMIN")
        .antMatchers("/user").hasRole("USER")
        .antMatchers(HttpMethod.GET, "/admin/**").hasRole("ADMIN")
        .antMatchers(HttpMethod.POST, "/admin/**").hasRole("ADMIN")
        .antMatchers(HttpMethod.DELETE, "/admin/**").hasRole("ADMIN")
        .antMatchers(HttpMethod.PATCH, "/admin/**").hasRole("ADMIN")
        .antMatchers(HttpMethod.GET, "/user/**").hasAnyRole("ADMIN", "USER")
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .loginPage("/login")
        .successHandler(successUserHandler)
        .failureUrl("/login")
        .and()
        .logout()
        .logoutRequestMatcher(new AntPathRequestMatcher("/login/logout", "POST"))
        .invalidateHttpSession(true)
        .clearAuthentication(true)
        .deleteCookies("JSESSIONID")
        .logoutSuccessUrl("/login")
        .permitAll();

  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
//        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
//
//        return daoAuthenticationProvider;
//    }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
        .userDetailsService(userDetailsService)
        .passwordEncoder(passwordEncoder());

  }
}