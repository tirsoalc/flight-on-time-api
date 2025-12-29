package com.flightontime.flightapi.infra.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration
@EnableWebSecurity
public class  SecurityConfigurations {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfigurations.class);

    @Autowired
    private SecurityFilter securityFilter;

    public static final String [] ENDPOINTS_POST_NO_AUTH = {"/login", "/predict"};
    public static final String [] ENDPOINTS_GET_NO_AUTH = {"/", "/check/**", "/h2-console/**", "/generate_204", "/gerar-senha"};
    public static final String [] ENDPOINTS_SWAGGER = {"/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**"};
    public static final String [] ENDPOINTS_ADMIN = {"/admin", "/usuarios/**"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("SecurityConfigurations.SecurityFilterChain");
        return http
                .cors(cors -> {})
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm ->
                        sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(req -> {

                    // 🔓 LOGIN
                    req.requestMatchers(HttpMethod.POST, "/login").permitAll();

                    //  VOOS - VISUALIZAÇÃO PÚBLICA
                    req.requestMatchers(HttpMethod.GET, "/predict").permitAll();

                    // PUBLICO SWAGGER
                    req.requestMatchers(
                            "/v3/api-docs/**",
                            "/swagger-ui.html",
                            "/swagger-ui/**"
                    ).permitAll();

                    // PERM. SOMENTE ADMIN / EQUIPE
                    req.requestMatchers(
                            HttpMethod.POST, "/voos/**"
                    ).hasRole("ADMIN");

                    req.requestMatchers(
                            HttpMethod.PUT, "/voos/**"
                    ).hasRole("ADMIN");

                    req.requestMatchers(
                            HttpMethod.DELETE, "/voos/**"
                    ).hasRole("ADMIN");

                    req.requestMatchers("/usuarios/**").hasRole("ADMIN");

                    //TODO O RESTO PUBLICO
                    req.anyRequest().authenticated();
                })

                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
//                .cors(cors -> {}) // add recent
//                .csrf(AbstractHttpConfigurer::disable)
//                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(req -> {
//                    // login somente para equipe
//                    req.requestMatchers(HttpMethod.POST,"/login").permitAll();
//                    //visualização pública
//                    req.requestMatchers(HttpMethod.GET, "/voos").permitAll();
//                    req.requestMatchers(ENDPOINTS_SWAGGER).permitAll();
//                    req.requestMatchers(ENDPOINTS_ADMIN).hasRole("ADMIN");
//                    req.anyRequest().authenticated();
//                })
//                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
//                .build();
//    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        log.info("SecurityConfigurations.AuthenticationManager");
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}

