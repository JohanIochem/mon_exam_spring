package com.jiochem.spring.exam.Config;


import com.jiochem.spring.exam.Services.SpringAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    SpringAuthService springAuthService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
// csrf.disable pendant la phase de développement.

                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/login", "/register", "error/**","annonces/list", "/annonces/{id}","/js/**", "css/**","upload/**", "/").permitAll()
                        .requestMatchers("/js/**", "css/**").permitAll()
                        .requestMatchers("/admin").hasAuthority("admin")
                        .requestMatchers("/annonces/ajouter", "/annonces/modifier/**", "/annonces/supprimer/**").hasAnyAuthority("journaliste", "admin")
                        .anyRequest().authenticated())
                .formLogin((form)->form.loginPage("/login").permitAll())
                .logout((logout)->logout.logoutUrl("/logout-processing"));


        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(springAuthService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());


        return daoAuthenticationProvider;
    }
}
