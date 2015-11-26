package cgk.bibliothouris.learning.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationFailure authFailure;

    @Autowired
    private LogoutSuccess logoutSuccess;


    @Autowired
    private AuthenticationSuccess authSuccess;

    @Autowired
    private EntryPointUnauthorized unauthorizedHandler;

    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {

        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery(
                        "select username,password,enabled from book_user where username=?")
                .authoritiesByUsernameQuery(
                        "select username,role from user_roles where username=?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/app/**").authenticated()
                .antMatchers("/api/**").permitAll()
                .antMatchers("/app/js/**").authenticated()
                .and()
                .formLogin()
                .successHandler(authSuccess)
                .failureHandler(authFailure)
                .loginPage("/login.html")
                .loginProcessingUrl("/auth/login")
                .defaultSuccessUrl("/app", true)
                .failureUrl("/login?error")
                .and()
                .logout()
                .logoutUrl("/auth/logout")
                .logoutSuccessUrl("/login?logout")
                .logoutSuccessHandler(logoutSuccess)
                .clearAuthentication(true)
                .deleteCookies()
                .invalidateHttpSession(true)
                .and()
                .addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class);
    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }
}
