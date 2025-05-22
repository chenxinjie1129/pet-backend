package com.petshome.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Spring Security配置类
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 配置AuthenticationManager使用自定义的UserDetailsService
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // 启用CORS并禁用CSRF
        httpSecurity.cors().and().csrf().disable()
                // 不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 异常处理
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
                // 授权请求
                .authorizeRequests()
                // 允许访问的公共资源
                .antMatchers("/auth/**", "/user/register", "/user/login", "/user/login/mobile", "/admin/login", "/admin/logout",
                             "/api/auth/**", "/api/user/register", "/api/user/login", "/api/user/login/mobile",
                             "/api/admin/login", "/api/admin/logout").permitAll()
                // Swagger相关资源
                .antMatchers("/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs/**", "/webjars/**",
                             "/api/swagger-ui/**", "/api/swagger-resources/**", "/api/v3/api-docs/**", "/api/webjars/**").permitAll()
                // 静态资源路径，允许公开访问
                .antMatchers("/api/uploads/**", "/uploads/**", "/static/**", "/api/static/**").permitAll()
                // 添加更多日志记录，便于调试
                .antMatchers("/api/uploads/**").access("@debugLogger.logAccess(request, 'Accessing uploads')")
                .antMatchers("/static/**").access("@debugLogger.logAccess(request, 'Accessing static')")
                // 其他所有请求需要认证
                .anyRequest().authenticated();

        // 添加JWT过滤器
        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // 禁用缓存
        httpSecurity.headers().cacheControl();
    }
}
