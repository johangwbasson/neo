package net.johanbasson.neo.config;

import net.johanbasson.neo.security.JwtRequestFilter;
import net.johanbasson.neo.security.JwtTokenService;
import net.johanbasson.neo.users.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;
//    @Autowired
//    private AuthEntryPointJwt unauthorizedHandler;
//    @Bean
//    public AuthTokenFilter authenticationJwtTokenFilter() {
//        return new AuthTokenFilter();
//    }

//    @Override
//    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//    }

//    @Bean
//    @Override
//    public AuthenticationManager authenticationManager() throws Exception {
//        return authenticationManagerBean();
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.cors().and().csrf().disable()
//                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//                .authorizeRequests().antMatchers("/api/authenticate").permitAll()
//                .antMatchers("/api/test/**").permitAll()
//                .anyRequest().authenticated();
//        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//    }


    @Bean
    public AuthenticationManager authenticationManager(
            final AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain configure(final HttpSecurity http) throws Exception {
        return http.cors().and()
                .csrf().disable()
                .authorizeRequests().
                antMatchers("/api/authenticate").permitAll().
                anyRequest().hasRole(Role.USER.name()).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


//    @Bean
//    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
//
//        // Configure AuthenticationManagerBuilder
//        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
//        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//        // Get AuthenticationManager
//        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
//        AuthenticationConverter authenticationConverter = http.getSharedObject(AuthenticationConverter.class);
//        http
//                .cors().and()
//                .csrf().disable().authorizeRequests()
//                .antMatchers(HttpMethod.POST, "/api/authenticate")
//                .permitAll()
////                .antMatchers(SecurityConstants.H2_CONSOLE)
////                .permitAll()
//                .anyRequest().authenticated().and()
//                // User Authentication with custom login URL path
//                .addFilter(getAuthenticationFilter(authenticationManager, authenticationConverter))
//                // User Authorization with JWT
//                .addFilter(new AuthorizationFilter(authenticationManager, jwtUtils, userDetailsService))
//                .authenticationManager(authenticationManager)
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.headers().frameOptions().disable();
//        return http.build();
//    }
//    // Configure custom login URL path
//    protected AuthenticationFilter getAuthenticationFilter(AuthenticationManager authenticationManager, AuthenticationConverter converter) throws Exception {
//        final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager, converter);
//        return filter;
//    }
}
