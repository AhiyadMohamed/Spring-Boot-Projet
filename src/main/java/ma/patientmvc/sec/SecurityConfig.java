package ma.patientmvc.sec;

import ma.patientmvc.sec.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {


        /*

    Memorie Authentification

        String encodedPWD = passwordEncoder.encode("1234");
        System.out.println(encodedPWD);

        auth.inMemoryAuthentication().withUser("User1").password(encodedPWD).roles("USER");
        auth.inMemoryAuthentication().withUser("User2").password(passwordEncoder.encode("1234"))
                .roles("USER");
        auth.inMemoryAuthentication().withUser("Admin").password(passwordEncoder.encode("1234"))
                .roles("USER","ADMIN");

         */

       //  JDBC AUTHENTIFICATION
        /*

        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select username as principal ,password as credentials,active from users where username=?")
                .authoritiesByUsernameQuery("select username as principal,role as role from users_role where username=?")
                .rolePrefix("ROLE_")
                .passwordEncoder(passwordEncoder());

        */



     //  User Details Service
        auth.userDetailsService(userDetailsService);




    }

    //Pour Specifier Les Droit D'acces
    //formlogin permet de demander a springsecurity d'utiliser un formulaire d'authentification
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.formLogin();
        http.authorizeHttpRequests().antMatchers("/delete/**", "/edit/**",  "/save/**","/formPatients/**" ).hasAuthority("ADMIN");
        http.authorizeHttpRequests().antMatchers("/index/**").hasAuthority("USER");

        http.authorizeHttpRequests().anyRequest().authenticated();
        http.exceptionHandling().accessDeniedPage("/403");
    }


}
