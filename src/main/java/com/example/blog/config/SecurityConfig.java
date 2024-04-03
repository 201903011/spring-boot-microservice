package com.example.blog.config;

import com.example.blog.repositories.UserRepo;
import com.example.blog.security.CustomuserDetailService;
import com.example.blog.security.JwtAuthenticationEntrypoint;
import com.example.blog.security.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    @Autowired
//    CustomuserDetailService userDetailsService1;

    @Autowired
    UserRepo userRepo;

    @Autowired
    private JwtAuthenticationEntrypoint jwtAuthenticationEntrypoint;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
//    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(); //a provider
        provider.setUserDetailsService(new CustomuserDetailService(this.userRepo)); //user details service
        provider.setPasswordEncoder(bCryptPasswordEncoder()); //you can add password encoders too

        return provider;
    }

//    @Bean
//    public UserDetailsService customuserDetailsService() {
//        return this.userDetailsService;
//    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        return this.userDetailsService1;
//    }


//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user = User.builder().username("user").password("xyz")
//                .roles("ADMIN").build();
////        UserDetails user1 = User.builder().username("user").password(bCryptPasswordEncoder().encode("xyz"))
////                .roles("USER").build();
//
//        return new InMemoryUserDetailsManager(user);
//    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrfConfigurer -> csrfConfigurer.disable())
                .cors(cors -> cors.disable())
                .authorizeHttpRequests((authorize) ->

                        authorize.requestMatchers(HttpMethod.GET, "/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/**").hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .exceptionHandling(exception -> exception.
                        authenticationEntryPoint((request, response, authException) ->
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED))

                )
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                
                .httpBasic(Customizer.withDefaults());
//        http.authenticationProvider();
        return http.build();
    }


}
