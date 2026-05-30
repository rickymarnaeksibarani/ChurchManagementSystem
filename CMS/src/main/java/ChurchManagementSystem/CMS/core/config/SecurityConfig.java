package ChurchManagementSystem.CMS.core.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
@RequiredArgsConstructor
@EnableTransactionManagement
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CorsCustomConfiguration corsCustomConfiguration;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors.configurationSource(corsCustomConfiguration))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
            /*
                please use only one
                .anyRequest().authenticated() -> all endpoint request must be hit WITH the authentication before
                .anyRequest().permitAll() -> all endpoint request can hit WITHOUT authentication
             */
//                                .anyRequest().permitAll()
                        .requestMatchers(
                                "/api/auth/register/main", "/api/auth/login",
                                "/api/auth/register/users",
                                "/api/auth/verify",
                                "/api/auth/forgot",
//                                "/api/auth/reset",
                                "/api/auth/resend",
//                                "/api/auth/change-password",
                                "/api/auth/logout",
                                "/api/auth/login/user",
                                "/api/auth/register/view",
                                "/api/v1/news/**",
                                "/api/v1/news/{id}",
                                "/api/v1/activity/**")
                                .permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/**").hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.POST, "/api/auth/admin/change-password").hasAnyRole("ADMIN")
                                    .requestMatchers(HttpMethod.POST, "/api/auth/change-password").hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.POST, "/api/v1/**").hasAnyRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/v1/**").hasAnyRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/**").hasAnyRole("ADMIN")
                                .anyRequest().authenticated()

                )
                // disable this filter jwt if all endpoint request can hit WITHOUT authentication
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
